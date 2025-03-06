package com.qianzibi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianzibi.annotation.Check;
import com.qianzibi.annotation.VerifyParam;
import com.qianzibi.common.ResultCode;
import com.qianzibi.entity.constants.Constants;
import com.qianzibi.entity.dto.ImportErrorItem;
import com.qianzibi.entity.dto.SessionUserAdminDto;
import com.qianzibi.entity.enums.PermissionCodeEnum;
import com.qianzibi.entity.enums.PostStatusEnum;
import com.qianzibi.entity.enums.QuestionTypeEnum;
import com.qianzibi.entity.po.ExamQuestion;
import com.qianzibi.entity.po.ExamQuestionItem;
import com.qianzibi.entity.query.ExamQuestionQuery;
import com.qianzibi.exception.BusinessException;
import com.qianzibi.service.ExamQuestionItemService;
import com.qianzibi.service.ExamQuestionService;
import com.qianzibi.utils.CommonUtils;
import com.qianzibi.utils.JsonUtils;
import com.qianzibi.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/examQuestion")
public class ExamQuestionController {

    @Resource
    private ExamQuestionService examQuestionService;

    @Resource
    private ExamQuestionItemService examQuestionItemService;

    /**
     * 加载
     */
    @PostMapping("/loadDataList")
    @Check(permissionCode = PermissionCodeEnum.EXAM_QUESTION_LIST)
    public R loadDataList(ExamQuestionQuery query) {
        // TODO：原本用com.qianzibi.entity.query.BaseParam来进行分页查询
//        PaginationResultVO<ExamQuestion> listByPage = examQuestionService.findListByPage(query);

        ExamQuestion examQuestion = new ExamQuestion();
        BeanUtils.copyProperties(query, examQuestion);
        LambdaQueryWrapper<ExamQuestion> queryWrapper = new LambdaQueryWrapper<>(examQuestion);
        queryWrapper.orderByAsc(ExamQuestion::getQuestionId);
        queryWrapper.like(query.getTitleFuzzy() != null, ExamQuestion::getTitle, query.getTitleFuzzy());
        queryWrapper.like(query.getCreateUserNameFuzzy() != null, ExamQuestion::getCreateUserName, query.getCreateUserNameFuzzy());
        queryWrapper.eq(query.getDifficultyLevel() != null, ExamQuestion::getDifficultyLevel, query.getDifficultyLevel());
        queryWrapper.eq(query.getStatus() != null, ExamQuestion::getStatus, query.getStatus());
        queryWrapper.eq(query.getCategoryId() != null, ExamQuestion::getCategoryId, query.getCategoryId());
        queryWrapper.eq(query.getQuestionType() != null, ExamQuestion::getQuestionType, query.getQuestionType());
        return R.ok().data(examQuestionService.page(new Page<>(query.getCurrent(), query.getPageSize()), queryWrapper));
    }

    /**
     * 保存/修改
     */
    @PostMapping("/saveExamQuestion")
    @Check(permissionCode = PermissionCodeEnum.EXAM_QUESTION_EDIT)
    public R saveExamQuestion(HttpSession session,
                              @VerifyParam(required = true) ExamQuestion examQuestion,
                              String questionItemListJson) {
        // 设置创建者信息
        SessionUserAdminDto sessionUserAdminDto = (SessionUserAdminDto) session.getAttribute(Constants.SESSION_KEY);
        examQuestion.setCreateUserId(String.valueOf(sessionUserAdminDto.getUserId()));
        examQuestion.setCreateUserName(sessionUserAdminDto.getUserName());
        List<ExamQuestionItem> examQuestionItemList = new ArrayList<>();
        // 判断题
        if (!QuestionTypeEnum.TRUE_FALSE.getType().equals(examQuestion.getQuestionType())) {
            if (CommonUtils.isEmpty(questionItemListJson)) {
                throw new BusinessException(ResultCode.ERROR_600, "参数类型错误");
            }
            examQuestionItemList = JsonUtils.convertJsonArray2List(questionItemListJson, ExamQuestionItem.class);
        }
        examQuestionService.saveExamQuestion(examQuestionItemList, examQuestion, sessionUserAdminDto.getSuperAdmin());
        return R.ok();
    }

