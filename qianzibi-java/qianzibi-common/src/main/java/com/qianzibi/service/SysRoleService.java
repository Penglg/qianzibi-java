package com.qianzibi.service;

import com.qianzibi.entity.po.SysAccount;
import com.qianzibi.entity.po.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86158
* @description 针对表【sys_role(系统角色表)】的数据库操作Service
* @createDate 2025-01-26 17:10:10
*/
public interface SysRoleService extends IService<SysRole> {

    void saveRole(SysRole sysRole, String menuIds, String halfMenuIds);

    void saveRoleMenu(Integer roleId, String menuIds, String halfMenuIds);

    SysRole getSysRoleByRoleId(Integer roleId);

    Integer deleteRole(Integer roleId);

    void setRolesNamesByRoles(List<SysAccount> sysAccounts);

    void setRolesNamesByRoles(SysAccount sysAccount);
}
