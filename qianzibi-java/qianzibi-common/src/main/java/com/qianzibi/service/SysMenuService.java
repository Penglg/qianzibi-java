package com.qianzibi.service;

import com.qianzibi.entity.po.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianzibi.entity.query.SysMenuQuery;

import java.util.List;

/**
* @author 86158
* @description 针对表【sys_menu(菜单表)】的数据库操作Service
* @createDate 2025-01-26 17:09:06
*/
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> findLisByParam(SysMenuQuery query);

    List<SysMenu> convertLine2Tree4Menu(List<SysMenu> menuList, Integer pid);
}
