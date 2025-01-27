package com.qianzibi.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysMenuVO implements Serializable {
    /**
     * 菜单名
     */
    private String menuName;

    /**
     * 菜单跳转到的地址
     */
    private String menuUrl;
    /**
     * 图标
     */
    private String icon;

    private List<SysMenuVO> children;

    private static final long serialVersionUID = 1L;
}