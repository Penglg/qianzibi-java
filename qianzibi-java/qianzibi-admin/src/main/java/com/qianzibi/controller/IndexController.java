package com.qianzibi.controller;

import com.qianzibi.annotation.Check;
import com.qianzibi.entity.enums.PermissionCodeEnum;
import com.qianzibi.service.IndexService;
import com.qianzibi.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 首页数据统计
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @Resource
    private IndexService indexService;


    @PostMapping("/getAllData")
    @Check(permissionCode = PermissionCodeEnum.HOME)
    public R getAllData() {
        return R.ok().data(indexService.getAllData());
    }


    @PostMapping("/getAppWeekData")
    @Check(permissionCode = PermissionCodeEnum.HOME)
    public R getAppWeekData() {
        return R.ok().data(indexService.getAppWeekData());
    }

    @RequestMapping("/getContentWeekData")
    @Check(permissionCode = PermissionCodeEnum.HOME)
    public R getContentWeekData() {
        return R.ok().data(indexService.getContentWeekData());
    }
}
