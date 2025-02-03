package com.qianzibi.mapper;

import com.qianzibi.entity.po.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 86158
* @description 针对表【sys_role(系统角色表)】的数据库操作Mapper
* @createDate 2025-01-26 17:10:10
* @Entity com.qianzibi.entity.po.SysRole
*/
public interface SysRoleMapper extends BaseMapper<SysRole> {

    String selectRoleNames(String roles);

}




