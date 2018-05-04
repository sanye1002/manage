package com.sanye.manage.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-09 下午 6:38
 * 通知工作人员表
 */
@Data
@Entity
@Table(name = "notice_personnel")
public class NoticePersonnel {
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 通知表Id
     */
    private Integer noticeId;
    /**
     * 用户ID
     */
    private Integer personnelId;
    /**
     * 阅读状态
     */
    private Integer readStatus;
    /**
     * 阅读时间
     */
    private String readTime;
}
