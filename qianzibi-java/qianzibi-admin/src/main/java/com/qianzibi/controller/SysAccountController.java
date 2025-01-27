package com.qianzibi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianzibi.entity.po.SysAccount;
import com.qianzibi.entity.query.SysAccountQuery;
import com.qianzibi.service.SysAccountService;
import com.qianzibi.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/setting")
public class SysAccountController {
    @Resource
    SysAccountService sysAccountService;

    @PostMapping("/loadDataList")
    public R<SysAccount> loadDataList(SysAccountQuery query) {
        SysAccount sysAccountQuery = new SysAccount();
        BeanUtils.copyProperties(query, sysAccountQuery);
        QueryWrapper<SysAccount> queryWrapper = new QueryWrapper<>(sysAccountQuery);
        Page<SysAccount> sysAccountPage = sysAccountService.page(new Page<>(query.getCurrent(), query.getPageSize()), queryWrapper);
        return R.ok().data(sysAccountPage);
    }
}
