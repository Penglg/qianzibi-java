package com.qianzibi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianzibi.entity.po.AppUpdate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianzibi.entity.query.AppUpdateQuery;
import org.springframework.web.multipart.MultipartFile;

/**
* @author 86158
* @description 针对表【app_update(app发布)】的数据库操作Service
* @createDate 2025-03-06 23:19:12
*/
public interface AppUpdateService extends IService<AppUpdate> {

    Page<AppUpdate> getAppUpdatePage(AppUpdateQuery query);

    void saveOrUpdateAppUpdate(AppUpdate appUpdate, MultipartFile file);

    void postAppUpdate(Integer id, Integer status, String grayscaleDevice);
}
