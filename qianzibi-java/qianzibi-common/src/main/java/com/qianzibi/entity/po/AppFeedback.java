package com.qianzibi.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 问题反馈
 * @TableName app_feedback
 */
@Data
public class AppFeedback implements Serializable {
    /**
     * 自增ID
     */
    private Integer feedbackId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 父级ID
     */
    private Integer pFeedbackId;

    /**
     * 状态 0:未回复 1:已回复
     */
    private Integer status;

    /**
     * 0:访客 1:管理员
     */
    private Integer sendType;

    /**
     * 访客最后发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.UPDATE)
    private Date clientLastSendTime;

    private static final long serialVersionUID = 1L;
}