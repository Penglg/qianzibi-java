package com.qianzibi.entity.po;

import java.io.Serializable;

/**
 * 角色对应的菜单权限表
 * @TableName sys_role_2_menu
 */
public class SysRole2Menu implements Serializable {
    /**
     * 角色ID
     */
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

    /**
     * 角色ID
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 角色ID
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 菜单ID
     */
    public Integer getMenuId() {
        return menuId;
    }

    /**
     * 菜单ID
     */
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    /**
     * 0:半选. 1:全选
     */
    public Integer getCheckType() {
        return checkType;
    }

    /**
     * 0:半选. 1:全选
     */
    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SysRole2Menu other = (SysRole2Menu) that;
        return (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()))
            && (this.getMenuId() == null ? other.getMenuId() == null : this.getMenuId().equals(other.getMenuId()))
            && (this.getCheckType() == null ? other.getCheckType() == null : this.getCheckType().equals(other.getCheckType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        result = prime * result + ((getMenuId() == null) ? 0 : getMenuId().hashCode());
        result = prime * result + ((getCheckType() == null) ? 0 : getCheckType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", roleId=").append(roleId);
        sb.append(", menuId=").append(menuId);
        sb.append(", checkType=").append(checkType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}