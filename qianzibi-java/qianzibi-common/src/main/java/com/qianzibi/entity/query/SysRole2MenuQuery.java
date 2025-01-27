package com.qianzibi.entity.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色对应的菜单权限表参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRole2MenuQuery extends BaseParam {
    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 菜单ID
     */
    private Integer menuId;

    /**
     * 0:半选 1:全选
     */
    private Integer checkType;


}