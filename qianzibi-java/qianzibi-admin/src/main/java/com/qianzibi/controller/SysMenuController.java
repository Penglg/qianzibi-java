package com.qianzibi.controller;

import com.qianzibi.annotation.Check;
import com.qianzibi.entity.enums.PermissionCodeEnum;
import com.qianzibi.entity.po.SysMenu;
import com.qianzibi.entity.query.SysMenuQuery;
import com.qianzibi.service.SysMenuService;
import com.qianzibi.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/setting")
public class SysMenuController{
    @Resource
    private SysMenuService sysMenuService;

    /**
     * @Description 根据分页条件查询
     * @Author Lenove
     * @Date 2024/5/15
     * @MethodName loadDataList
     * @Param null
     * @Return: null
     */
    @PostMapping("/menuList")
    @Check(permissionCode = PermissionCodeEnum.SETTINGS_MENU)
    public R loadDataList() {
        SysMenuQuery query = new SysMenuQuery();
        query.setFormate2Tree(true);
        query.setOrderByAsc("sort");
        List<SysMenu> menuList = sysMenuService.findLisByParam(query);
        return R.ok().data(menuList);
    }
}
