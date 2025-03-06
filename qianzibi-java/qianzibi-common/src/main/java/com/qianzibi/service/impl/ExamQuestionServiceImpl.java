package com.qianzibi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianzibi.common.ResultCode;
import com.qianzibi.entity.constants.Constants;
import com.qianzibi.entity.dto.ImportErrorItem;
import com.qianzibi.entity.dto.SessionUserAdminDto;
import com.qianzibi.entity.enums.CategoryTypeEnum;
import com.qianzibi.entity.enums.PostStatusEnum;
import com.qianzibi.entity.enums.QuestionTypeEnum;
import com.qianzibi.entity.enums.VerifyRegexEnum;
import com.qianzibi.entity.po.Category;
import com.qianzibi.entity.po.ExamQuestion;
import com.qianzibi.entity.po.ExamQuestionItem;
import com.qianzibi.entity.query.ExamQuestionQuery;
import com.qianzibi.exception.BusinessException;
import com.qianzibi.mapper.CategoryMapper;
import com.qianzibi.mapper.ExamQuestionItemMapper;
import com.qianzibi.service.CategoryService;
import com.qianzibi.service.ExamQuestionService;
import com.qianzibi.mapper.ExamQuestionMapper;
import com.qianzibi.utils.CommonUtils;
import com.qianzibi.utils.ExcelUtils;
import com.qianzibi.utils.VerifyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
* @author 86158
* @description 针对表【exam_question(考试题目)】的数据库操作Service实现
* @createDate 2025-02-04 21:32:08
*/
@Service
public class ExamQuestionServiceImpl extends ServiceImpl<ExamQuestionMapper, ExamQuestion>
    implements ExamQuestionService{

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private ExamQuestionMapper examQuestionMapper;

    @Resource
    private ExamQuestionItemMapper examQuestionItemMapper;

    @Resource
    private CategoryService categoryService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveExamQuestion(List<ExamQuestionItem> examQuestionItemList, ExamQuestion examQuestion, boolean isSuperAdmin) {
        Category category = categoryMapper.selectById(examQuestion.getCategoryId());
        if (category == null) {
            throw new BusinessException(ResultCode.ERROR_600, "数据类型错误");
        }
        if (examQuestion.getQuestionId() == null) {
            examQuestion.setCategoryName(category.getCategoryName());
            examQuestionMapper.insert(examQuestion);
        } else {
            ExamQuestion dbInfo = this.examQuestionMapper.selectById(examQuestion.getQuestionId());
            if (!dbInfo.getCreateUserId().equals(examQuestion.getCreateUserId()) && !isSuperAdmin) {
                throw new BusinessException(ResultCode.ERROR_600, "网络异常");
            }
            examQuestion.setQuestionType(null);
            examQuestion.setCreateUserId(null);
            examQuestion.setCreateUserName(null);
            examQuestionMapper.updateById(examQuestion);
        }
        examQuestionItemList.forEach(item -> item.setQuestionId(examQuestion.getQuestionId()));
        /*
          item.getItemId()!=null说明是需要更新的选项
         */
        List<ExamQuestionItem> updateItemList = examQuestionItemList.stream().filter(item -> item.getItemId() != null).collect(Collectors.toList());
        /*
          item.getItemId()==null说明是需要新增的选项
         */
        List<ExamQuestionItem> addItemList = examQuestionItemList.stream().filter(item -> item.getItemId() == null).collect(Collectors.toList());

        // 将要更新的选项列表转换为 Map，以选项 ID 为键，选项对象为值
        Map<Integer, ExamQuestionItem> paramItemMap = updateItemList.stream().collect(Collectors.toMap(ExamQuestionItem::getItemId, Function.identity(), (data1, data2) -> data2));
        // 查询数据库中已存在的问题选项列表
        List<ExamQuestionItem> dbExamQuestionItems = examQuestionItemMapper.selectList(new QueryWrapper<ExamQuestionItem>().eq("question_id", examQuestion.getQuestionId()));
        //构建删除队列
        ArrayList<Integer> delList = new ArrayList<>();
        //如果paramItemMap中没有db中的数据,则说明id=db.getItemId()是需要被删除的选项
        if (!paramItemMap.isEmpty()) {
            for (ExamQuestionItem db : dbExamQuestionItems) {
                if (paramItemMap.get(db.getItemId()) == null) {
                    delList.add(db.getItemId());
                }
            }
        }
        // 新增
        if (!addItemList.isEmpty()) {
            examQuestionItemMapper.insertBatchSomeColumn(addItemList);
        }
        // 修改
        for (ExamQuestionItem item : updateItemList) {
            examQuestionItemMapper.updateById(item);
        }
        // 删除
        if (!delList.isEmpty()) {
            examQuestionItemMapper.deleteBatchIds(delList);
        }
    }

    /**
     * 批量更新
     */
    @Override
    public void updateBatch(ExamQuestionQuery query, ExamQuestion examQuestion) {
        List<String> list = Arrays.asList(query.getQuestionIds());
        examQuestionMapper.updateBatchStatus(list, examQuestion);
    }

    /**
     * 删除
     * userId是当前登录用户的用户ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeExamQuestion(List<String> questionIds, Integer userId) {
        if (userId != null) {
            List<ExamQuestion> examQuestions = examQuestionMapper.selectList(new QueryWrapper<ExamQuestion>().select("question_id", "create_user_id").in("question_id", questionIds));
            List<ExamQuestion> currentUserDataList = examQuestions.stream().filter(a -> !a.getCreateUserId().equals(String.valueOf(userId))).collect(Collectors.toList());
            if (!currentUserDataList.isEmpty()) {
                throw new BusinessException(ResultCode.ERROR_NAN, "删除列表中包含非当前用户创建的数据");
            }
        }
        QueryWrapper<ExamQuestion> queryWrapper = new QueryWrapper<>();
        // 没发布的才可以删除
        queryWrapper.eq("status", PostStatusEnum.NO_POST.getStatus());
        queryWrapper.in("question_id", questionIds);
        examQuestionMapper.delete(queryWrapper);
        QueryWrapper<ExamQuestionItem> questionItemQueryWrapper = new QueryWrapper<>();
        questionItemQueryWrapper.in("question_id", questionIds);
        examQuestionItemMapper.delete(questionItemQueryWrapper);
    }

    /**
     * 处理导入
     */
    @Override
    public List<ImportErrorItem> importExamQuestion(MultipartFile file, SessionUserAdminDto sessionUserAdminDto) {
        List<Category> categoryList = categoryService.loadAllCategoryByType(CategoryTypeEnum.EXAM);
        //以分类名字为键,分类对象为值
        Map<String, Category> categoryMap = categoryList.stream().collect(Collectors.toMap(Category::getCategoryName, Function.identity(), (data1, data2) -> data2));
        //读取excel数据生成列表
        List<List<String>> dataList = ExcelUtils.readExcel(file, Constants.EXCEL_TITLE_EXAM_QUESTION, 1);
        //从第三行开始获取数据,第一行为备注,第二行为标题(index=1,表示第二行)
        Integer dataRowNum = 2;

        /*
          错误列
         */
        List<ImportErrorItem> errorList = new ArrayList<>();
        /*
          数据列
         */
        List<ExamQuestion> examQuestionList = new ArrayList<>();
        // row: 行
        for (List<String> row : dataList) {
            if (errorList.size() > Constants.LENGTH_50) {
                throw new BusinessException(ResultCode.ERROR_600, "错误数据超过" + Constants.LENGTH_50 + "行，请认真检查数据后再导入");
            }
            dataRowNum++;

            List<String> errorItemList = new ArrayList<>();
            int index = 0;

            /*
              标题
             */
            String title = row.get(index++);
            if (CommonUtils.isEmpty(title) || title.length() > Constants.LENGTH_150) {
                errorItemList.add("标题不能为空，且长度不能超过" + Constants.LENGTH_150);
            }

            /*
              分类
             */
            String categoryName = row.get(index++);
            Category category = categoryMap.get(categoryName);
            if (category == null) {
                errorItemList.add("分类名称不存在");
            }

            /*
              难度
             */
            String difficultyLevel = row.get(index++);
            Integer difficultyLevelInt = null;
            if (VerifyUtils.verify(VerifyRegexEnum.POSITIVE_INTEGER, difficultyLevel)) {
                difficultyLevelInt = Integer.parseInt(difficultyLevel);
                if (difficultyLevelInt > 5) {
                    errorItemList.add("问题难度不能大于5");
                }
            } else {
                errorItemList.add("问题难度必须是整数");
            }

            /*
              问题类型
             */
            String questionType = row.get(index++);
            QuestionTypeEnum typeEnum = QuestionTypeEnum.getByDesc(questionType);
            if (typeEnum == null) {
                errorItemList.add("问题类型错误");
            }

            /*
              问题选项
             */
            String questionItem = row.get(index++);
            if (typeEnum != null && typeEnum != QuestionTypeEnum.TRUE_FALSE && !StringUtils.hasText(questionItem)) {
                errorItemList.add("问题选项为空");
            }
            List<String> examQuestionTitleList = new ArrayList<>();
            if (StringUtils.hasText(questionItem)) {
                examQuestionTitleList = Arrays.asList(questionItem.split("\\n"));
                /*
                  去除空格
                 */
                examQuestionTitleList = examQuestionTitleList.stream().filter(item -> !CommonUtils.isEmpty(item.trim())).collect(Collectors.toList());
            }
            /*
              答案
             */
            String questionAnswer = row.get(index++);
            if (StringUtils.hasText(questionAnswer) && typeEnum != null) {
                switch (typeEnum) {
                    case TRUE_FALSE:
                        /*
                          Constants.TRUE_STR="正确",Constants.ONE_STR=1.即是1=正确
                          Constants.FALSE_STR="错误",Constants.ZERO_STR=1.即是0=错误
                         */
                        if (Constants.TRUE_STR.equals(questionAnswer)) {
                            questionAnswer = Constants.ONE_STR;
                        } else if (Constants.FALSE_STR.equals(questionAnswer)) {
                            questionAnswer = Constants.ZERO_STR;
                        } else {
                            errorItemList.add("判断题答案只能是正确或者错误");
                        }
                        break;
                    case RADIO:
                        /*
                          radioAnswerIndex:根据正确选项拿到的下标
                          Arrays.binarySearch()方法与其名字一样，是一个二分查找的方法。
                          通过二分法查找已排序数组，并返回其下标。
                         */
                        Integer radioAnswerIndex = Arrays.binarySearch(Constants.LETTERS, questionAnswer);
                        if (radioAnswerIndex >= 0 && radioAnswerIndex < examQuestionTitleList.size()) {
                            questionAnswer = String.valueOf(radioAnswerIndex);
                        } else {
                            errorItemList.add("题目答案不正确");
                        }
                        break;
                    case CHECK_BOX:
                        StringBuilder answerBuilder = new StringBuilder();
                        questionAnswer = questionAnswer.replace("\n", "");
                        String[] answerArray = questionAnswer.split("、");
                        Boolean answerOk = true;
                        for (String answerItem : answerArray) {
                            answerItem = answerItem.trim();
                            Integer checkBoxAnswerIndex = Arrays.binarySearch(Constants.LETTERS, answerItem);
                            if (checkBoxAnswerIndex >= 0 && checkBoxAnswerIndex < examQuestionTitleList.size()) {
                                answerBuilder.append(checkBoxAnswerIndex).append(",");
                            } else {
                                answerOk = false;
                                break;
                            }
                        }
                        if (answerOk) {
                            questionAnswer = answerBuilder.toString();
                            questionAnswer = questionAnswer.substring(0, questionAnswer.lastIndexOf(","));
                        } else {
                            errorItemList.add("题目答案不正确");
                        }
                        break;
                }
            } else if (CommonUtils.isEmpty(questionAnswer)) {
                errorItemList.add("答案不能为空");
            }
            /*
              答案解析
             */
            String question = row.get(index++);
            String answerAnalysis = row.get(index++);
            if (CommonUtils.isEmpty(answerAnalysis)) {
                errorItemList.add("答案解析不能为空");
            }

            if (!errorItemList.isEmpty() || !errorList.isEmpty()) {
                ImportErrorItem errorItem = new ImportErrorItem();
                errorItem.setRowNum(dataRowNum);
                errorItem.setErrorItemList(errorItemList);
                errorList.add(errorItem);
                continue;
            }
            //保存问题
            ExamQuestion examQuestion = new ExamQuestion();
            examQuestion.setTitle(title);
            examQuestion.setCategoryId(category.getCategoryId());
            examQuestion.setCategoryName(category.getCategoryName());
            examQuestion.setDifficultyLevel(difficultyLevelInt);
            examQuestion.setQuestionType(typeEnum.getType());
            examQuestion.setQuestion(question);
            examQuestion.setQuestionAnswer(questionAnswer);
            examQuestion.setAnswerAnalysis(answerAnalysis);
            examQuestion.setCreateTime(new Date());
            examQuestion.setStatus(PostStatusEnum.NO_POST.getStatus());
            examQuestion.setCreateUserId(String.valueOf(sessionUserAdminDto.getUserId()));
            examQuestion.setCreateUserName(sessionUserAdminDto.getUserName());

            /*
              保存问题选项
              Collections.sort自定义排序
             */
            Collections.sort(examQuestionTitleList);
            List<ExamQuestionItem> itemList = new ArrayList<>();
            Integer sort = 0;
            for (String titleItem : examQuestionTitleList) {
                ExamQuestionItem examQuestionItem = new ExamQuestionItem();
                titleItem = titleItem.substring(titleItem.indexOf("、") + 1);
                examQuestionItem.setTitle(titleItem);
                examQuestionItem.setSort(++sort);
                itemList.add(examQuestionItem);
            }
            examQuestion.setQuestionItemList(itemList);
            examQuestionList.add(examQuestion);
        }
        if (examQuestionList.isEmpty()) {
            return errorList;
        }
        examQuestionMapper.insertBatchSomeColumn(examQuestionList);
        List<ExamQuestionItem> allExamQuestionItemList = new ArrayList<>();
        //遍历问题队列的每一个问题
        for (ExamQuestion examQuestion : examQuestionList) {
            //通过每一个问题获取questionId,为每一个选项设置questionId用于保存
            for (ExamQuestionItem item : examQuestion.getQuestionItemList()) {
                item.setQuestionId(examQuestion.getQuestionId());
            }
            //用于保存所有选项的队列
            allExamQuestionItemList.addAll(examQuestion.getQuestionItemList());
        }
        if (!allExamQuestionItemList.isEmpty()) {
            this.examQuestionItemMapper.insertBatchSomeColumn(allExamQuestionItemList);
        }
        return errorList;
    }

    @Override
    public ExamQuestion showDetailNext(ExamQuestionQuery query, Integer nextType, Integer currentId, boolean updateReadCount) {

        QueryWrapper<ExamQuestion> queryWrapper = new QueryWrapper<>();
        if (nextType == null) {
            queryWrapper.eq("question_id", currentId);
        } else if (nextType == -1) {
            queryWrapper.lt("question_id", currentId);
            queryWrapper.orderByDesc("question_id");
        } else if (nextType == 1) {
            queryWrapper.gt("question_id", currentId);
            queryWrapper.orderByAsc("question_id");
        }
        queryWrapper.last("limit 0,1");
        ExamQuestion examQuestion = examQuestionMapper.selectOne(queryWrapper);
        if (examQuestion == null && nextType == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "内容不存在");
        } else if (examQuestion == null && nextType == -1) {
            throw new BusinessException(ResultCode.ERROR_OTHER, "已经是第一条了");
        } else if (examQuestion == null && nextType == 1) {
            throw new BusinessException(ResultCode.ERROR_OTHER, "已经是最后一条了");
        }

        return examQuestion;
    }
}




