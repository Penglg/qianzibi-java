package com.qianzibi.entity.query;

import lombok.Data;

@Data
public class BaseParam {
    // 第几页
    private Integer pageNo;

    // 总数
    private Integer countTotal;

    // 页数大小
    private Integer pageSize;

    // 总页数
    private Integer pageTotal;

    private Integer start;

    private Integer end;

    private String orderByDesc;

    private String orderByAsc;
}
