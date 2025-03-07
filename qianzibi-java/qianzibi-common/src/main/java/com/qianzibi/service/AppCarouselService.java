package com.qianzibi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianzibi.entity.po.AppCarousel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianzibi.entity.query.AppCarouselQuery;

/**
* @author 86158
* @description 针对表【app_carousel(app轮播)】的数据库操作Service
* @createDate 2025-03-06 23:18:38
*/
public interface AppCarouselService extends IService<AppCarousel> {

    Page findListAppCarouselPage(AppCarouselQuery query);

    void saveAppCarousel(AppCarousel appCarousel);

    void changerAppCarouselSort(String carouselIds);
}
