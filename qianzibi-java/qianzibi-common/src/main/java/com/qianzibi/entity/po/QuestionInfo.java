package com.qianzibi.entity.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 问题
 * @TableName question_info
 */
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

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    public Integer getQuestionId() {
        return questionId;
    }

    /**
     * ID
     */
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    /**
     * 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 分类名称
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 分类名称
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 难度
     */
    public Integer getDifficultyLevel() {
        return difficultyLevel;
    }

    /**
     * 难度
     */
    public void setDifficultyLevel(Integer difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    /**
     * 问题描述
     */
    public String getQuestion() {
        return question;
    }

    /**
     * 问题描述
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * 回答解释
     */
    public String getAnswerAnalysis() {
        return answerAnalysis;
    }

    /**
     * 回答解释
     */
    public void setAnswerAnalysis(String answerAnalysis) {
        this.answerAnalysis = answerAnalysis;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 0:未发布; 1:已发布
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 0:未发布; 1:已发布
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 用户ID
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 用户ID
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 姓名
     */
    public String getCreateUserName() {
        return createUserName;
    }

    /**
     * 姓名
     */
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    /**
     * 阅读数量
     */
    public Integer getReadCount() {
        return readCount;
    }

    /**
     * 阅读数量
     */
    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    /**
     * 收藏数
     */
    public Integer getCollectCount() {
        return collectCount;
    }

    /**
     * 收藏数
     */
    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    /**
     * 0:内部; 1:外部投稿
     */
    public Integer getPostUserType() {
        return postUserType;
    }

    /**
     * 0:内部; 1:外部投稿
     */
    public void setPostUserType(Integer postUserType) {
        this.postUserType = postUserType;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        QuestionInfo other = (QuestionInfo) that;
        return (this.getQuestionId() == null ? other.getQuestionId() == null : this.getQuestionId().equals(other.getQuestionId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getDifficultyLevel() == null ? other.getDifficultyLevel() == null : this.getDifficultyLevel().equals(other.getDifficultyLevel()))
            && (this.getQuestion() == null ? other.getQuestion() == null : this.getQuestion().equals(other.getQuestion()))
            && (this.getAnswerAnalysis() == null ? other.getAnswerAnalysis() == null : this.getAnswerAnalysis().equals(other.getAnswerAnalysis()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateUserId() == null ? other.getCreateUserId() == null : this.getCreateUserId().equals(other.getCreateUserId()))
            && (this.getCreateUserName() == null ? other.getCreateUserName() == null : this.getCreateUserName().equals(other.getCreateUserName()))
            && (this.getReadCount() == null ? other.getReadCount() == null : this.getReadCount().equals(other.getReadCount()))
            && (this.getCollectCount() == null ? other.getCollectCount() == null : this.getCollectCount().equals(other.getCollectCount()))
            && (this.getPostUserType() == null ? other.getPostUserType() == null : this.getPostUserType().equals(other.getPostUserType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getQuestionId() == null) ? 0 : getQuestionId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getDifficultyLevel() == null) ? 0 : getDifficultyLevel().hashCode());
        result = prime * result + ((getQuestion() == null) ? 0 : getQuestion().hashCode());
        result = prime * result + ((getAnswerAnalysis() == null) ? 0 : getAnswerAnalysis().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateUserId() == null) ? 0 : getCreateUserId().hashCode());
        result = prime * result + ((getCreateUserName() == null) ? 0 : getCreateUserName().hashCode());
        result = prime * result + ((getReadCount() == null) ? 0 : getReadCount().hashCode());
        result = prime * result + ((getCollectCount() == null) ? 0 : getCollectCount().hashCode());
        result = prime * result + ((getPostUserType() == null) ? 0 : getPostUserType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", questionId=").append(questionId);
        sb.append(", title=").append(title);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", difficultyLevel=").append(difficultyLevel);
        sb.append(", question=").append(question);
        sb.append(", answerAnalysis=").append(answerAnalysis);
        sb.append(", createTime=").append(createTime);
        sb.append(", status=").append(status);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", createUserName=").append(createUserName);
        sb.append(", readCount=").append(readCount);
        sb.append(", collectCount=").append(collectCount);
        sb.append(", postUserType=").append(postUserType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}