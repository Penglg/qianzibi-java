package com.qianzibi.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qianzibi.annotation.VerifyParam;
import com.qianzibi.entity.enums.VerifyRegexEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 账号信息
 * @TableName sys_account
 */
@TableName(value = "sys_account")
@Data
public class SysAccount implements Serializable {
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Integer userId;

    /**
     * 手机号(有唯一索引
     */
    @VerifyParam(required = true, regex = VerifyRegexEnum.PHONE)
    private String phone;

    /**
     * 用户名
     */
    @VerifyParam(required = true, max = 20)
    private String userName;

    /**
     * 密码
     */
    @VerifyParam(regex = VerifyRegexEnum.PASSWORD)
    private String password;

    /**
     * 职位
     */
    private String position;

    /**
     * 状态0:禁用 1:启用
     */
    private Integer status;

    /**
     * 用户拥有的角色多个用逗号隔开
     */
    private String roles;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}