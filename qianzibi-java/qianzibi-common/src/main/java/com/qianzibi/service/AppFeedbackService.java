package com.qianzibi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianzibi.entity.po.AppFeedback;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianzibi.entity.query.AppFeedbackQuery;

import java.util.List;

/**
* @author 86158
* @description 针对表【app_feedback(问题反馈)】的数据库操作Service
* @createDate 2025-03-06 23:19:08
*/
public interface AppFeedbackService extends IService<AppFeedback> {

    Page<AppFeedback> findListByPage(AppFeedbackQuery query);

    List<AppFeedback> getReplyFeedback(AppFeedbackQuery appFeedbackQuery);

    void replyFeedbackById(AppFeedbackQuery query);
}
