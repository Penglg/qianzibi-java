package com.qianzibi.mapper;

import com.qianzibi.entity.po.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
* @author 86158
* @description 针对表【sys_menu(菜单表)】的数据库操作Mapper
* @createDate 2025-01-26 17:09:06
* @Entity com.qianzibi.entity.po.SysMenu
*/
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据角色获取菜单
     */
    List<SysMenu> selectAllMenuByRoleIds(@Param("roleIds") int[] roleIds);
}




