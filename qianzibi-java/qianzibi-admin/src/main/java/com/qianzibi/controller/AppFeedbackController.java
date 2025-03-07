package com.qianzibi.controller;

import com.qianzibi.annotation.Check;
import com.qianzibi.annotation.VerifyParam;
import com.qianzibi.entity.constants.Constants;
import com.qianzibi.entity.dto.SessionUserAdminDto;
import com.qianzibi.entity.enums.PermissionCodeEnum;
import com.qianzibi.entity.po.AppFeedback;
import com.qianzibi.entity.query.AppFeedbackQuery;
import com.qianzibi.service.AppFeedbackService;
import com.qianzibi.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/appFeedback")
public class AppFeedbackController {

    @Resource
    private AppFeedbackService appFeedbackService;

    @PostMapping("loadFeedback")
    @Check
    public R loadFeedback(AppFeedbackQuery query) {
        return R.ok().data(appFeedbackService.findListByPage(query));
    }

    @PostMapping("loadFeedbackReply")
    @Check(permissionCode = PermissionCodeEnum.APP_FEEDBACK_REPLY)
    public R replyFeedback(@VerifyParam(required = true) @RequestParam(value = "pFeedbackId") Integer feedbackId) {
        AppFeedbackQuery appFeedbackQuery = new AppFeedbackQuery();
        appFeedbackQuery.setFeedbackId(feedbackId);
        List<AppFeedback> list = appFeedbackService.getReplyFeedback(appFeedbackQuery);
        return R.ok().data(list);
    }

    @PostMapping("replyFeedback")
    @Check(permissionCode = PermissionCodeEnum.APP_FEEDBACK_REPLY)
    public R replyFeedback(HttpSession session,
                           @VerifyParam(required = true, max = 500) String content,
                           @VerifyParam(required = true) @RequestParam(value = "pFeedbackId") Integer feedbackId) {
        SessionUserAdminDto sessionUserAdminDto = (SessionUserAdminDto) session.getAttribute(Constants.SESSION_KEY);

        AppFeedbackQuery query = new AppFeedbackQuery();
        query.setNickName(sessionUserAdminDto.getUserName());
        query.setFeedbackId(feedbackId);
        query.setContent(content);
        query.setUserId(String.valueOf(sessionUserAdminDto.getUserId()));
        appFeedbackService.replyFeedbackById(query);
        return R.ok();
    }


}
