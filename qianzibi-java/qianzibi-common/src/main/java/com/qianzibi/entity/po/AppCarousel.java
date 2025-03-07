package com.qianzibi.entity.po;

import java.io.Serializable;

/**
 * app轮播
 * @TableName app_carousel
 */
public class AppCarousel implements Serializable {
    /**
     * 自增ID
     */
    private Integer carouselId;

    /**
     * 图片
     */
    private String imgPath;

    /**
     * 0:分享 1:问题 2:考题 3:外部
     */
    private Integer objectType;

    /**
     * 文章ID
     */
    private String objectId;

    /**
     * 外部连接
     */
    private String outerLink;

    /**
     * 排序
     */
    private Integer sort;

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    public Integer getCarouselId() {
        return carouselId;
    }

    /**
     * 自增ID
     */
    public void setCarouselId(Integer carouselId) {
        this.carouselId = carouselId;
    }

    /**
     * 图片
     */
    public String getImgPath() {
        return imgPath;
    }

    /**
     * 图片
     */
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    /**
     * 0:分享 1:问题 2:考题 3:外部
     */
    public Integer getObjectType() {
        return objectType;
    }

    /**
     * 0:分享 1:问题 2:考题 3:外部
     */
    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
    }

    /**
     * 文章ID
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * 文章ID
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * 外部连接
     */
    public String getOuterLink() {
        return outerLink;
    }

    /**
     * 外部连接
     */
    public void setOuterLink(String outerLink) {
        this.outerLink = outerLink;
    }

    /**
     * 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
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
        AppCarousel other = (AppCarousel) that;
        return (this.getCarouselId() == null ? other.getCarouselId() == null : this.getCarouselId().equals(other.getCarouselId()))
            && (this.getImgPath() == null ? other.getImgPath() == null : this.getImgPath().equals(other.getImgPath()))
            && (this.getObjectType() == null ? other.getObjectType() == null : this.getObjectType().equals(other.getObjectType()))
            && (this.getObjectId() == null ? other.getObjectId() == null : this.getObjectId().equals(other.getObjectId()))
            && (this.getOuterLink() == null ? other.getOuterLink() == null : this.getOuterLink().equals(other.getOuterLink()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCarouselId() == null) ? 0 : getCarouselId().hashCode());
        result = prime * result + ((getImgPath() == null) ? 0 : getImgPath().hashCode());
        result = prime * result + ((getObjectType() == null) ? 0 : getObjectType().hashCode());
        result = prime * result + ((getObjectId() == null) ? 0 : getObjectId().hashCode());
        result = prime * result + ((getOuterLink() == null) ? 0 : getOuterLink().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", carouselId=").append(carouselId);
        sb.append(", imgPath=").append(imgPath);
        sb.append(", objectType=").append(objectType);
        sb.append(", objectId=").append(objectId);
        sb.append(", outerLink=").append(outerLink);
        sb.append(", sort=").append(sort);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}