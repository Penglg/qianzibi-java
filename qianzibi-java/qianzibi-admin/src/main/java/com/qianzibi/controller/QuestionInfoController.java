package com.qianzibi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianzibi.annotation.Check;
import com.qianzibi.annotation.VerifyParam;
import com.qianzibi.entity.constants.Constants;
import com.qianzibi.entity.dto.ImportErrorItem;
import com.qianzibi.entity.dto.SessionUserAdminDto;
import com.qianzibi.entity.enums.PermissionCodeEnum;
import com.qianzibi.entity.enums.PostStatusEnum;
import com.qianzibi.entity.po.QuestionInfo;
import com.qianzibi.entity.query.QuestionInfoQuery;
import com.qianzibi.service.QuestionInfoService;
import com.qianzibi.utils.CommonUtils;
import com.qianzibi.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 问题控制器
 */
@RestController
@RequestMapping("/questionInfo")
public class QuestionInfoController {

    @Resource
    private QuestionInfoService questionInfoService;

    @PostMapping("/loadDataList")
    @Check(permissionCode = PermissionCodeEnum.QUESTION_LIST)
    public R LoadDataList(QuestionInfoQuery query) {
        QuestionInfo questionInfo = new QuestionInfo();
        BeanUtils.copyProperties(query, questionInfo);
        LambdaQueryWrapper<QuestionInfo> queryWrapper = new LambdaQueryWrapper<>(questionInfo);
        queryWrapper.like(query.getTitleFuzzy() != null, QuestionInfo::getTitle, query.getTitleFuzzy());
        queryWrapper.like(query.getCreateUserNameFuzzy() != null, QuestionInfo::getCreateUserName, query.getCreateUserNameFuzzy());
        queryWrapper.eq(query.getDifficultyLevel() != null, QuestionInfo::getDifficultyLevel, query.getDifficultyLevel());
        queryWrapper.eq(query.getStatus() != null, QuestionInfo::getStatus, query.getStatus());
        queryWrapper.eq(query.getCategoryId() != null, QuestionInfo::getCategoryId, query.getCategoryId());
        queryWrapper.orderByDesc(QuestionInfo::getQuestionId);
        return R.ok().data(questionInfoService.page(new Page<>(query.getCurrent(), query.getPageSize()), queryWrapper));
    }

    @PostMapping("/saveQuestionInfo")
    @Check(permissionCode = PermissionCodeEnum.QUESTION_EDIT)
    public R saveQuestionInfo(HttpSession session, QuestionInfo questionInfo) {
        SessionUserAdminDto sessionUserAdminDto = (SessionUserAdminDto) session.getAttribute(Constants.SESSION_KEY);
        questionInfo.setCreateUserName(sessionUserAdminDto.getUserName());
        questionInfo.setCreateUserId(String.valueOf(sessionUserAdminDto.getUserId()));
        questionInfoService.saveOrUpdateQIF(sessionUserAdminDto.getSuperAdmin(), questionInfo);
        return R.ok();
    }

    /**
     * 查看Quesion详细时跳转上一条或下一条
      */
    @PostMapping("/showQuestionDetailNext")
    @Check(permissionCode = PermissionCodeEnum.QUESTION_LIST)
    public R showQuestionDetailNext(QuestionInfoQuery query, Integer nextType,
                                    @VerifyParam(required = true) Integer currentId) {
        QuestionInfo questionInfo = questionInfoService.showDetailNext(query, nextType, currentId, false);
        return R.ok().data(questionInfo);
    }


    @RequestMapping("/delQuestion")
    @Check(permissionCode = PermissionCodeEnum.QUESTION_DEL)
    public R delQuestion(HttpSession session, @VerifyParam(required = true) Integer questionId) {
        SessionUserAdminDto sessionUserAdminDto = (SessionUserAdminDto) session.getAttribute(Constants.SESSION_KEY);
        questionInfoService.removeBatchQIF(String.valueOf(questionId), sessionUserAdminDto.getSuperAdmin() ? null : sessionUserAdminDto.getUserId());
        return R.ok();
    }

    /**
     * 批量删除
     */
    @RequestMapping("/delQuestionBatch")
    @Check(permissionCode = PermissionCodeEnum.QUESTION_DEL_BATCH)
    public R delQuestionBatch(@VerifyParam(required = true) String questionIds) {
        questionInfoService.removeBatchQIF(questionIds, null);
        return R.ok();
    }

    /**
     * 发布问题
     * questionIds:需要发布的questionId
     */
    @RequestMapping("/postQuestion")
    @Check(permissionCode = PermissionCodeEnum.QUESTION_POST)
    public R postQuestion(@VerifyParam(required = true) String questionIds) {
        updateStatus(questionIds, PostStatusEnum.POST.getStatus());
        return R.ok();
    }

    /**
     * 取消发布问题
     */
    @PostMapping("/cancelPostQuestion")
    @Check(permissionCode = PermissionCodeEnum.QUESTION_POST)
    public R cancelPostQuestion(@VerifyParam(required = true) String questionIds) {
        updateStatus(questionIds, PostStatusEnum.NO_POST.getStatus());
        return R.ok();
    }

    /**
     * 更新问题发布状态
     */
    private void updateStatus(String questionIds, Integer status) {
        QuestionInfoQuery queryParams = new QuestionInfoQuery();
        queryParams.setQuestionIds(questionIds.split(","));
        QuestionInfo questionInfo = new QuestionInfo();
        questionInfo.setStatus(status);
        CommonUtils.checkParam(queryParams);
        questionInfoService.updateBatchByQIFId(questionInfo, queryParams);
    }

    @RequestMapping("/importQuestion")
    @Check(permissionCode = PermissionCodeEnum.QUESTION_IMPORT)
    public R importQuestion(HttpSession session, MultipartFile file) {
        SessionUserAdminDto sessionUserAdminDto = (SessionUserAdminDto) session.getAttribute(Constants.SESSION_KEY);
        List<ImportErrorItem> list = questionInfoService.importQuestion(file, sessionUserAdminDto);
        return R.ok().data(list);
    }

}