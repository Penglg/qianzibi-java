package com.qianzibi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianzibi.entity.po.AppCarousel;
import com.qianzibi.entity.query.AppCarouselQuery;
import com.qianzibi.service.AppCarouselService;
import com.qianzibi.mapper.AppCarouselMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
* @author 86158
* @description 针对表【app_carousel(app轮播)】的数据库操作Service实现
* @createDate 2025-03-06 23:18:38
*/
@Service
public class AppCarouselServiceImpl extends ServiceImpl<AppCarouselMapper, AppCarousel>
    implements AppCarouselService{

    @Resource
    private AppCarouselMapper appCarouselMapper;

    /**
     * 分页查询
     */
    @Override
    public Page findListAppCarouselPage(AppCarouselQuery query) {
        LambdaQueryWrapper<AppCarousel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(AppCarousel::getSort);
        return appCarouselMapper.selectPage(new Page<>(query.getCurrent(), query.getPageSize()), queryWrapper);
    }

    /**
     * 保存
     */
    @Override
    public void saveAppCarousel(AppCarousel appCarousel) {

        if (appCarousel.getCarouselId() == null) {
            Integer max_sort = appCarouselMapper.selectOne(new QueryWrapper<AppCarousel>().select("MAX(sort) as sort")).getSort();
            appCarousel.setSort(max_sort + 1);
            appCarouselMapper.insert(appCarousel);
        } else {
            appCarouselMapper.updateById(appCarousel);
        }
    }

    /**
     * 改变顺序
     */
    @Override
    public void changerAppCarouselSort(String carouselIds) {
        List<String> carouselIds_list = Arrays.asList(carouselIds.split(","));
        QueryWrapper<AppCarousel> queryWrapper = new QueryWrapper();
        queryWrapper.in("carousel_id", carouselIds_list);
        int sort_num = 1;
        for (String carouselId : carouselIds_list) {
            appCarouselMapper.updateAppCarouselSort(carouselId, sort_num++);
        }

    }
}




