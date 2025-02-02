package com.qianzibi.entity.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统角色表参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleQuery extends PageRequest {
    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 角色名称
     */
    private String roleName;

    private String roleNameFuzzy;

    /**
     * 角色描述
     */
    private String roleDesc;

    private String roleDescFuzzy;

    /**
     * 创建时间
     */
    private String createTime;

    private String createTimeStart;

    private String createTimeEnd;

    /**
     * 最后更新时间
     */
    private String lastUpdateTime;

    private String lastUpdateTimeStart;

    private String lastUpdateTimeEnd;
}