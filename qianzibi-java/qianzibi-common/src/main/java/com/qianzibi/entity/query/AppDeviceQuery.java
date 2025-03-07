package com.qianzibi.entity.query;

import lombok.Data;

import java.io.Serializable;

/**
 * 设备信息参数
 */
@Data
public class AppDeviceQuery extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 设备ID
     */
    private String deviceId;

    private String deviceIdFuzzy;

    /**
     * 手机品牌
     */
    private String deviceBrand;

    private String deviceBrandFuzzy;

    /**
     * 创建时间
     */
    private String createTime;

    private String createTimeStart;

    private String createTimeEnd;

    /**
     * 最后使用时间
     */
    private String lastUseTime;

    private String lastUseTimeStart;

    private String lastUseTimeEnd;

    /**
     * ip
     */
    private String ip;

    private String ipFuzzy;
}
