package com.qianzibi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianzibi.annotation.Check;
import com.qianzibi.annotation.VerifyParam;
import com.qianzibi.common.ResultCode;
import com.qianzibi.entity.config.AppConfig;
import com.qianzibi.entity.enums.PermissionCodeEnum;
import com.qianzibi.entity.enums.UserStatusEnum;
import com.qianzibi.entity.enums.VerifyRegexEnum;
import com.qianzibi.entity.po.SysAccount;
import com.qianzibi.entity.query.SysAccountQuery;
import com.qianzibi.exception.BusinessException;
import com.qianzibi.service.SysAccountService;
import com.qianzibi.service.SysRoleService;
import com.qianzibi.utils.MD5;
import com.qianzibi.utils.R;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/settings")
public class SysAccountController {
    @Resource
    private SysAccountService sysAccountService;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private AppConfig appConfig;

    /**
     * 查询用户
     */
    @PostMapping("/loadAccountList")
    @Check(permissionCode = PermissionCodeEnum.SETTINGS_ACCOUNT_LIST)
    public R loadAccountList(SysAccountQuery query) {
        SysAccount sysAccount = new SysAccount();
        BeanUtils.copyProperties(query, sysAccount);
        LambdaQueryWrapper<SysAccount> queryWrapper = new LambdaQueryWrapper<>(sysAccount);
        queryWrapper.orderByDesc(SysAccount::getCreateTime);
        queryWrapper.like(query.getUserNameFuzzy() != null, SysAccount::getUserName, query.getUserNameFuzzy());
        queryWrapper.like(query.getRolesFuzzy() != null, SysAccount::getRoles, query.getRolesFuzzy());
        queryWrapper.like(query.getPositionFuzzy() != null, SysAccount::getPosition, query.getPositionFuzzy());
        queryWrapper.like(query.getPhoneFuzzy() != null, SysAccount::getPhone, query.getPhoneFuzzy());
        queryWrapper.like(query.getPasswordFuzzy() != null, SysAccount::getPassword, query.getPasswordFuzzy());
        Page<SysAccount> sysAccountPage = sysAccountService.page(new Page<>(query.getCurrent(), query.getPageSize()), queryWrapper);
        // 加载角色
        sysRoleService.setRolesNamesByRoles(sysAccountPage.getRecords());
        return R.ok().data(sysAccountPage);
    }

    /**
     * 修改用户状态
     */
    @PostMapping("/updateStatus")
    @Check(permissionCode = PermissionCodeEnum.SETTINGS_ACCOUNT_UPDATE_PASSWORD)
    public R updateStatus(@VerifyParam(required = true) Integer userId, @VerifyParam(required = true) Integer status) {
        UserStatusEnum userStatusEnum = UserStatusEnum.getByStatus(status);
        if (userStatusEnum == null) {
            throw new BusinessException(ResultCode.ERROR_600, "找不到该类型状态");
        }
        SysAccount updateInfo = new SysAccount();
        updateInfo.setStatus(status);
        updateInfo.setUserId(userId);
        sysAccountService.updateById(updateInfo);
        return R.ok();
    }

    @PostMapping("/delAccount")
    @Check(permissionCode = PermissionCodeEnum.SETTINGS_ACCOUNT_DEL)
    public R delAccount(@VerifyParam Integer userId) {
        SysAccount sysAccount = sysAccountService.getById(userId);
        if (sysAccount == null) {
            throw new BusinessException(ResultCode.ERROR_NAN, "账户不存在");
        }
        if (StringUtils.hasText(appConfig.getSuperAdminPhones()) && ArrayUtils.contains(appConfig.getSuperAdminPhones().split(","), sysAccount.getPhone())) {
            throw new BusinessException(ResultCode.ERROR_OTHER, "系统超级管理员不允许删除");
        }
        sysAccountService.removeById(sysAccount);
        return R.ok();
    }

    @PostMapping("/saveAccount")
    @Check(permissionCode = PermissionCodeEnum.SETTINGS_ACCOUNT_EDIT)
    public R saveAccount(@VerifyParam SysAccount sysAccount) {
        sysAccountService.saveAccountUser(sysAccount);
        return R.ok();
    }

    /**
     * 修改密码
     */
    @PostMapping("/updatePassword")
    @Check(permissionCode = PermissionCodeEnum.SETTINGS_ACCOUNT_UPDATE_PASSWORD)
    public R updatePassword(@VerifyParam Integer userId,
                            @VerifyParam(required = true, regex = VerifyRegexEnum.PASSWORD) String password) {
        SysAccount sysAccount = new SysAccount();
        sysAccount.setUserId(userId);
        sysAccount.setPassword(MD5.encrypt(password));
        sysAccountService.updateById(sysAccount);
        return R.ok();
    }
}
