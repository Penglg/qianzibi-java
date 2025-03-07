package com.qianzibi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianzibi.entity.po.AppDevice;
import com.qianzibi.entity.query.AppDeviceQuery;
import com.qianzibi.service.AppDeviceService;
import com.qianzibi.mapper.AppDeviceMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 86158
* @description 针对表【app_device(设备信息)】的数据库操作Service实现
* @createDate 2025-03-06 23:19:04
*/
@Service
public class AppDeviceServiceImpl extends ServiceImpl<AppDeviceMapper, AppDevice>
    implements AppDeviceService{

    @Resource
    private AppDeviceMapper appDeviceMapper;

    @Override
    public Page<AppDevice> findListByPage(AppDeviceQuery query) {
        LambdaQueryWrapper<AppDevice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(query.getDeviceBrandFuzzy() != null, AppDevice::getDeviceBrand, query.getDeviceBrandFuzzy());
        queryWrapper.like(query.getIpFuzzy() != null, AppDevice::getIp, query.getDeviceIdFuzzy());
        queryWrapper.between(query.getCreateTimeStart() != null && query.getCreateTimeEnd() != null,
                                AppDevice::getCreateTime, query.getCreateTimeStart(), query.getCreateTimeEnd());
        queryWrapper.between(query.getLastUseTimeStart() != null && query.getLastUseTimeEnd() != null,
                                AppDevice::getLastUseTime, query.getLastUseTimeStart(), query.getLastUseTimeEnd());
        queryWrapper.orderByDesc(AppDevice::getCreateTime);
        return appDeviceMapper.selectPage(new Page<>(query.getCurrent(), query.getPageSize()), queryWrapper);
    }
}




