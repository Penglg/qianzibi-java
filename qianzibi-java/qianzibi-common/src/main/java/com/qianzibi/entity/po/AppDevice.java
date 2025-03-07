package com.qianzibi.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备信息
 * @TableName app_device
 */
public class AppDevice implements Serializable {
    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 手机品牌
     */
    private String deviceBrand;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 最后使用时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.UPDATE)
    private Date lastUseTime;

    /**
     * ip
     */
    private String ip;

    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * 设备ID
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * 手机品牌
     */
    public String getDeviceBrand() {
        return deviceBrand;
    }

    /**
     * 手机品牌
     */
    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
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
     * 最后使用时间
     */
    public Date getLastUseTime() {
        return lastUseTime;
    }

    /**
     * 最后使用时间
     */
    public void setLastUseTime(Date lastUseTime) {
        this.lastUseTime = lastUseTime;
    }

    /**
     * ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * ip
     */
    public void setIp(String ip) {
        this.ip = ip;
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
        AppDevice other = (AppDevice) that;
        return (this.getDeviceId() == null ? other.getDeviceId() == null : this.getDeviceId().equals(other.getDeviceId()))
            && (this.getDeviceBrand() == null ? other.getDeviceBrand() == null : this.getDeviceBrand().equals(other.getDeviceBrand()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getLastUseTime() == null ? other.getLastUseTime() == null : this.getLastUseTime().equals(other.getLastUseTime()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDeviceId() == null) ? 0 : getDeviceId().hashCode());
        result = prime * result + ((getDeviceBrand() == null) ? 0 : getDeviceBrand().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getLastUseTime() == null) ? 0 : getLastUseTime().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", deviceId=").append(deviceId);
        sb.append(", deviceBrand=").append(deviceBrand);
        sb.append(", createTime=").append(createTime);
        sb.append(", lastUseTime=").append(lastUseTime);
        sb.append(", ip=").append(ip);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}