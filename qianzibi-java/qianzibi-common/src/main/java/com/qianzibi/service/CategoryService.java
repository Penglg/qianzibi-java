package com.qianzibi.service;

import com.qianzibi.entity.enums.CategoryTypeEnum;
import com.qianzibi.entity.po.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86158
* @description 针对表【category(分类)】的数据库操作Service
* @createDate 2025-02-04 21:31:56
*/
public interface CategoryService extends IService<Category> {

    void saveCategory(Category category);

    void changeSortById(String categoryIds);

    List<Category> loadAllCategoryByType(CategoryTypeEnum typeEnum);
}
