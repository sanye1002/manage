package com.sanye.manage.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-10 下午 9:09
 * @Description description
 */
@Entity
@Data
@Table(name = "feedback_detail")
public class FeedbackDetail {
    @Id
    @GeneratedValue
    private Integer id;
    //反馈id
    private Integer feedbackInfoId;
    //发送内容
    private String content;
    //发送时间
    private String sendTime;
    //发送人
    private Integer sendUserId;
    //处理人
    private Integer backUserId;
    //处理时间
    private String backTime;
    //处理内容
    private String backContent;
}
