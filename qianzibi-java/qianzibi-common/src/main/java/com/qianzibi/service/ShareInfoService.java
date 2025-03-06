package com.qianzibi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianzibi.entity.po.ShareInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianzibi.entity.query.ShareInfoQuery;

/**
* @author 86158
* @description 针对表【share_info(文章)】的数据库操作Service
* @createDate 2025-02-04 21:32:18
*/
public interface ShareInfoService extends IService<ShareInfo> {

    Page selectPage(ShareInfoQuery query);

    void updateStatus(ShareInfoQuery shareInfoQuery, String shareIds);

    Integer saveShareInfo(ShareInfoQuery shareInfoQuery, Boolean superAdmin);

    void removeBatchShareInfo(String[] splitIds, Integer integer);

    ShareInfo showDetailNext(ShareInfoQuery query, Integer nextType, Integer currentId, boolean b);
}
