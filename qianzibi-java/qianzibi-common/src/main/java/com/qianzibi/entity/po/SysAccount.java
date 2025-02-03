package com.qianzibi.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qianzibi.annotation.VerifyParam;
import com.qianzibi.entity.enums.VerifyRegexEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    @VerifyParam(required = true, regex = VerifyRegexEnum.PASSWORD)
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
    @VerifyParam(required = true)
    private String roles;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(exist = false)
    private String roleNames;

    private static final long serialVersionUID = 1L;
}