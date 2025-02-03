package com.qianzibi.service;

import com.qianzibi.entity.dto.SessionUserAdminDto;
import com.qianzibi.entity.po.SysAccount;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86158
* @description 针对表【sys_account(账号信息)】的数据库操作Service
* @createDate 2025-01-22 21:00:52
*/
public interface SysAccountService extends IService<SysAccount> {

    SessionUserAdminDto login(String phone, String password);

    void saveAccountUser(SysAccount sysAccount);
}
