package com.sanye.manage.dataobject;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-09 下午 6:33
 * 通知详情表
 */
@Data
@Entity
@Table(name = "notice_detail")
public class NoticeDetail {

    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 发布人员id
     */
    private Integer userId;
    /**
     * 发布人员姓名
     */
    private String userName;
    /**
     * 标题
     */
    @Column(length = 512)
    private String title;
    /**
     * 内容
     */
    @Column(columnDefinition = "TEXT")
    private String content;


}
