package com.qianzibi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianzibi.annotation.VerifyParam;
import com.qianzibi.common.ResultCode;
import com.qianzibi.entity.enums.MenuCheckTypeEnum;
import com.qianzibi.entity.enums.ResponseCodeEnum;
import com.qianzibi.entity.po.SysAccount;
import com.qianzibi.entity.po.SysRole;
import com.qianzibi.entity.po.SysRole2Menu;
import com.qianzibi.exception.BusinessException;
import com.qianzibi.mapper.SysAccountMapper;
import com.qianzibi.mapper.SysRole2MenuMapper;
import com.qianzibi.mapper.SysRoleMapper;
import com.qianzibi.service.SysRole2MenuService;
import com.qianzibi.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 86158
 * @description 针对表【sys_role(系统角色表)】的数据库操作Service实现
 * @createDate 2025-01-26 17:10:10
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
        implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysRole2MenuMapper sysRole2MenuMapper;

    @Resource
    private SysAccountMapper sysAccountMapper;

    @Resource
    private SysRole2MenuService sysRole2MenuService;

    /**
     * @Description 保存角色
     * menuIds多个menuId构成的字符串;
     * halfMenuIds是否全选,因为给角色分配menu权限时menu是一个树的形式,勾选父选项可以选择父选项下所有的字选项
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(SysRole sysRole, String menuIds, String halfMenuIds) {
        Integer roleId = sysRole.getRoleId();
        boolean addMenu = false;
        // 保证角色名称唯一性
        if (sysRole.getRoleName() != null) {
            LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysRole::getRoleName, sysRole.getRoleName());
            SysRole sysRole1 = sysRoleMapper.selectOne(queryWrapper);
            if (sysRole1 != null) {
                throw new BusinessException(ResponseCodeEnum.CODE_601.getCode(), "角色名称重复");
            }
        }
        if (sysRole.getRoleId() == null) {
            sysRoleMapper.insert(sysRole);
            LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysRole::getRoleName, sysRole.getRoleName());
            sysRole = sysRoleMapper.selectOne(queryWrapper);
            roleId = sysRole.getRoleId();
            addMenu = true;
        } else {
            sysRoleMapper.updateById(sysRole);
        }

        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getRoleName, sysRole.getRoleName());
        int roleCount = Math.toIntExact(baseMapper.selectCount(queryWrapper));
        if (roleCount > 1) {
            throw new BusinessException(ResultCode.ERROR_OTHER, "角色已经存在");
        }
        // 到menu和role的连接表role_2_menu中添加权限
        if (addMenu) {
            saveRoleMenu(roleId, menuIds, halfMenuIds);
        }
    }

    /**
     * @Description 更改角色权限
     */
    // TODO menuIds出现空指针
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleMenu(Integer roleId, String menuIds, String halfMenuIds) {
        if (null == roleId || menuIds == null) {
            System.out.println("********roleId********" + roleId);
            System.out.println("********menuIds********" + menuIds);
            throw new BusinessException(ResponseCodeEnum.CODE_600);

        }

        // 删除当前角色ID已有角色对应
        LambdaQueryWrapper<SysRole2Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole2Menu::getRoleId, roleId);
        sysRole2MenuMapper.delete(queryWrapper);
        // 返回menuId的字符串.用","分隔,打断其使其成为列表
        String[] menuIdsArray = menuIds.split(",");
        String[] halfMenuIdArray = !StringUtils.hasText(halfMenuIds) ? new String[]{} : halfMenuIds.split(",");

        List<SysRole2Menu> role2MenuList = new ArrayList<>();
        // MenuCheckTypeEnum.ALL 代表全选
        convertMenuId2RoleMenu(role2MenuList, roleId, menuIdsArray, MenuCheckTypeEnum.ALL);
        // MenuCheckTypeEnum.HALF 代表半选
        convertMenuId2RoleMenu(role2MenuList, roleId, halfMenuIdArray, MenuCheckTypeEnum.HALF);
        // 不为空则批量存入role2Menu
        if (!role2MenuList.isEmpty()) {
            sysRole2MenuService.saveBatch(role2MenuList);
        }
    }

    /**
     * @Description 将menuIdArray中的menuid存入role2Menu中并根据设置role2Menu的CheckType属性, 方便后续吧role2Menu存入数据库
     * @MethodName checkTypeEnum:检查全选or半选
     */
    private void convertMenuId2RoleMenu(List<SysRole2Menu> role2MenuList,
                                        Integer roleId,
                                        String[] menuIdArray,
                                        MenuCheckTypeEnum checkTypeEnum) {
        for (String menuId : menuIdArray) {
            SysRole2Menu role2Menu = new SysRole2Menu();
            role2Menu.setMenuId(Integer.parseInt(menuId));
            role2Menu.setRoleId(roleId);
            role2Menu.setCheckType(checkTypeEnum.getType());

            role2MenuList.add(role2Menu);
        }
    }

    /**
     * 根据role获取对象
     */
    @Override
    public SysRole getSysRoleByRoleId(Integer roleId) {
        // 根据role获取菜单
        SysRole sysRole = sysRoleMapper.selectById(roleId);
        LambdaQueryWrapper<SysRole2Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole2Menu::getRoleId, roleId).eq(SysRole2Menu::getCheckType, MenuCheckTypeEnum.ALL.getType());
        List<SysRole2Menu> selectMenuIds = sysRole2MenuMapper.selectList(queryWrapper);
        List<Integer> menuIds = new ArrayList<>();
        selectMenuIds.forEach(item -> {
            menuIds.add(item.getMenuId());
        });
        sysRole.setMenuIds(menuIds);
        return sysRole;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteRole(Integer roleId) {
        System.out.println("deleteRole" + roleId);
        Long roles = sysAccountMapper.selectCount(new QueryWrapper<SysAccount>().like("roles", roleId));
        if (roles > 0) {
            throw new BusinessException(ResultCode.ERROR_OTHER, "角色已被用户使用,请先更改用户角色");
        }
        int count = baseMapper.deleteById(roleId);
        sysRole2MenuMapper.delete(new QueryWrapper<SysRole2Menu>().eq("role_id", roleId));
        return count;
    }
}




