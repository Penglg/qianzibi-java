package com.qianzibi.entity.query;

import lombok.Data;

import java.io.Serializable;

/**
 * app轮播参数
 */
@Data
public class AppCarouselQuery extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    private Integer carouselId;

    /**
     * 图片
     */
    private String imgPath;

    private String imgPathFuzzy;

    /**
     * 0:分享1:问题 2:考题 3:外部连接
     */
    private Integer objectType;

    /**
     * 文章ID
     */
    private String objectId;

    private String objectIdFuzzy;

    /**
     * 外部连接
     */
    private String outerLink;

    private String outerLinkFuzzy;

    /**
     * 排序
     */
    private Integer sort;
}
