package com.qianzibi.entity.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 账号信息参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysAccountQuery extends PageRequest implements Serializable {
    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 手机号
     */
    private String phone;

    private String phoneFuzzy;

    /**
     * 用户名
     */
    private String userName;

    private String userNameFuzzy;

    /**
     * 密码
     */
    private String password;

    private String passwordFuzzy;

    /**
     * 职位
     */
    private String position;

    private String positionFuzzy;

    /**
     * 状态 0:禁用 1:启用
     */
    private Integer status;

    /**
     * 用户拥有的角色多个用逗号隔开
     */
    private String roles;

    private String rolesFuzzy;

    /**
     * 创建时间
     */
    private String createTime;

    private String createTimeStart;

    private String createTimeEnd;

    private Boolean queryRoles;

    private static final long serialVersionUID = 1L;
}
