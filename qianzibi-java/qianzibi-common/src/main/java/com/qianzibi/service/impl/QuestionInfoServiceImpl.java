package com.qianzibi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianzibi.common.ResultCode;
import com.qianzibi.entity.constants.Constants;
import com.qianzibi.entity.dto.ImportErrorItem;
import com.qianzibi.entity.dto.SessionUserAdminDto;
import com.qianzibi.entity.enums.CategoryTypeEnum;
import com.qianzibi.entity.enums.PostStatusEnum;
import com.qianzibi.entity.enums.VerifyRegexEnum;
import com.qianzibi.entity.po.Category;
import com.qianzibi.entity.po.QuestionInfo;
import com.qianzibi.entity.query.QuestionInfoQuery;
import com.qianzibi.exception.BusinessException;
import com.qianzibi.mapper.ACommonMapper;
import com.qianzibi.service.CategoryService;
import com.qianzibi.service.QuestionInfoService;
import com.qianzibi.mapper.QuestionInfoMapper;
import com.qianzibi.utils.ExcelUtils;
import com.qianzibi.utils.VerifyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
* @author 86158
* @description 针对表【question_info(问题)】的数据库操作Service实现
* @createDate 2025-02-04 21:32:15
*/
@Service
public class QuestionInfoServiceImpl extends ServiceImpl<QuestionInfoMapper, QuestionInfo>
    implements QuestionInfoService{

    @Resource
    private CategoryService categoryService;

    @Resource
    private QuestionInfoMapper questionInfoMapper;

    @Resource
    private ACommonMapper aCommonMapper;

    @Override
    public void saveOrUpdateQIF(Boolean isSuperAdmin, QuestionInfo questionInfo) {
            Category CategoryById = categoryService.getById(questionInfo.getCategoryId());
        questionInfo.setCategoryName(CategoryById.getCategoryName());
        if (questionInfo.getQuestionId() == null) {
            questionInfoMapper.insert(questionInfo);
        } else {
            QuestionInfo dbQIF = questionInfoMapper.selectById(questionInfo);
            if (!dbQIF.getCreateUserId().equals(questionInfo.getCreateUserId()) && !isSuperAdmin) {
                throw new BusinessException(ResultCode.ERROR_NOPERMISSION, "修改者全权限不足");
            }
            questionInfo.setCreateTime(null);
            questionInfo.setCreateUserName(null);
            questionInfo.setCategoryId(null);
            questionInfoMapper.updateById(questionInfo);
        }
    }

    /**
     * 显示下一条
     * @Param query:问题详情
     * @Param nextType=-1:已经是第一条； nextType=1:不是第一条 ,
     * @Param currentId: ,
     * @Param updateReadCount:阅读数量
     */
    @Override
    public QuestionInfo showDetailNext(QuestionInfoQuery query, Integer nextType, Integer currentId, boolean updateReadCount) {
        if (nextType == null) {
            query.setQuestionId(currentId);
        } else {
            query.setNextType(nextType);
            query.setCurrentId(currentId);
        }
        // 获取问题详情
        LambdaQueryWrapper<QuestionInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (nextType == null) {
            queryWrapper.eq(QuestionInfo::getQuestionId, currentId);
        } else if (nextType == 1) {
            // 上一条
            queryWrapper.lt(QuestionInfo::getQuestion, currentId);
            queryWrapper.orderByDesc(QuestionInfo::getQuestionId);
        } else if (nextType == -1) {
            // 下一条
            queryWrapper.gt(QuestionInfo::getQuestionId, currentId);
            queryWrapper.orderByAsc(QuestionInfo::getQuestionId);
        }
        QuestionInfo questionInfo = questionInfoMapper.selectOne(queryWrapper);
        if (questionInfo == null && nextType == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "内容不存在");
        } else if (questionInfo == null && nextType == -1) {
            throw new BusinessException(ResultCode.ERROR_OTHER, "已经是第一条了");
        } else if (questionInfo == null && nextType == 1) {
            throw new BusinessException(ResultCode.ERROR_OTHER, "已经是最后一条了");
        }
        // 更新阅读数量
        if (updateReadCount && questionInfo != null) {
            aCommonMapper.updateCount(Constants.TABLE_NAME_QUESTION_INFO, 1, null, currentId);
            questionInfo.setReadCount((questionInfo.getReadCount() == null ? 0 : questionInfo.getReadCount()) + 1);
        }
        return questionInfo;
    }

    /**
     * 批量删除
     * 传userId的就是创建者删除自己上传的题目
     * userId为null的就是超级管理员，可以任意删除
     */
    @Override
    public void removeBatchQIF(String questionIds, Integer userId) {
        String[] questionIdsArray = questionIds.split(",");
        if (userId != null) {
            QuestionInfoQuery queryQIF = new QuestionInfoQuery();
            queryQIF.setQuestionIds(questionIdsArray);
            QueryWrapper<QuestionInfo> queryWrapper = judgeTextQW(queryQIF);
            List<QuestionInfo> questionInfoList = questionInfoMapper.selectList(queryWrapper);
            // 过滤出不属于当前用户的数据列表
            List<QuestionInfo> currentUserDataList = questionInfoList.stream()
                    .filter(a -> !a.getCreateUserId().equals(String.valueOf(userId)))
                    .collect(Collectors.toList());
            if (!currentUserDataList.isEmpty()) {
                throw new BusinessException(ResultCode.ERROR_NAN, "当前用户可删除的数据列表为空");
            }
        }
        QueryWrapper<QuestionInfo> queryDel = new QueryWrapper<>();
        queryDel.eq("status", PostStatusEnum.NO_POST.getStatus());
        if (userId != null) {
            queryDel.eq("create_user_id", userId);
        }
        //转化为int类型数组
        List<String> list = Arrays.asList(questionIdsArray);
        queryDel.in("question_id", list);
        questionInfoMapper.delete(queryDel);
    }

    /**
     * 当query.getQueryTextContent为false排除question和answer_analysis
     */
    protected QueryWrapper<QuestionInfo> judgeTextQW(QuestionInfoQuery query) {
        QueryWrapper<QuestionInfo> queryWrapper = new QueryWrapper<>();
        if (query.getQueryTextContent() != null && !query.getQueryTextContent()) {
            queryWrapper.select(QuestionInfo.class, info -> !(info.getColumn().equals("question") && info.getColumn().equals("answer_analysis")));
        }
        return queryWrapper;
    }

    @Override
    public void updateBatchByQIFId(QuestionInfo questionInfo, QuestionInfoQuery queryParams) {
        List<String> list = Arrays.asList(queryParams.getQuestionIds());
        questionInfoMapper.updateBatchByQIFId(list, questionInfo);
    }

    /**
     * 导入处理
     * @param file 文件
     * @param sessionUserAdminDto 用户
     */
    @Override
    public List<ImportErrorItem> importQuestion(MultipartFile file, SessionUserAdminDto sessionUserAdminDto) {
        List<Category> categories = categoryService.loadAllCategoryByType(CategoryTypeEnum.QUESTION);
        // 将分类列表转换为以分类对象的名为键，分类对象为值的 Map
        Map<String, Category> categoryMap = categories.stream().collect(Collectors.toMap(Category::getCategoryName, Function.identity(), (data1, data2) -> data2));
        // 从 Excel 文件中读取数据，并将数据存储在一个二维列表中
        List<List<String>> dataList = ExcelUtils.readExcel(file, Constants.EXCEL_TITLE_QUESTION, 1);

        // 错误列
        ArrayList<ImportErrorItem> errorList = new ArrayList<>();

        ArrayList<QuestionInfo> questionList = new ArrayList<>();

        // 因为模板中第三行开始才是数据，所以定义为2
        Integer dataRowNum = 2;
        for (List<String> row : dataList) {
            if (errorList.size() > Constants.LENGTH_50) {
                throw new BusinessException(ResultCode.ERROR_600, "错误数据超过" + Constants.LENGTH_50 + "行，请认真检查数据后再导入");
            }
            dataRowNum++;
            List<String> errorItemList = new ArrayList<>();

            Integer index = 0;
            // 标题
            String title = row.get(index++);
            if (!StringUtils.hasText(title) || title.length() > Constants.LENGTH_150) {
                errorItemList.add("标题不能为空，且长度不能超过" + Constants.LENGTH_150);
            }
            // 分类
            String categoryName = row.get(index++);
            Category category = categoryMap.get(categoryName);
            if (category == null) {
                errorItemList.add("分类名称不存在");
            }
            // 难度
            String difficultyLevel = row.get(index++);
            Integer difficultyLevelInt = null;
            if (VerifyUtils.verify(VerifyRegexEnum.POSITIVE_INTEGER, difficultyLevel)) {
                difficultyLevelInt = Integer.parseInt(difficultyLevel);
                if (difficultyLevelInt > 5) {
                    errorItemList.add("难度只能是1到5的数字");
                }
            } else {
                errorItemList.add("难度必须是正整数");
            }
            // 问题描述
            String question = row.get(index++);
            // 答题详解
            String answerAnalysis = row.get(index++);
            if (!StringUtils.hasText(answerAnalysis)) {
                errorItemList.add("答案不能为空");
            }

            // 有错误则添加错误信息
            if (!errorItemList.isEmpty() || !errorList.isEmpty()) {
                ImportErrorItem errorItem = new ImportErrorItem();
                errorItem.setRowNum(dataRowNum);
                errorItem.setErrorItemList(errorItemList);
                errorList.add(errorItem);
                continue;
            }

            // 没错误，保存信息
            QuestionInfo questionInfo = new QuestionInfo();
            questionInfo.setTitle(title);
            questionInfo.setCategoryId(category.getCategoryId());
            questionInfo.setCategoryName(category.getCategoryName());
            questionInfo.setDifficultyLevel(difficultyLevelInt);
            questionInfo.setQuestion(question);
            questionInfo.setAnswerAnalysis(answerAnalysis);
            questionInfo.setCreateTime(new Date());
            questionInfo.setStatus(PostStatusEnum.NO_POST.getStatus());
            questionInfo.setCreateUserId(String.valueOf(sessionUserAdminDto.getUserId()));
            questionInfo.setCreateUserName(sessionUserAdminDto.getUserName());
            questionList.add(questionInfo);
        }
        if (questionList.isEmpty()) {
            return errorList;
        }
        // 批量存入
        this.questionInfoMapper.insertBatch(questionList);
        return errorList;
    }
}




