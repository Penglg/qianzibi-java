package com.qianzibi.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName exam_question_item
 */
@Data
public class ExamQuestionItem implements Serializable {
    /**
     * 选项ID
     */
    @TableId(type = IdType.AUTO)
    private Integer itemId;

    /**
     * 问题ID
     */
    private Integer questionId;

    /**
     * 标题
     */
    private String title;

    /**
     * 排序
     */
    private Integer sort;

    private static final long serialVersionUID = 1L;
}