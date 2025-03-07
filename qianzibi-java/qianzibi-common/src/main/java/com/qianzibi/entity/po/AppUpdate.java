package com.qianzibi.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * app发布
 * @TableName app_update
 */
public class AppUpdate implements Serializable {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 版本号
     */
    private String version;

    /**
     * 更新描述
     */
    private String updateDesc;

    /**
     * 更新类型 0:全更新 1:局部热更新
     */
    private Integer updateType;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 0:未发布 1:灰度发布 2:全网发布
     */
    private Integer status;

    /**
     * 灰度设备ID
     */
    private String grayscaleDevice;

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 自增ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 版本号
     */
    public String getVersion() {
        return version;
    }

    /**
     * 版本号
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 更新描述
     */
    public String getUpdateDesc() {
        return updateDesc;
    }

    /**
     * 更新描述
     */
    public void setUpdateDesc(String updateDesc) {
        this.updateDesc = updateDesc;
    }

    /**
     * 更新类型 0:全更新 1:局部热更新
     */
    public Integer getUpdateType() {
        return updateType;
    }

    /**
     * 更新类型 0:全更新 1:局部热更新
     */
    public void setUpdateType(Integer updateType) {
        this.updateType = updateType;
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
     * 0:未发布 1:灰度发布 2:全网发布
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 0:未发布 1:灰度发布 2:全网发布
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 灰度设备ID
     */
    public String getGrayscaleDevice() {
        return grayscaleDevice;
    }

    /**
     * 灰度设备ID
     */
    public void setGrayscaleDevice(String grayscaleDevice) {
        this.grayscaleDevice = grayscaleDevice;
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
        AppUpdate other = (AppUpdate) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getUpdateDesc() == null ? other.getUpdateDesc() == null : this.getUpdateDesc().equals(other.getUpdateDesc()))
            && (this.getUpdateType() == null ? other.getUpdateType() == null : this.getUpdateType().equals(other.getUpdateType()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getGrayscaleDevice() == null ? other.getGrayscaleDevice() == null : this.getGrayscaleDevice().equals(other.getGrayscaleDevice()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getUpdateDesc() == null) ? 0 : getUpdateDesc().hashCode());
        result = prime * result + ((getUpdateType() == null) ? 0 : getUpdateType().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getGrayscaleDevice() == null) ? 0 : getGrayscaleDevice().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", version=").append(version);
        sb.append(", updateDesc=").append(updateDesc);
        sb.append(", updateType=").append(updateType);
        sb.append(", createTime=").append(createTime);
        sb.append(", status=").append(status);
        sb.append(", grayscaleDevice=").append(grayscaleDevice);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}