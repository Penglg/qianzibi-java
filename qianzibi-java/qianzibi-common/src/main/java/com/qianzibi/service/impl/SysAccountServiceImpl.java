package com.qianzibi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianzibi.entity.dto.SessionUserAdminDto;
import com.qianzibi.entity.po.SysMenu;
import com.qianzibi.entity.query.SysMenuQuery;
import com.qianzibi.entity.vo.SysMenuVO;
import com.qianzibi.exception.BusinessException;
import com.qianzibi.entity.enums.SysAccountStatusEnum;
import com.qianzibi.entity.po.SysAccount;
import com.qianzibi.service.SysAccountService;
import com.qianzibi.mapper.SysAccountMapper;
import com.qianzibi.service.SysMenuService;
import com.qianzibi.utils.CopyTools;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author 86158
* @description 针对表【sys_account(账号信息)】的数据库操作Service实现
* @createDate 2025-01-22 21:00:52
*/
@Service
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccount>
    implements SysAccountService {
    @Resource
    private SysAccountMapper sysAccountMapper;

    @Resource
    private SysMenuService sysMenuService;

    @Override
    public SessionUserAdminDto login(String phone, String password) {
        LambdaQueryWrapper<SysAccount> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysAccount::getPhone, phone);
        SysAccount sysAccount = sysAccountMapper.selectOne(queryWrapper);
        if (sysAccount == null) {
            throw new BusinessException("账号或密码错误");
        }
        if (sysAccount.getStatus().equals(SysAccountStatusEnum.DISABLE.getStatus())) {
            throw new BusinessException("账号已禁用");
        }
        if (!sysAccount.getPassword().equals(password)) {
            throw new BusinessException("账号或密码错误");
        }

        // 获取菜单
        SysMenuQuery query = new SysMenuQuery();
        query.setFormate2Tree(false);
        query.setOrderByAsc("sort");
        List<SysMenu> sysMenuList = sysMenuService.findLisByParam(query);
        // 获取权限
        List<String> permissionCodeList = new ArrayList<>();
        sysMenuList.forEach(item -> {
            permissionCodeList.add(item.getPermissionCode());
        });

        sysMenuList = sysMenuService.convertLine2Tree4Menu(sysMenuList, 0);
        // 菜单封装vo
        List<SysMenuVO> menuVOList = new ArrayList<>();
        sysMenuList.forEach(item -> {
            SysMenuVO menuVO = CopyTools.copy(item, SysMenuVO.class);
            menuVO.setChildren(CopyTools.copyList(item.getChildren(), SysMenuVO.class));
            menuVOList.add(menuVO);
        });

        SessionUserAdminDto adminDto = new SessionUserAdminDto();
        adminDto.setSuperAdmin(true);
        adminDto.setUserId(sysAccount.getUserId());
        adminDto.setUserName(sysAccount.getUserName());
        adminDto.setMenuList(menuVOList);
        adminDto.setPermissionCodeList(permissionCodeList);
        return adminDto;
    }
}




