package com.qianzibi.mapper;

import com.qianzibi.entity.po.QuestionInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
* @author 86158
* @description 针对表【question_info(问题)】的数据库操作Mapper
* @createDate 2025-02-04 21:32:15
* @Entity com.qianzibi.entity.po.QuestionInfo
*/
public interface QuestionInfoMapper extends BaseMapper<QuestionInfo> {

    void updateBatchByQIFId(@Param("questionIds")List<String> list, @Param("query") QuestionInfo questionInfo);

    void insertBatch(@Param("queryList") ArrayList<QuestionInfo> questionList);
}




