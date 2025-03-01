package com.qianzibi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qianzibi.annotation.Check;
import com.qianzibi.annotation.VerifyParam;
import com.qianzibi.entity.enums.CategoryTypeEnum;
import com.qianzibi.entity.enums.PermissionCodeEnum;
import com.qianzibi.entity.enums.ResponseCodeEnum;
import com.qianzibi.entity.po.Category;
import com.qianzibi.entity.query.CategoryQuery;
import com.qianzibi.exception.BusinessException;
import com.qianzibi.service.CategoryService;
import com.qianzibi.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController("categoryController")
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @PostMapping("/loadAllCategory")
    @Check(permissionCode = PermissionCodeEnum.CATEOGRY_LIST)
    public R loadAllCategory(CategoryQuery query) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        queryWrapper.like(query.getCategoryNameFuzzy() != null, Category::getCategoryName, query.getCategoryNameFuzzy());
        queryWrapper.like(query.getBgColorFuzzy() != null, Category::getBgColor, query.getBgColorFuzzy());
        queryWrapper.like(query.getIconPathFuzzy() != null, Category::getIconPath, query.getIconPathFuzzy());
        List<Category> categories = categoryService.list(queryWrapper);
        return R.ok().data(categories);
    }

    @PostMapping("/saveCategory")
    @Check(permissionCode = PermissionCodeEnum.CATEOGRY_EDIT)
    public R saveCategory(Category category) {
        categoryService.saveCategory(category);
        return R.ok();
    }

    @PostMapping("/delCategory")
    @Check(permissionCode = PermissionCodeEnum.CATEOGRY_DEL)
    public R delCategory(@VerifyParam(required = true) Integer categoryId) {
        boolean removeById = categoryService.removeById(categoryId);
        return R.ok();
    }
//
//    /**
//     * @Description 删除所有表中与categoryId相关的数据
//     * @Author Lenove
//     * @Date 2024/5/30
//     * @MethodName delAbout_Category_All
//     * @Param categoryId
//     * @Return: int 在category表中执行成功删除的category_id个数
//     */
//    @PostMapping("/delAboutCategoryAll223")
//    @GlobalInterceptor(permissionCode = PermissionCodeEnum.CATEOGRY_DEL)
//    public R delAbout_Category_All(@VerifyParam(required = true) Integer categoryId) {
//        Integer delCategoryAll = categoryService.delAboutCategoryAll(categoryId);
//        return R.ok().data(delCategoryAll);
//    }

    @PostMapping("/changeSort")
    @Check(permissionCode = PermissionCodeEnum.CATEOGRY_EDIT)
    public R changeSort(@VerifyParam(required = true) String categoryIds) {
        categoryService.changeSortById(categoryIds);
        return R.ok();
    }

    @RequestMapping("/loadAllCategoryByType")
    @Check(permissionCode = PermissionCodeEnum.CATEOGRY_LIST)
    public R loadAllCategoryByType(@VerifyParam(required = true) Integer type) {
        CategoryTypeEnum typeEnum = CategoryTypeEnum.getByType(type);
        if (typeEnum == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        List<Category> categories = categoryService.loadAllCategoryByType(typeEnum);
        return R.ok().data(categories);
    }

//    @RequestMapping("/loadAllCategory4Select")
//    public R loadAllCategory4Select(@VerifyParam(required = true) Integer type) {
//        List<Category> categories = categoryService.loadAllCategoryByType(type);
//        System.out.println("type+++" + type);
//        System.out.println("categories" + categories);
//        return R.ok().data(categories);
//    }

}
