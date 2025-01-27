package com.qianzibi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianzibi.entity.po.SysMenu;
import com.qianzibi.entity.query.SysMenuQuery;
import com.qianzibi.service.SysMenuService;
import com.qianzibi.mapper.SysMenuMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 86158
* @description 针对表【sys_menu(菜单表)】的数据库操作Service实现
* @createDate 2025-01-26 17:09:06
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService{

    public static final Integer DEFAULT_ROOT_MENU_ID = 0;
    private static final String ROOT_MENU_NAME = "所有菜单";

    /**
     * 查询菜单
     */
    @Override
    public List<SysMenu> findLisByParam(SysMenuQuery query) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc(query.getOrderByAsc());
        List<SysMenu> menuList = baseMapper.selectList(queryWrapper);
        // 加上"所有菜单"的这一属性并递归排序
        if (query.getFormate2Tree() != null && query.getFormate2Tree()) {
            SysMenu root = new SysMenu();
            root.setMenuId(DEFAULT_ROOT_MENU_ID);
            root.setPId(-1);
            root.setMenuName(ROOT_MENU_NAME);
            menuList.add(root);
            menuList = convertLine2Tree4Menu(menuList, -1);
        }

        return menuList;
    }

    /**
     * 菜单递归, 每次在for循环中取出一个SysMenu并递归, 在递归中遍历所有SysMenu,
     * 每次递归中将其list中所有pid=之前取出的SysMenu.menuId的都添加为children属性(List<SysMenu> children
     */
    @Override
    public List<SysMenu> convertLine2Tree4Menu(List<SysMenu> menuList, Integer pid) {
        List<SysMenu> children = new ArrayList<>();
        for (SysMenu menu : menuList) {
            if (menu.getMenuId() != null && menu.getPId() != null && menu.getPId().equals(pid)) {
                menu.setChildren(convertLine2Tree4Menu(menuList, menu.getMenuId()));
                children.add(menu);
            }
        }
        return children;
    }
}




