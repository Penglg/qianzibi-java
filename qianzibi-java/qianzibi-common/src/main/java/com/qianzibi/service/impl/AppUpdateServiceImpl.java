package com.qianzibi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianzibi.common.ResultCode;
import com.qianzibi.entity.config.AppConfig;
import com.qianzibi.entity.constants.Constants;
import com.qianzibi.entity.enums.AppUpdateStatusEnum;
import com.qianzibi.entity.enums.AppUpdateTypeEnum;
import com.qianzibi.entity.po.AppUpdate;
import com.qianzibi.entity.query.AppUpdateQuery;
import com.qianzibi.exception.BusinessException;
import com.qianzibi.service.AppUpdateService;
import com.qianzibi.mapper.AppUpdateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
* @author 86158
* @description 针对表【app_update(app发布)】的数据库操作Service实现
* @createDate 2025-03-06 23:19:12
*/
@Service
public class AppUpdateServiceImpl extends ServiceImpl<AppUpdateMapper, AppUpdate>
    implements AppUpdateService{

    @Resource
    private AppUpdateMapper appUpdateMapper;

    @Resource
    private AppConfig appConfig;

    @Override
    public Page<AppUpdate> getAppUpdatePage(AppUpdateQuery query) {
        LambdaQueryWrapper<AppUpdate> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(query.getCreateTimeStart() != null && query.getCreateTimeEnd() != null,
                            AppUpdate::getCreateTime, query.getCreateTimeStart(), query.getCreateTimeEnd());
        wrapper.orderByDesc(AppUpdate::getId);
        return appUpdateMapper.selectPage(new Page<>(query.getCurrent(), query.getPageSize()), wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateAppUpdate(AppUpdate appUpdate, MultipartFile file) {
        LambdaQueryWrapper<AppUpdate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(AppUpdate::getId);
        Page<AppUpdate> page = new Page<>(0, 1);
        AppUpdate appUpdateMaxVersion = appUpdateMapper.selectPage(page, queryWrapper).getRecords().get(0);
        if (appUpdateMaxVersion != null) {
            Long replaceVersionDB = Long.parseLong(appUpdateMaxVersion.getVersion().replace(".", ""));
            Long saveVersion = Long.parseLong(appUpdate.getVersion().replace(".", ""));
            if (appUpdate.getId() != null && (replaceVersionDB > saveVersion || appUpdate.getVersion().equals(appUpdateMaxVersion.getVersion()))) {
                throw new BusinessException(ResultCode.VERSION_ERROR, "更新或新版本必须大于历史版本");
            }
        }
        if (appUpdate.getId() == null) {
            appUpdate.setStatus(AppUpdateStatusEnum.INIT.getStatus());
            appUpdateMapper.insert(appUpdate);
        } else {
            appUpdate.setStatus(null);
            appUpdate.setGrayscaleDevice(null);
            appUpdateMapper.updateById(appUpdate);
        }
        if (file != null) {
            File folder = new File(appConfig.getProjectFolder() + Constants.APP_UPDATE_FOLDER);
            if (folder.exists()) {
                folder.mkdir();
            }
            AppUpdateTypeEnum appUpdateTypeEnum = AppUpdateTypeEnum.getByType(appUpdate.getUpdateType());
            try {
                if (appUpdateTypeEnum == null) {
                    throw new BusinessException(ResultCode.ERROR_NAN, "app更新指定参数不存在");
                }
                // 将上传的文件转移到指定的新文件位置
                file.transferTo(new File(folder.getAbsolutePath() + "/" + appUpdate.getId() + appUpdateTypeEnum.getSuffix()));
            } catch (IOException e) {
                throw new BusinessException(ResultCode.APPUPDATE_BAD, "App更新失败");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void postAppUpdate(Integer id, Integer status, String grayscaleDevice) {
        AppUpdateStatusEnum statusEnum = AppUpdateStatusEnum.getByStatus(status);

        if (id == null) {
            throw new BusinessException(ResultCode.ERROR_NAN, "发布id为空");
        }
        if (statusEnum == AppUpdateStatusEnum.GRAYSCALE && StringUtils.isEmpty(grayscaleDevice)) {
            throw new BusinessException(ResultCode.ERROR_600, "发布app参数错误");
        }
        if (AppUpdateStatusEnum.GRAYSCALE != statusEnum) {
            grayscaleDevice = "";
        }
        AppUpdate appUpdate = new AppUpdate();
        appUpdate.setGrayscaleDevice(grayscaleDevice);
        appUpdate.setId(id);
        appUpdate.setStatus(status);
        appUpdateMapper.updateById(appUpdate);
    }
}




