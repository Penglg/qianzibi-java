package com.qianzibi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianzibi.annotation.Check;
import com.qianzibi.annotation.VerifyParam;
import com.qianzibi.entity.enums.PermissionCodeEnum;
import com.qianzibi.entity.po.SysRole;
import com.qianzibi.entity.query.SysRoleQuery;
import com.qianzibi.service.SysRoleService;
import com.qianzibi.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/settings")
public class SysRoleController {

    @Resource
    SysRoleService sysRoleService;

    /**
     * 根据条件分页查询
     */
    @PostMapping("/loadRoles")
    @Check(permissionCode = PermissionCodeEnum.SETTINGS_ROLE_LIST)
    public R loadRoles(SysRoleQuery query) {

        SysRole sysRoleQuery = new SysRole();
        BeanUtils.copyProperties(query, sysRoleQuery);
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>(sysRoleQuery);
        queryWrapper.orderByDesc(SysRole::getCreateTime);
        Page<SysRole> sysRolePage = sysRoleService.page(new Page<>(query.getCurrent(), query.getPageSize()), queryWrapper);
        return R.ok().data(sysRolePage);
    }

    @PostMapping("/saveRole")
    @Check(permissionCode = PermissionCodeEnum.SETTINGS_ROLE_EDIT)
    public R saveRole(@VerifyParam(required = true) SysRole sysRole,
                      String menuIds,
                      String halfMenuIds) {
        sysRoleService.saveRole(sysRole, menuIds, halfMenuIds);
        return R.ok();
    }

    @PostMapping("/saveRoleMenu")
    @Check(permissionCode = PermissionCodeEnum.SETTINGS_ROLE_EDIT)
    public R saveRoleMenu(@VerifyParam(required = true) Integer roleId,
                          @VerifyParam(required = true) String menuIds,
                          String halfMenuIds) {
        sysRoleService.saveRoleMenu(roleId, menuIds, halfMenuIds);
        return R.ok();
    }

    @PostMapping("/getRoleByRoleId")
    @Check(permissionCode = PermissionCodeEnum.SETTINGS_ROLE_LIST)
    public R getRoleByRoleId(@VerifyParam(required = true) Integer roleId) {
        SysRole sysRole = sysRoleService.getSysRoleByRoleId(roleId);
        return R.ok().data(sysRole);
    }

    @PostMapping("/delRole")
    @Check(permissionCode = PermissionCodeEnum.SETTINGS_ROLE_DEL)
    public R deleteRole(@VerifyParam(required = true) Integer roleId) {
        sysRoleService.deleteRole(roleId);
        return R.ok();
    }
}
