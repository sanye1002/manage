package com.sanye.manage.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-13 上午 11:05
 * @Description description
 * 短信通知
 */
@Data
@Entity
@Table(name = "notice_message")
public class NoticeMessage {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer sendUserId;
    //y用户id
    private Integer userId;
    //用户手机
    private String phone;
    //用户姓名
    private String name;
    //内容
    private String content;
    //时间
    private String time;
    //通知结果
    private Integer status;
}
