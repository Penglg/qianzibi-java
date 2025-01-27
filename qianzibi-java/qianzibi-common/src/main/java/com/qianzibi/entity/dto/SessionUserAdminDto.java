package com.qianzibi.entity.dto;

import com.qianzibi.entity.vo.SysMenuVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户session信息
 */
@Data
public class SessionUserAdminDto implements Serializable {

    private Integer userId;

    private String userName;

    private Boolean superAdmin;

    private List<SysMenuVO> menuList;

    private List<String> permissionCodeList;

    private static final long serialVersionUID = 1L;
}