    /**
     * 加载选项
     */
    @RequestMapping("/loadQuestionItem")
    @Check(permissionCode = PermissionCodeEnum.EXAM_QUESTION_EDIT)
    public R loadQuestionItem(@VerifyParam(required = true) Integer questionId) {
        QueryWrapper<ExamQuestionItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("question_id", questionId);
        List<ExamQuestionItem> listByQuestionId = examQuestionItemService.list(queryWrapper);
        return R.ok().data(listByQuestionId);
    }

    /**
     * 发布
     */
    @PostMapping("/postExamQuestion")
    @Check(permissionCode = PermissionCodeEnum.EXAM_QUESTION_DEL)
    public R postExamQuestion(@VerifyParam(required = true) String questionIds) {
        updateStatus(questionIds, PostStatusEnum.POST.getStatus());
        return R.ok();
    }

    /**
     * 取消发布
     */
    @PostMapping("/cancelPostExamQuestion")
    @Check(permissionCode = PermissionCodeEnum.EXAM_QUESTION_DEL)
    public R cancelPostExamQuestion(@VerifyParam(required = true) String questionIds) {
        updateStatus(questionIds, PostStatusEnum.NO_POST.getStatus());
        return R.ok();
    }

    /**
     * 更新发布状态
     */
    private void updateStatus(String questionIds, Integer status) {
        ExamQuestionQuery query = new ExamQuestionQuery();
        query.setQuestionIds(questionIds.split(","));
        ExamQuestion examQuestion = new ExamQuestion();
        examQuestion.setStatus(status);
        examQuestionService.updateBatch(query, examQuestion);
    }

    /**
     * 根据id删除
     */
    @PostMapping("/delExamQuestion")
    @Check(permissionCode = PermissionCodeEnum.EXAM_QUESTION_DEL)
    public R delExamQuestion(HttpSession session, @VerifyParam(required = true) Integer questionId) {
        SessionUserAdminDto sessionUserAdminDto = (SessionUserAdminDto) session.getAttribute(Constants.SESSION_KEY);
        List<String> questionIds = new ArrayList<>();
        questionIds.add(String.valueOf(questionId));
        examQuestionService.removeExamQuestion(questionIds, sessionUserAdminDto.getSuperAdmin() ? null : sessionUserAdminDto.getUserId());
        return R.ok();
    }

    /**
     * 批量删除
     */
    @PostMapping("/delExamQuestionBatch")
    @Check(permissionCode = PermissionCodeEnum.EXAM_QUESTION_DEL_BATCH)
    public R delExamQuestionBatch(HttpSession session, @VerifyParam(required = true) String questionIds) {
        SessionUserAdminDto sessionUserAdminDto = (SessionUserAdminDto) session.getAttribute(Constants.SESSION_KEY);
        List<String> questionIdList = Arrays.asList(questionIds.split(","));
        examQuestionService.removeExamQuestion(questionIdList, sessionUserAdminDto.getSuperAdmin() ? null : sessionUserAdminDto.getUserId());
        return R.ok();
    }

    /**
     * 导入
     */
    @RequestMapping("/importExamQuestion")
    @Check(permissionCode = PermissionCodeEnum.EXAM_QUESTION_IMPORT)
    public R importExamQuestion(HttpSession session, MultipartFile file) {
        SessionUserAdminDto sessionUserAdminDto = (SessionUserAdminDto) session.getAttribute(Constants.SESSION_KEY);
        List<ImportErrorItem> importErrorItems = examQuestionService.importExamQuestion(file, sessionUserAdminDto);
        return R.ok().data(importErrorItems);
    }

    /**
     * 上一条/下一条切换
     */
    @PostMapping("/showExamQuestionDetailNext")
    @Check(permissionCode = PermissionCodeEnum.QUESTION_LIST)
    public R showExamQuestionDetailNext(ExamQuestionQuery query, Integer nextType,
                                        @VerifyParam(required = true) Integer currentId) {
        ExamQuestion examQuestion = examQuestionService.showDetailNext(query, nextType, currentId, false);
        return R.ok().data(examQuestion);
    }
}
