package com.qianzibi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianzibi.annotation.Check;
import com.qianzibi.annotation.VerifyParam;
import com.qianzibi.entity.enums.PermissionCodeEnum;
import com.qianzibi.entity.po.AppCarousel;
import com.qianzibi.entity.query.AppCarouselQuery;
import com.qianzibi.service.AppCarouselService;
import com.qianzibi.utils.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/appCarousel")
public class AppCarouselController {

    @Resource
    private AppCarouselService appCarouselService;

    /**
     * 分页查询
     */
    @RequestMapping("/loadDataList")
    @Check(permissionCode = PermissionCodeEnum.APP_CAROUSEL_LIST)
    public R loadDataList(AppCarouselQuery query) {
        Page appCarouselPage = appCarouselService.findListAppCarouselPage(query);
        return R.ok().data(appCarouselPage.getRecords());
    }

    /**
     * 保存
     */
    @RequestMapping("/saveCarousel")
    @Check(permissionCode = PermissionCodeEnum.APP_CAROUSEL_EDIT)
    public R saveCarousel(AppCarousel appCarousel) {
        appCarouselService.saveAppCarousel(appCarousel);
        return R.ok();
    }

    /**
     * 改变顺序
     */
    @RequestMapping("/changeSort")
    @Check(permissionCode = PermissionCodeEnum.APP_CAROUSEL_EDIT)
    public R changerSort(@VerifyParam(required = true) String carouselIds) {
        appCarouselService.changerAppCarouselSort(carouselIds);
        return R.ok();
    }

    /**
     * 单个删除
     */
    @RequestMapping("/delCarousel")
    @Check(permissionCode = PermissionCodeEnum.APP_CAROUSEL_EDIT)
    public R delCarousel(@VerifyParam(required = true) Integer carouselId) {
//        // 批量删除
//        List<String> carouselIdList = Arrays.asList(carouselIds.split(","));
//        appCarouselService.removeBatchByIds(carouselIdList);

        appCarouselService.removeById(carouselId);
        return R.ok();
    }
}
