package com.qianzibi.mapper;

import com.qianzibi.entity.po.ShareInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianzibi.entity.query.ShareInfoQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 86158
* @description 针对表【share_info(文章)】的数据库操作Mapper
* @createDate 2025-02-04 21:32:18
* @Entity com.qianzibi.entity.po.ShareInfo
*/
public interface ShareInfoMapper extends BaseMapper<ShareInfo> {

    void updateByShareInfoId(@Param("shareIds") List<String> shareIds, @Param("query") ShareInfoQuery shareInfoQuery);
}




