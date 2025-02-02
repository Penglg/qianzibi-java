package com.qianzibi.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qianzibi.annotation.VerifyParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单表
 * @TableName sys_menu
 */
@TableName("sys_menu")
@Data
public class SysMenu implements Serializable {
    /**
     * menu_id，自增主键
     */
    @TableId(type = IdType.AUTO)
    private Integer menuId;

    /**
     * 菜单名
     */
    @VerifyParam(required = true, max = 32)
    private String menuName;

    /**
     * 菜单类型 0：菜单. 1:按钮.
     */
    @VerifyParam(required = true)
    private Integer menuType;

    /**
     * 菜单跳转到的地址
     */
    private String menuUrl;

    /**
     * 上级菜单ID
     */
    @VerifyParam(required = true)
    private Integer pId;

    /**
     * 菜单排序
     */
    @VerifyParam(required = true)
    private Integer sort;

    /**
     * 权限编码
     */
    @VerifyParam(required = true, max = 50)
    private String permissionCode;

    /**
     * 图标
     */
//    @VerifyParam(required = true, max = 50)
    private String icon;

    /**
     * 子菜单
     * 该字段不映射数据库
     */
    @TableField(exist = false)
    private List<SysMenu> children;

    private static final long serialVersionUID = 1L;
}