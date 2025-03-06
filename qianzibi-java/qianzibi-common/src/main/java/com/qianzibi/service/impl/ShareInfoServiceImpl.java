package com.qianzibi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianzibi.common.ResultCode;
import com.qianzibi.entity.constants.Constants;
import com.qianzibi.entity.po.ShareInfo;
import com.qianzibi.entity.query.ShareInfoQuery;
import com.qianzibi.exception.BusinessException;
import com.qianzibi.mapper.ACommonMapper;
import com.qianzibi.service.ShareInfoService;
import com.qianzibi.mapper.ShareInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 86158
* @description 针对表【share_info(文章)】的数据库操作Service实现
* @createDate 2025-02-04 21:32:18
*/
@Service
public class ShareInfoServiceImpl extends ServiceImpl<ShareInfoMapper, ShareInfo>
    implements ShareInfoService{

    @Resource
    private ShareInfoMapper shareInfoMapper;

    @Resource
    private ACommonMapper aCommonMapper;

    @Override
    public Page<ShareInfo> selectPage(ShareInfoQuery query) {
        ShareInfo shareInfo = new ShareInfo();
        BeanUtils.copyProperties(query, shareInfo);
        // TODO: 是否可以不加shareInfo的参数
        LambdaQueryWrapper<ShareInfo> queryWrapper = new LambdaQueryWrapper<>(shareInfo);

        // 是否查询内容
        if (!query.getQueryTextContent()) {
            // 排除内容的查询
            queryWrapper.select(ShareInfo.class, tableFieldInfo -> !tableFieldInfo.getColumn().equals("content"));
        }
        queryWrapper.like(query.getTitleFuzzy() != null, ShareInfo::getTitle, query.getTitleFuzzy());
        queryWrapper.like(query.getCreateUserNameFuzzy() != null, ShareInfo::getCreateUserName, query.getCreateUserNameFuzzy());
        queryWrapper.eq(query.getStatus() != null, ShareInfo::getStatus, query.getStatus());
        queryWrapper.orderByDesc(ShareInfo::getShareId);

        return shareInfoMapper.selectPage(new Page<>(query.getCurrent(), query.getPageSize()), queryWrapper);
    }

    @Override
    public void updateStatus(ShareInfoQuery shareInfoQuery, String shareIds) {
        List<String> shareId_list = Arrays.asList(shareIds.split(","));
        QueryWrapper<ShareInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("share_id", shareId_list);
        shareInfoMapper.updateByShareInfoId(shareId_list, shareInfoQuery);
    }

    @Override
    public Integer saveShareInfo(ShareInfoQuery shareInfoQuery, Boolean superAdmin) {
        int saveDateNum;

        ShareInfo shareInfo = new ShareInfo();
        shareInfo.setTitle(shareInfoQuery.getTitle());
        shareInfo.setCoverPath(shareInfoQuery.getCoverPath());
        shareInfo.setCoverType(shareInfoQuery.getCoverType());
        shareInfo.setCreateUserId(shareInfoQuery.getCreateUserId());
        shareInfo.setContent(shareInfoQuery.getContent());
        shareInfo.setCreateUserName(shareInfoQuery.getCreateUserName());
        if (shareInfoQuery.getShareId() == null) {
            saveDateNum = shareInfoMapper.insert(shareInfo);
        } else {
            ShareInfo dbShareInfo = shareInfoMapper.selectById(shareInfoQuery.getShareId());
            if (shareInfoQuery.getCreateUserId().equals(dbShareInfo.getCreateUserId()) && !superAdmin) {
                throw new BusinessException(ResultCode.ERROR_600, "该用户无法修改其他用户的数据");
            }
            shareInfo.setShareId(shareInfoQuery.getShareId());
            saveDateNum = shareInfoMapper.updateById(shareInfo);
        }

        return saveDateNum;
    }

    @Override
    public void removeBatchShareInfo(String[] shareIds, Integer userid) {
        List<String> ShareIds_list = Arrays.asList(shareIds);
        QueryWrapper<ShareInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("share_id", "create_user_id", "create_user_name");
        queryWrapper.in("share_id", ShareIds_list);
        List<ShareInfo> dbShareInfo = shareInfoMapper.selectList(queryWrapper);
        if (userid != null) {
            List<ShareInfo> badShareInfo = dbShareInfo.stream().filter(item -> !item.getCreateUserId().equals(String.valueOf(userid))).collect(Collectors.toList());
            if (!badShareInfo.isEmpty()) {
                throw new BusinessException(ResultCode.ERROR_NOPERMISSION, "删除的队列中存在非该用户创建的数据");
            }
        }
        shareInfoMapper.deleteBatchIds(ShareIds_list);
    }

    @Override
    public ShareInfo showDetailNext(ShareInfoQuery query, Integer nextType, Integer currentId, boolean updateReadCount) {
//        if (nextType == null) {
//            query.setShareId(currentId);
//        } else {
//            query.setNextType(nextType);
//            query.setCurrentId(currentId);
//        }
        // 获取问题详情
        QueryWrapper<ShareInfo> queryWrapper = new QueryWrapper<>();
        if (nextType == null) {
            queryWrapper.eq("share_id", currentId);
        } else if (nextType == 1) {
            queryWrapper.lt("share_id", currentId);
            queryWrapper.orderByDesc("share_id");
        } else if (nextType == -1) {
            queryWrapper.gt("share_id", currentId);
            queryWrapper.orderByAsc("share_id");
        }
        queryWrapper.last("limit 0,1");
        ShareInfo shareInfo = shareInfoMapper.selectOne(queryWrapper);
        if (shareInfo == null && nextType == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "内容不存在");
        } else if (shareInfo == null && nextType == -1) {
            throw new BusinessException(ResultCode.ERROR_OTHER, "已经是第一条了");
        } else if (shareInfo == null && nextType == 1) {
            throw new BusinessException(ResultCode.ERROR_OTHER, "已经是最后一条了");
        }
        if (updateReadCount && shareInfo != null) {
            aCommonMapper.updateCount(Constants.TABLE_NAME_SHARE_INFO, 1, null, currentId);
            shareInfo.setReadCount((shareInfo.getReadCount() == null ? 0 : shareInfo.getReadCount()) + 1);
        }
        return shareInfo;
    }
}




