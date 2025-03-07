package com.qianzibi.controller;

import com.qianzibi.annotation.Check;
import com.qianzibi.annotation.VerifyParam;
import com.qianzibi.common.ResultCode;
import com.qianzibi.entity.enums.PermissionCodeEnum;
import com.qianzibi.entity.enums.UserStatusEnum;
import com.qianzibi.entity.po.AppUserInfo;
import com.qianzibi.entity.query.AppDeviceQuery;
import com.qianzibi.entity.query.AppUserInfoQuery;
import com.qianzibi.exception.BusinessException;
import com.qianzibi.service.AppDeviceService;
import com.qianzibi.service.AppUserInfoService;
import com.qianzibi.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/appUser")
public class AppUserInfoController {

    @Resource
    private AppDeviceService appDeviceService;

    @Resource
    private AppUserInfoService appUserInfoService;

    @PostMapping("/loadDeviceList")
    @Check(permissionCode = PermissionCodeEnum.APP_USER_DEVICE)
    public R loadDeviceList(AppDeviceQuery query) {
        return R.ok().data(appDeviceService.findListByPage(query));
    }

    @RequestMapping("/loadDataList")
    @Check(permissionCode = PermissionCodeEnum.APP_USER_LIST)
    public R loadDataList(AppUserInfoQuery query) {
        return R.ok().data(appUserInfoService.findListByPage(query));
    }

    /**
     * 更新用户状态
     */
    @RequestMapping("/updateStatus")
    @Check(permissionCode = PermissionCodeEnum.APP_USER_EDIT)
    public R updateStatus(@VerifyParam(required = true) String userId,
                          @VerifyParam(required = true) Integer status) {
        UserStatusEnum userStatusEnum = UserStatusEnum.getByStatus(status);
        if (userStatusEnum == null) {
            throw new BusinessException(ResultCode.ERROR_600, "状态码错误,请按规范传递参数");
        }
        AppUserInfo appUserInfo = new AppUserInfo();
        appUserInfo.setUserId(userId);
        appUserInfo.setStatus(status);
        appUserInfoService.updateById(appUserInfo);
        return R.ok();
    }
}
