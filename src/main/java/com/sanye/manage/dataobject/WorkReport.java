package com.sanye.manage.dataobject;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-09 下午 6:46
 * 工作报告
 */
@Data
@Entity
@Table(name = "work_report")
public class WorkReport {
    @Id
    @GeneratedValue
    private Integer id;
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

    /**
     * 发布人员
     */
    private Integer personnelId;

    /**
     * 发布时间
     */
    private String crateTime;



}
