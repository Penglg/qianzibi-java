package com.qianzibi.controller;

import com.qianzibi.annotation.Check;
import com.qianzibi.annotation.VerifyParam;
import com.qianzibi.entity.enums.PermissionCodeEnum;
import com.qianzibi.entity.po.AppUpdate;
import com.qianzibi.entity.query.AppUpdateQuery;
import com.qianzibi.service.AppUpdateService;
import com.qianzibi.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app")
public class AppUpdateController {

    @Resource
    private AppUpdateService appUpdateService;

    @PostMapping("/loadDataList")
    @Check(permissionCode = PermissionCodeEnum.APP_UPDATE_LIST)
    public R loadDatalist(AppUpdateQuery query) {
        return R.ok().data(appUpdateService.getAppUpdatePage(query));
    }

    @PostMapping("/saveUpdate")
    @Check
    public R saveUpdate(Integer id,
                        @VerifyParam(required = true) String version,
                        @VerifyParam(required = true) String updateDesc,
                        @VerifyParam(required = true) Integer updateType,
                        MultipartFile file) {

        AppUpdate appUpdate = new AppUpdate();
        appUpdate.setUpdateDesc(updateDesc);
        appUpdate.setId(id);
        appUpdate.setVersion(version);
        appUpdate.setUpdateType(updateType);
        appUpdateService.saveOrUpdateAppUpdate(appUpdate, file);
        return R.ok();
    }

    @PostMapping("/postUpdate")
    @Check(permissionCode = PermissionCodeEnum.APP_UPDATE_POST)
    public R postUpdate(@VerifyParam(required = true) Integer id,
                        @VerifyParam(required = true) Integer status,
                        String grayscaleDevice) {
        appUpdateService.postAppUpdate(id, status, grayscaleDevice);
        return R.ok();
    }

    @PostMapping("/delUpdate")
    @Check(permissionCode = PermissionCodeEnum.APP_UPDATE_POST)
    public R delUpdateApp(@VerifyParam(required = true) Integer id) {
        appUpdateService.removeById(id);
        return R.ok();
    }


}
