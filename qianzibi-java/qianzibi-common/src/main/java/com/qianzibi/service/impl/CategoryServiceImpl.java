package com.qianzibi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianzibi.common.ResultCode;
import com.qianzibi.entity.enums.CategoryTypeEnum;
import com.qianzibi.entity.po.Category;
import com.qianzibi.exception.BusinessException;
import com.qianzibi.service.CategoryService;
import com.qianzibi.mapper.CategoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author 86158
* @description 针对表【category(分类)】的数据库操作Service实现
* @createDate 2025-02-04 21:31:56
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 保存分类
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCategory(Category category) {
        // 如果query中的name不为空, 且查询数据库的数据大于1说明没有重复name则可以进行新增or更新
        if (category.getCategoryName() != null && Math.toIntExact(categoryMapper
                .selectCount(new LambdaQueryWrapper<Category>()
                        .eq(Category::getCategoryName, category.getCategoryName()))) > 1) {
            throw new BusinessException(ResultCode.ERROR_OTHER, "分类名称重复");
        }

        if (category.getCategoryId() == null) {
            category.setSort(Math.toIntExact(categoryMapper.selectCount(new QueryWrapper<>()) + 1));
            categoryMapper.insert(category);
        } else {
            category.setCategoryId(category.getCategoryId());
            categoryMapper.updateById(category);
        }
        Category categoryInfo = categoryMapper.selectById(category.getCategoryId());

        // 如果category_name与原来不同, 则修改表exam_question和表question_info中的name数据
        if (!categoryInfo.getCategoryName().equals(category.getCategoryName())) {
            categoryMapper.updateCategoryName(category.getCategoryId(), category.getCategoryName());
        }
    }

    /**
     * 获取所有category的id, 它们顺序就是排序完毕的字符串, 根据这个顺序设置sort
     * @Param categoryIds:以逗号分割所有category的id字符串
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeSortById(String categoryIds) {
        String[] categoryIdArray = categoryIds.split(",");
        Integer index = 1;
        for (String categoryIdStr : categoryIdArray) {
            Integer categoryId = Integer.parseInt(categoryIdStr);
            Category category = new Category();
            category.setSort(index);
            category.setCategoryId(categoryId);
            categoryMapper.updateById(category);
            index++;
        }
    }

    /**
     * 根据类型获取分类
     */
    @Override
    public List<Category> loadAllCategoryByType(CategoryTypeEnum typeEnum) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        List<Integer> types = new ArrayList<>();
        types.add(typeEnum.getType());
        types.add(CategoryTypeEnum.QUESTION_EXAM.getType());
        queryWrapper.orderByAsc(Category::getSort)
                .in(Category::getType, types);
        return categoryMapper.selectList(queryWrapper);
        // 未测试
    }
}




