package com.qianzibi.annotation;

import com.qianzibi.entity.enums.PermissionCodeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//作用在方法上
@Retention(RetentionPolicy.RUNTIME)//需要什么级别保存该注释信息
public @interface Check {

    boolean checkParams() default true;

    boolean checkLogin() default true;

    // 不校验权限
    PermissionCodeEnum permissionCode() default PermissionCodeEnum.NO_PERMISSION;
}
