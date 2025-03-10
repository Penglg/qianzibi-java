package com.qianzibi.mapper;

import com.qianzibi.entity.po.ExamQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 86158
* @description 针对表【exam_question(考试题目)】的数据库操作Mapper
* @createDate 2025-02-04 21:32:08
* @Entity com.qianzibi.entity.po.ExamQuestion
*/
public interface ExamQuestionMapper extends BaseMapper<ExamQuestion> {

    void updateBatchStatus(@Param("List") List<String> questionIds, @Param("query") ExamQuestion examQuestion);

    void insertBatchSomeColumn(List<ExamQuestion> examQuestionList);
}




