package com.qianzibi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianzibi.entity.po.AppDevice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianzibi.entity.query.AppDeviceQuery;

/**
* @author 86158
* @description 针对表【app_device(设备信息)】的数据库操作Service
* @createDate 2025-03-06 23:19:04
*/
public interface AppDeviceService extends IService<AppDevice> {

    Page<AppDevice> findListByPage(AppDeviceQuery query);
}
