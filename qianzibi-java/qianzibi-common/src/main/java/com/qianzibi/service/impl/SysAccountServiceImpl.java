package com.qianzibi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianzibi.common.ResultCode;
import com.qianzibi.entity.config.AppConfig;
import com.qianzibi.entity.dto.SessionUserAdminDto;
import com.qianzibi.entity.enums.MenuTypeEnum;
import com.qianzibi.entity.enums.SysAccountStatusEnum;
import com.qianzibi.entity.enums.UserStatusEnum;
import com.qianzibi.entity.po.SysAccount;
import com.qianzibi.entity.po.SysMenu;
import com.qianzibi.entity.query.SysMenuQuery;
import com.qianzibi.entity.vo.SysMenuVO;
import com.qianzibi.exception.BusinessException;
import com.qianzibi.mapper.SysAccountMapper;
import com.qianzibi.service.SysAccountService;
import com.qianzibi.service.SysMenuService;
import com.qianzibi.utils.CopyTools;
import com.qianzibi.utils.MD5;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Resource
    private AppConfig appConfig;

    @Override
    public SessionUserAdminDto login(String phone, String password) {
//        LambdaQueryWrapper<SysAccount> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(SysAccount::getPhone, phone);
//        SysAccount sysAccount = sysAccountMapper.selectOne(queryWrapper);
//        if (sysAccount == null) {
//            throw new BusinessException("账号或密码错误");
//        }
//        if (sysAccount.getStatus().equals(SysAccountStatusEnum.DISABLE.getStatus())) {
//            throw new BusinessException("账号已禁用");
//        }
//        if (!sysAccount.getPassword().equals(password)) {
//            throw new BusinessException("账号或密码错误");
//        }
//
//        // 获取菜单
//        SysMenuQuery query = new SysMenuQuery();
//        query.setFormate2Tree(false);
//        query.setOrderByAsc("sort");
//        List<SysMenu> sysMenuList = sysMenuService.findLisByParam(query);
//        // 获取权限
//        List<String> permissionCodeList = new ArrayList<>();
//        sysMenuList.forEach(item -> permissionCodeList.add(item.getPermissionCode()));
//
//        sysMenuList = sysMenuService.convertLine2Tree4Menu(sysMenuList, 0);
//        // 菜单封装vo
//        List<SysMenuVO> menuVOList = new ArrayList<>();
//        sysMenuList.forEach(item -> {
//            SysMenuVO menuVO = CopyTools.copy(item, SysMenuVO.class);
//            menuVO.setChildren(CopyTools.copyList(item.getChildren(), SysMenuVO.class));
//            menuVOList.add(menuVO);
//        });
//
//        SessionUserAdminDto adminDto = new SessionUserAdminDto();
//        adminDto.setSuperAdmin(true);
//        adminDto.setUserId(sysAccount.getUserId());
//        adminDto.setUserName(sysAccount.getUserName());
//        adminDto.setMenuList(menuVOList);
//        adminDto.setPermissionCodeList(permissionCodeList);
//        return adminDto;

        //判断空值和密码正确、用户是否被禁用
        LambdaQueryWrapper<SysAccount> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysAccount::getPhone, phone);
        SysAccount sysUser = sysAccountMapper.selectOne(queryWrapper);
        if (sysUser.getStatus().equals(SysAccountStatusEnum.DISABLE.getStatus())) {
            throw new BusinessException(ResultCode.ERROR_OTHER, "用户已被禁用");
        }

//        // 后端测试，在这里进行Md5加密
//        if (!MD5.encrypt(password).equals(sysUser.getPassword())) {
//            throw new BusinessException(ResultCode.ERROR_OTHER, "账号或者密码错误");
//        }
        // 前端进行md5加密
        if (!password.equals(sysUser.getPassword())) {
            throw new BusinessException(ResultCode.ERROR_OTHER, "账号或者密码错误");
        }

        SessionUserAdminDto adminDto = new SessionUserAdminDto();
        adminDto.setUserId(sysUser.getUserId());
        adminDto.setUserName(sysUser.getUserName());

        List<SysMenu> sysMenuList;
        String[] split = appConfig.getSuperAdminPhones().split(",");
        // 如果是超级管理员则获取所有信息
        if (StringUtils.hasText(appConfig.getSuperAdminPhones()) && ArrayUtils.contains(split, phone)) {
            adminDto.setSuperAdmin(true);
            // 查询所有表sys_menu中所有数据
            SysMenuQuery query = new SysMenuQuery();
            query.setOrderByAsc("sort");
            // 这条数据是为了不将数据放到"所有菜单"下
            query.setFormate2Tree(false);
            sysMenuList = sysMenuService.findLisByParam(query);
        } else {
            adminDto.setSuperAdmin(false);
            //根据权限获取menu列表
            sysMenuList = sysMenuService.getAllMenuByRoleIds(sysUser.getRoles());
        }

        List<String> permissionCodeList = new ArrayList<>();
        List<SysMenu> menuList = new ArrayList<>();
        // 区分菜单和按钮,如果是菜单则加入到menuList中
        sysMenuList.forEach(item -> {
            if (MenuTypeEnum.MEMU.getType().equals(item.getMenuType())) {
                menuList.add(item);
            }
            // 获取所有权限编码
            permissionCodeList.add(item.getPermissionCode());
        });
        // 如果在menuList完成后还是为空则说明没有权限
        if (menuList.isEmpty()) {
            throw new BusinessException(ResultCode.NOT_FOUND, "请联系管理员分配角色");
        }
        // 递归排序
        sysMenuList = sysMenuService.convertLine2Tree4Menu(sysMenuList, 0);

        List<SysMenuVO> menuListVO = new ArrayList<>();
        // 将数据放到menuListVO中,以便返回前端
        sysMenuList.forEach(item -> {
            SysMenuVO menuVO = CopyTools.copy(item, SysMenuVO.class);
            menuVO.setChildren(CopyTools.copyList(item.getChildren(), SysMenuVO.class));
            menuListVO.add(menuVO);
        });

        adminDto.setPermissionCodeList(permissionCodeList);
        adminDto.setMenuList(menuListVO);
        return adminDto;
    }

    @Override
    public void saveAccountUser(SysAccount sysAccount) {
        SysAccount selectByPhone_Account = sysAccountMapper.selectOne(new QueryWrapper<SysAccount>().eq("phone", sysAccount.getPhone()));
        // 如果根据手机查询数据库结果不为空,且(传入的UserId为空 或 如果根据手机查询数据库结果的 UserId 不等于 前端传入的值的UserId)
        if (selectByPhone_Account != null
                && (sysAccount.getUserId() == null || !selectByPhone_Account.getUserId().equals(sysAccount.getUserId()))) {
            throw new BusinessException(ResultCode.ERROR_OTHER, "该手机号已经被使用");
        }
        if (sysAccount.getUserId() == null) {
            sysAccount.setStatus(UserStatusEnum.ENABLE.getStatus());
            sysAccount.setPassword(MD5.encrypt(sysAccount.getPassword()));
            sysAccountMapper.insert(sysAccount);
        } else {
            sysAccount.setStatus(null);
            sysAccount.setPassword(null);
            sysAccountMapper.updateById(sysAccount);
        }
    }
}




