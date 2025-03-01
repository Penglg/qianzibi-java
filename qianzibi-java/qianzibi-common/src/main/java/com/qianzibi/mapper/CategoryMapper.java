package com.qianzibi.mapper;

import com.qianzibi.entity.po.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 86158
* @description 针对表【category(分类)】的数据库操作Mapper
* @createDate 2025-02-04 21:31:56
* @Entity com.qianzibi.entity.po.Category
*/
public interface CategoryMapper extends BaseMapper<Category> {

    void updateCategoryName(@Param("categoryId") Integer categoryId, @Param("categoryName") String categoryName);
}




