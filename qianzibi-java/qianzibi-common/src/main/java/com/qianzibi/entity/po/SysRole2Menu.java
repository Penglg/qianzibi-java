package com.qianzibi.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色对应的菜单权限表
 * @TableName sys_role_2_menu
 */
@TableName("sys_role_2_menu")
@Data
public class SysRole2Menu implements Serializable {
    /**
     * 角色ID
     */
    @TableId(type = IdType.AUTO)
    private Integer roleId;

    /**
     * 菜单ID
     */
    private Integer menuId;

    /**
     * 0:半选. 1:全选
     */
    private Integer checkType;

    private static final long serialVersionUID = 1L;
}