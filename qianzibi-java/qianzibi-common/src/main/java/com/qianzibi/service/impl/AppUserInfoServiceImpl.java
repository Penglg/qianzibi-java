package com.qianzibi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianzibi.entity.po.AppUserInfo;
import com.qianzibi.entity.query.AppUserInfoQuery;
import com.qianzibi.service.AppUserInfoService;
import com.qianzibi.mapper.AppUserInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 86158
* @description 针对表【app_user_info】的数据库操作Service实现
* @createDate 2025-03-06 23:19:15
*/
@Service
public class AppUserInfoServiceImpl extends ServiceImpl<AppUserInfoMapper, AppUserInfo>
    implements AppUserInfoService{

    @Resource
    private AppUserInfoMapper appUserInfoMapper;

    @Override
    public Page<AppUserInfo> findListByPage(AppUserInfoQuery query) {
        LambdaQueryWrapper<AppUserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(query.getJoinTimeStart() != null && query.getJoinTimeEnd() != null,
                                AppUserInfo::getJoinTime, query.getJoinTimeStart(), query.getJoinTimeEnd());
        queryWrapper.like(query.getEmailFuzzy() != null, AppUserInfo::getEmail, query.getEmailFuzzy());
        queryWrapper.like(query.getLastUseDeviceIdFuzzy() != null, AppUserInfo::getLastUseDeviceId, query.getLastUseDeviceIdFuzzy());
        queryWrapper.orderByDesc(AppUserInfo::getJoinTime);
        return appUserInfoMapper.selectPage(new Page<>(query.getCurrent(), query.getPageSize()), queryWrapper);
    }

}




