package com.qianzibi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianzibi.exception.BusinessException;
import com.qianzibi.entity.enums.SysAccountStatusEnum;
import com.qianzibi.entity.po.SysAccount;
import com.qianzibi.service.SysAccountService;
import com.qianzibi.mapper.SysAccountMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Override
    public void login(String phone, String password) {
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
    }
}




