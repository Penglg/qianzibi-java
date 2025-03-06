package com.qianzibi.controller;

import com.qianzibi.annotation.Check;
import com.qianzibi.annotation.VerifyParam;
import com.qianzibi.entity.constants.Constants;
import com.qianzibi.entity.dto.SessionUserAdminDto;
import com.qianzibi.entity.enums.PermissionCodeEnum;
import com.qianzibi.entity.enums.PostStatusEnum;
import com.qianzibi.entity.po.ShareInfo;
import com.qianzibi.entity.query.ShareInfoQuery;
import com.qianzibi.service.ShareInfoService;
import com.qianzibi.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/shareInfo")
public class ShareController {

    @Resource
    private ShareInfoService shareInfoService;

    /**
     * 加载
     */
    @PostMapping("/loadDataList")
    @Check(permissionCode = PermissionCodeEnum.SHARE_LIST)
    public R loadDataList(ShareInfoQuery query) {
        // 获取内容
        query.setQueryTextContent(true);
        return R.ok().data(shareInfoService.selectPage(query));
    }

    // TODO 优化: 修改效率低藕合度高

    /**
     * 发布分享
     */
    @PostMapping("/postShare")
    @Check(permissionCode = PermissionCodeEnum.SHARE_POST)
    public R postShare(@VerifyParam(required = true) String shareIds) {
        ShareInfoQuery shareInfoQuery = new ShareInfoQuery();
        shareInfoQuery.setStatus(PostStatusEnum.POST.getStatus());
        shareInfoService.updateStatus(shareInfoQuery, shareIds);
        return R.ok();
    }

    /**
     * 撤销发布
     */
    // TODO 优化: 修改效率低藕合度高
    @PostMapping("/cancelPostShare")
    @Check(permissionCode = PermissionCodeEnum.SHARE_POST)
    public R cancelPostShare(@VerifyParam(required = true) String shareIds) {
        ShareInfoQuery shareInfoQuery = new ShareInfoQuery();
        shareInfoQuery.setStatus(PostStatusEnum.NO_POST.getStatus());
        shareInfoService.updateStatus(shareInfoQuery, shareIds);
        return R.ok();
    }

    /**
     * 保存
     */
    @PostMapping("/saveShareInfo")
    @Check(permissionCode = PermissionCodeEnum.SHARE_EDIT)
    public R saveShareInfo(HttpSession session, Integer shareId,
                           @VerifyParam(required = true) String title,
                           @VerifyParam(required = true) Integer coverType,
                           String coverPath,
                           @VerifyParam(required = true) String content) {

        SessionUserAdminDto sessionUserAdminDto = (SessionUserAdminDto) session.getAttribute(Constants.SESSION_KEY);
        ShareInfoQuery shareInfoQuery = new ShareInfoQuery();
        shareInfoQuery.setShareId(shareId);
        shareInfoQuery.setTitle(title);
        shareInfoQuery.setCoverType(coverType);
        shareInfoQuery.setCoverPath(coverPath);
        shareInfoQuery.setContent(content);
        shareInfoQuery.setCreateUserId(String.valueOf(sessionUserAdminDto.getUserId()));
        shareInfoQuery.setCreateUserName(sessionUserAdminDto.getUserName());
        shareInfoService.saveShareInfo(shareInfoQuery, sessionUserAdminDto.getSuperAdmin());
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delShare")
    @Check(permissionCode = PermissionCodeEnum.SHARE_DEL)
    public R delShare(HttpSession session, @VerifyParam(required = true) String shareIds) {
        SessionUserAdminDto sessionAttribute = (SessionUserAdminDto) session.getAttribute(Constants.SESSION_KEY);
        String[] splitIds = shareIds.split(",");
        shareInfoService.removeBatchShareInfo(splitIds, sessionAttribute.getSuperAdmin() ? null : sessionAttribute.getUserId());
        return R.ok();
    }

    /**
     * 删除
     * @param session
     * @param shareIds
     * @return
     */
    @RequestMapping("/delShareBatch")
    @Check(permissionCode = PermissionCodeEnum.SHARE_DEL_BATCH)
    public R delShareBatch(HttpSession session, @VerifyParam(required = true) String shareIds) {
        SessionUserAdminDto sessionAttribute = (SessionUserAdminDto) session.getAttribute(Constants.SESSION_KEY);
        String[] splitIds = shareIds.split(",");
        shareInfoService.removeBatchShareInfo(splitIds, sessionAttribute.getSuperAdmin() ? null : sessionAttribute.getUserId());
        return R.ok();
    }

    /**
     * 展示
     */
    @PostMapping("/showShareDetailNext")
    @Check(permissionCode = PermissionCodeEnum.APP_UPDATE_LIST)
    public R showShare(ShareInfoQuery query,
                       Integer nextType,
                       @VerifyParam(required = true) Integer currentId) {

        ShareInfo shareInfo = shareInfoService.showDetailNext(query, nextType, currentId, false);

        return R.ok().data(shareInfo);
    }


}
