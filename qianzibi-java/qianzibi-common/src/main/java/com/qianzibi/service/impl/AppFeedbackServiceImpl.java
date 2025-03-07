package com.qianzibi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianzibi.entity.enums.FeedbackSendTypeEnum;
import com.qianzibi.entity.enums.FeedbackStatusEnum;
import com.qianzibi.entity.enums.ResponseCodeEnum;
import com.qianzibi.entity.po.AppFeedback;
import com.qianzibi.entity.query.AppFeedbackQuery;
import com.qianzibi.exception.BusinessException;
import com.qianzibi.mapper.AppFeedbackMapper;
import com.qianzibi.service.AppFeedbackService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author 86158
* @description 针对表【app_feedback(问题反馈)】的数据库操作Service实现
* @createDate 2025-03-06 23:19:08
*/
@Service
public class AppFeedbackServiceImpl extends ServiceImpl<AppFeedbackMapper, AppFeedback>
    implements AppFeedbackService{

    @Resource
    private AppFeedbackMapper appFeedbackMapper;

    @Override
    public Page<AppFeedback> findListByPage(AppFeedbackQuery query) {
        LambdaQueryWrapper<AppFeedback> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(query.getStatus() != null, AppFeedback::getStatus, query.getStatus());
        queryWrapper.between(query.getCreateTimeStart() != null && query.getCreateTimeEnd() != null,
                                AppFeedback::getCreateTime, query.getCreateTimeStart(), query.getCreateTimeEnd());
        queryWrapper.like(query.getNickNameFuzzy() != null, AppFeedback::getNickName, query.getNickNameFuzzy());
        queryWrapper.orderByDesc(AppFeedback::getFeedbackId);
        return appFeedbackMapper.selectPage(new Page<>(query.getCurrent(), query.getPageSize()), queryWrapper);
    }

    /**
     * 查询选中问题的 提问/回复 记录
     * AppFeedbackQuery appFeedbackQuery:包含排序方式和选中的问题id
     */
    @Override
    public List<AppFeedback> getReplyFeedback(AppFeedbackQuery appFeedbackQuery) {
        AppFeedback dbAppFeedback = appFeedbackMapper.selectById(appFeedbackQuery.getFeedbackId());
        List<AppFeedback> appFeedbacks = new ArrayList<>();
        LambdaQueryWrapper<AppFeedback> queryWrapper = new LambdaQueryWrapper<>();
        /*
          当选中问题的 pFeedbackId = 0 时代表选中的是一个新问题,查询出和他feedbackId相同的pFeedbackId的数据和他本身并加入返回的List
          当选中问题的 pFeedbackId != 0 时代表选中的是一个追问,这时直接查询这个问题的父问题,再查询和他pFeedbackId相同的其他数据,然后返回
         */
        if (dbAppFeedback.getPFeedbackId() != 0) {
            queryWrapper.eq(AppFeedback::getPFeedbackId, dbAppFeedback.getPFeedbackId()).or().eq(AppFeedback::getFeedbackId, dbAppFeedback.getPFeedbackId()).orderByAsc(AppFeedback::getFeedbackId);
            appFeedbacks = appFeedbackMapper.selectList(queryWrapper);
        } else {
            queryWrapper.eq(AppFeedback::getPFeedbackId, appFeedbackQuery.getFeedbackId()).or().eq(AppFeedback::getFeedbackId, appFeedbackQuery.getFeedbackId()).orderByAsc(AppFeedback::getFeedbackId);
            List<AppFeedback> appFeedbacks_db = appFeedbackMapper.selectList(queryWrapper);
            appFeedbacks.addAll(appFeedbacks_db);
        }
        return appFeedbacks;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void replyFeedbackById(AppFeedbackQuery query) {
        // 查询要回复的反馈问题状态
        AppFeedback dbAppFeedback = appFeedbackMapper.selectById(query.getPFeedbackId());
        if (dbAppFeedback == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        // 回复的sql设置其父id为要回复的反馈的feedbackId
        Integer pFeedbackId = dbAppFeedback.getFeedbackId();

        /*
          保存回复的反馈
         */
        AppFeedback appFeedback = new AppFeedback();
        appFeedback.setPFeedbackId(pFeedbackId);
        appFeedback.setSendType(FeedbackSendTypeEnum.ADMIN.getStatus());
        appFeedback.setNickName(query.getNickName());
        appFeedback.setStatus(FeedbackStatusEnum.NO_REPLY.getStatus());
        appFeedback.setContent(query.getContent());
        appFeedback.setUserId(query.getUserId());
        appFeedbackMapper.insert(appFeedback);

        /*
          更新父级状态
         */
        AppFeedback replyPFeedback = new AppFeedback();
        replyPFeedback.setFeedbackId(query.getPFeedbackId());
        replyPFeedback.setStatus(FeedbackStatusEnum.REPLY.getStatus());
        appFeedbackMapper.updateById(replyPFeedback);
    }
}




