package com.qianzibi.mapper;

import com.qianzibi.entity.po.AppCarousel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 86158
* @description 针对表【app_carousel(app轮播)】的数据库操作Mapper
* @createDate 2025-03-06 23:18:38
* @Entity com.qianzibi.entity.po.AppCarousel
*/
public interface AppCarouselMapper extends BaseMapper<AppCarousel> {

    void updateAppCarouselSort(String carouselId, int sort);
}




