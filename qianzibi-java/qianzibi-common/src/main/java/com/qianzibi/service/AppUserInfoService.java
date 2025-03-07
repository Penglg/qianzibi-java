package com.qianzibi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianzibi.entity.po.AppUserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianzibi.entity.query.AppUserInfoQuery;

/**
* @author 86158
* @description 针对表【app_user_info】的数据库操作Service
* @createDate 2025-03-06 23:19:15
*/
public interface AppUserInfoService extends IService<AppUserInfo> {

    Page<AppUserInfo> findListByPage(AppUserInfoQuery query);
}
