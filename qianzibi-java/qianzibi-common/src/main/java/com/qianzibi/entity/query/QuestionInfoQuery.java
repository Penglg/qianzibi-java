package com.qianzibi.entity.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 问题参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionInfoQuery extends PageRequest implements Serializable {


    /**
     * ID
     */
    private Integer questionId;

    /**
     * 标题
     */
    private String title;

    private String titleFuzzy;

    /**
     * 分类ID
     */
    private Integer categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    private String categoryNameFuzzy;

    /**
     * 难度
     */
    private Integer difficultyLevel;

    /**
     * 问题描述
     */
    private String question;

    private String questionFuzzy;

    /**
     * 回答解释
     */
    private String answerAnalysis;

    private String answerAnalysisFuzzy;

    /**
     * 创建时间
     */
    private String createTime;

    private String createTimeStart;

    private String createTimeEnd;

    /**
     * 0:未发布 1:已发布
     */
    private Integer status;

    /**
     * 用户ID
     */
    private String createUserId;

    private String createUserIdFuzzy;

    /**
     * 姓名
     */
    private String createUserName;

    private String createUserNameFuzzy;

    /**
     * 阅读数量
     */
    private Integer readCount;

    /**
     * 收藏数
     */
    private Integer collectCount;

    /**
     * 0:内部 1:外部投稿
     */
    private Integer postUserType;
    /**
     * true:返回 question 和 answerAnalysis 字段 false:不返回 question 和 answerAnalysis字段
     */
    private Boolean queryTextContent;

    private String[] questionIds;

    private Integer nextType;

    private Integer currentId;

    private static final long serialVersionUID = 1L;
}
