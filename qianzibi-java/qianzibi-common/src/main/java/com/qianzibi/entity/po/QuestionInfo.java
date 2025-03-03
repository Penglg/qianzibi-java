package com.qianzibi.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 问题
 * @TableName question_info
 */
@Data
public class QuestionInfo implements Serializable {
    /**
     * ID
     */
    private Integer questionId;

    /**
     * 标题
     */
    private String title;

    /**
     * 分类名称
     */
    private Integer categoryId;

    /**
     * 难度
     */
    private Integer difficultyLevel;

    /**
     * 问题描述
     */
    private String question;

    /**
     * 回答解释
     */
    private String answerAnalysis;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 0:未发布; 1:已发布
     */
    private Integer status;

    /**
     * 用户ID
     */
    private String createUserId;

    /**
     * 姓名
     */
    private String createUserName;

    /**
     * 阅读数量
     */
    private Integer readCount;

    /**
     * 收藏数
     */
    private Integer collectCount;

    /**
     * 0:内部; 1:外部投稿
     */
    private Integer postUserType;

    /**
     * 分类名称
     */
    private String categoryName;

    private static final long serialVersionUID = 1L;
}