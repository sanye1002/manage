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
@Table(name = "feedback_info")
public class FeedbackInfo {
    //id
    @Id
    @GeneratedValue
    private Integer id;

    //标题
    private String title;

    //类型 id
    private Integer feedbackTypeId;
    //发送时间
    private String sendTime;
    //发送人
    private Integer sendUserId;
    //状态
    private Integer backStatus=0;

    private Integer accentUserId;

}
