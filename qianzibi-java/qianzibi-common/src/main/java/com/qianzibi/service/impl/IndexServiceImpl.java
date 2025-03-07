package com.qianzibi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qianzibi.entity.dto.StatisticsDataDto;
import com.qianzibi.entity.dto.StatisticsDataWeekDto;
import com.qianzibi.entity.enums.DateTimePatternEnum;
import com.qianzibi.entity.enums.FeedbackSendTypeEnum;
import com.qianzibi.entity.enums.StatisticsDateTypeEnum;
import com.qianzibi.entity.po.*;
import com.qianzibi.mapper.*;
import com.qianzibi.service.IndexService;
import com.qianzibi.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {

    @Resource
    private AppDeviceMapper appDeviceMapper;

    @Resource
    private AppUserInfoMapper appUserInfoMapper;

    @Resource
    private QuestionInfoMapper questionInfoMapper;

    @Resource
    private ExamQuestionMapper examQuestionMapper;

    @Resource
    private ShareInfoMapper shareInfoMapper;

    @Resource
    private AppFeedbackMapper appFeedbackMapper;

    /**
     * 获取全部数据
     */
    @Override
    public List<StatisticsDataDto> getAllData() {
        String format = DateUtil.format(DateUtil.getDayAgo(1), DateTimePatternEnum.YYYY_MM_DD.getPattern());
        List<StatisticsDataDto> dataList = new ArrayList<>();
        for (StatisticsDateTypeEnum item : StatisticsDateTypeEnum.values()) {
            StatisticsDataDto dataDto = new StatisticsDataDto();
            dataDto.setStatisticsName(item.getDescription());
            switch (item) {
                case APP_DOWNLOAD:
                    Long appDownCount = appDeviceMapper.selectCount(new QueryWrapper<>());
                    dataDto.setCount(Math.toIntExact(appDownCount));
                    QueryWrapper<AppDevice> queryWrapper = new QueryWrapper<>();
                    queryWrapper.like("create_time", format);
                    Long beforeCount = appDeviceMapper.selectCount(queryWrapper);
                    dataDto.setPreCount(Math.toIntExact(beforeCount));
                    break;
                case REGISTER_USER:
                    dataDto.setCount(Math.toIntExact(appUserInfoMapper.selectCount(
                            new QueryWrapper<>())));
                    dataDto.setPreCount(Math.toIntExact(appUserInfoMapper.selectCount(
                            new QueryWrapper<AppUserInfo>().like("join_time", format))));
                    break;
                case QUESTION_INFO:
                    dataDto.setCount(Math.toIntExact(questionInfoMapper.selectCount(
                            new QueryWrapper<>())));
                    dataDto.setPreCount(Math.toIntExact(questionInfoMapper.selectCount(
                            new QueryWrapper<QuestionInfo>().like("create_time", format))));
                    break;
                case EXAM:
                    dataDto.setCount(Math.toIntExact(examQuestionMapper.selectCount(
                            new QueryWrapper<>())));
                    dataDto.setPreCount(Math.toIntExact(examQuestionMapper.selectCount(
                            new QueryWrapper<ExamQuestion>().like("create_time", format))));
                    break;
                case SHARE:
                    dataDto.setCount(Math.toIntExact(shareInfoMapper.selectCount(
                            new QueryWrapper<>())));
                    dataDto.setPreCount(Math.toIntExact(shareInfoMapper.selectCount(
                            new QueryWrapper<ShareInfo>().like("create_time", format))));
                    break;
                case FEEDBACK:
                    dataDto.setCount(Math.toIntExact(appFeedbackMapper.selectCount(
                            new QueryWrapper<AppFeedback>().eq("send_type", FeedbackSendTypeEnum.CLIENT))));
                    dataDto.setPreCount(Math.toIntExact(appFeedbackMapper.selectCount(
                            new QueryWrapper<AppFeedback>().like("create_time", format).eq("send_type", FeedbackSendTypeEnum.CLIENT))));
                    break;

            }
            dataList.add(dataDto);
        }

        return dataList;
    }

    /**
     * 获取一周的App相关数据，包括App下载统计以及用户人数注册
     */
    @Override
    public StatisticsDataWeekDto getAppWeekData() {
        List<String> days = getDays();
        StatisticsDataWeekDto weekDto = new StatisticsDataWeekDto();
        weekDto.setDateList(days);
        weekDto.setItemDataList(new ArrayList<>());
        // 下载app的统计
        StatisticsDataDto download = new StatisticsDataDto();
        download.setListData(new ArrayList<>());
        download.setStatisticsName(StatisticsDateTypeEnum.APP_DOWNLOAD.getDescription());
        weekDto.getItemDataList().add(download);
        // 注册用户人数的统计
        StatisticsDataDto register = new StatisticsDataDto();
        register.setListData(new ArrayList<>());
        register.setStatisticsName(StatisticsDateTypeEnum.REGISTER_USER.getDescription());
        weekDto.getItemDataList().add(register);

        for (String day : days) {
            QueryWrapper<AppDevice> deviceQuery = new QueryWrapper<>();
            System.out.println("debug 日期格式" + day);
            deviceQuery.like("create_time", day);
            Long deviceCount = appDeviceMapper.selectCount(deviceQuery);
            download.getListData().add(Math.toIntExact(deviceCount));

            QueryWrapper<AppUserInfo> userQuery = new QueryWrapper<>();
            userQuery.like("join_time", day);
            Long userCount = appUserInfoMapper.selectCount(userQuery);
            register.getListData().add(Math.toIntExact(userCount));
        }

        return weekDto;
    }

    /**
     * 获取一周内内容的数据，包括问题、题库、经验分享以及反馈
     */
    @Override
    public StatisticsDataWeekDto getContentWeekData() {
        List<String> days = getDays();
        StatisticsDataWeekDto weekDto = new StatisticsDataWeekDto();
        weekDto.setDateList(days);
        weekDto.setItemDataList(new ArrayList<>());

        // 问题的统计数据
        StatisticsDataDto question = new StatisticsDataDto();
        question.setListData(new ArrayList<>());
        question.setStatisticsName(StatisticsDateTypeEnum.QUESTION_INFO.getDescription());
        weekDto.getItemDataList().add(question);
        // 题库的统计数据
        StatisticsDataDto examQuestion = new StatisticsDataDto();
        examQuestion.setStatisticsName(StatisticsDateTypeEnum.EXAM.getDescription());
        examQuestion.setListData(new ArrayList<>());
        weekDto.getItemDataList().add(examQuestion);
        // 经验分享的统计数据
        StatisticsDataDto share = new StatisticsDataDto();
        share.setStatisticsName(StatisticsDateTypeEnum.SHARE.getDescription());
        share.setListData(new ArrayList<>());
        weekDto.getItemDataList().add(share);
        // 反馈的统计数据
        StatisticsDataDto feedback = new StatisticsDataDto();
        feedback.setStatisticsName(StatisticsDateTypeEnum.FEEDBACK.getDescription());
        feedback.setListData(new ArrayList<>());
        weekDto.getItemDataList().add(feedback);

        for (String day : days) {

            QueryWrapper<QuestionInfo> questionInfoQueryWrapper = new QueryWrapper<>();
            System.out.println("debug 日期格式" + day);
            questionInfoQueryWrapper.like("create_time", day);
            Long questionCount = questionInfoMapper.selectCount(questionInfoQueryWrapper);
            question.getListData().add(Math.toIntExact(questionCount));

            QueryWrapper<ExamQuestion> examQuestionQueryWrapper = new QueryWrapper<>();
            examQuestionQueryWrapper.like("create_time", day);
            Long examQuestionCount = examQuestionMapper.selectCount(examQuestionQueryWrapper);
            examQuestion.getListData().add(Math.toIntExact(examQuestionCount));

            QueryWrapper<ShareInfo> shareInfoQueryWrapper = new QueryWrapper<>();
            shareInfoQueryWrapper.like("create_time", day);
            Long shareCount = shareInfoMapper.selectCount(shareInfoQueryWrapper);
            share.getListData().add(Math.toIntExact(shareCount));

            QueryWrapper<AppFeedback> feedbackQueryWrapper = new QueryWrapper<>();
            feedbackQueryWrapper.like("create_time", day);
            Long feedbackCount = appFeedbackMapper.selectCount(feedbackQueryWrapper);
            feedback.getListData().add(Math.toIntExact(feedbackCount));
        }

        return weekDto;
    }

    /**
     * 获取一个星期的时间
     */
    private List<String> getDays() {
        Date startDate = DateUtil.getDayAgo(7);
        Date preDate = DateUtil.getDayAgo(1);
        return DateUtil.getBetweenDate(startDate, preDate);
    }
}
