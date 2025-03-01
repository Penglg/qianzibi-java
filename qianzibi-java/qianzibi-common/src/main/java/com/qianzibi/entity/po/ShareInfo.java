package com.qianzibi.entity.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章
 * @TableName share_info
 */
public class ShareInfo implements Serializable {
    /**
     * 自增ID
     */
    private Integer shareId;

    /**
     * 标题
     */
    private String title;

    /**
     * 0:无封面 1:横幅 2:小图标
     */
    private Integer coverType;

    /**
     * 封面路径
     */
    private String coverPath;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 0:未发布 1:已发布
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
     * 阐读数量
     */
    private Integer readCount;

    /**
     * 收藏数
     */
    private Integer collectCount;

    /**
     * 0:内部1:外部投稿
     */
    private Integer postUserType;

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    public Integer getShareId() {
        return shareId;
    }

    /**
     * 自增ID
     */
    public void setShareId(Integer shareId) {
        this.shareId = shareId;
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
     * 0:无封面 1:横幅 2:小图标
     */
    public Integer getCoverType() {
        return coverType;
    }

    /**
     * 0:无封面 1:横幅 2:小图标
     */
    public void setCoverType(Integer coverType) {
        this.coverType = coverType;
    }

    /**
     * 封面路径
     */
    public String getCoverPath() {
        return coverPath;
    }

    /**
     * 封面路径
     */
    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    /**
     * 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 内容
     */
    public void setContent(String content) {
        this.content = content;
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
     * 0:未发布 1:已发布
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 0:未发布 1:已发布
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
     * 阐读数量
     */
    public Integer getReadCount() {
        return readCount;
    }

    /**
     * 阐读数量
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
     * 0:内部1:外部投稿
     */
    public Integer getPostUserType() {
        return postUserType;
    }

    /**
     * 0:内部1:外部投稿
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
        ShareInfo other = (ShareInfo) that;
        return (this.getShareId() == null ? other.getShareId() == null : this.getShareId().equals(other.getShareId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getCoverType() == null ? other.getCoverType() == null : this.getCoverType().equals(other.getCoverType()))
            && (this.getCoverPath() == null ? other.getCoverPath() == null : this.getCoverPath().equals(other.getCoverPath()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
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
        result = prime * result + ((getShareId() == null) ? 0 : getShareId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getCoverType() == null) ? 0 : getCoverType().hashCode());
        result = prime * result + ((getCoverPath() == null) ? 0 : getCoverPath().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
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
        sb.append(", shareId=").append(shareId);
        sb.append(", title=").append(title);
        sb.append(", coverType=").append(coverType);
        sb.append(", coverPath=").append(coverPath);
        sb.append(", content=").append(content);
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