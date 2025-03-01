package com.qianzibi.entity.po;

import com.qianzibi.annotation.VerifyParam;

import java.io.Serializable;

/**
 * 分类
 * @TableName category
 */
public class Category implements Serializable {
    /**
     * 分类ID
     */
    private Integer categoryId;

    /**
     * 名称
     */
    @VerifyParam(required = true)
    private String categoryName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 图标
     */
    private String iconPath;

    /**
     * 背景颜色
     */
    private String bgColor;

    /**
     * 0:问题分类; 1:考题分类; 2:问题分类和考题分类
     */
    private Integer type;

    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 分类ID
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 名称
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 名称
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    /**
     * 图标
     */
    public String getIconPath() {
        return iconPath;
    }

    /**
     * 图标
     */
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    /**
     * 背景颜色
     */
    public String getBgColor() {
        return bgColor;
    }

    /**
     * 背景颜色
     */
    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    /**
     * 0:问题分类; 1:考题分类; 2:问题分类和考题分类
     */
    public Integer getType() {
        return type;
    }

    /**
     * 0:问题分类; 1:考题分类; 2:问题分类和考题分类
     */
    public void setType(Integer type) {
        this.type = type;
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
        Category other = (Category) that;
        return (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getCategoryName() == null ? other.getCategoryName() == null : this.getCategoryName().equals(other.getCategoryName()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getIconPath() == null ? other.getIconPath() == null : this.getIconPath().equals(other.getIconPath()))
            && (this.getBgColor() == null ? other.getBgColor() == null : this.getBgColor().equals(other.getBgColor()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getCategoryName() == null) ? 0 : getCategoryName().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getIconPath() == null) ? 0 : getIconPath().hashCode());
        result = prime * result + ((getBgColor() == null) ? 0 : getBgColor().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", categoryId=").append(categoryId);
        sb.append(", categoryName=").append(categoryName);
        sb.append(", sort=").append(sort);
        sb.append(", iconPath=").append(iconPath);
        sb.append(", bgColor=").append(bgColor);
        sb.append(", type=").append(type);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}