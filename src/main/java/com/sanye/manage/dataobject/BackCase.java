package com.sanye.manage.dataobject;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-10 下午 12:28
 * @Description description
 */
@Data
@Entity
@Table(name = "back_case")
public class BackCase {
    @Id
    @GeneratedValue
    private Integer id;

    private String month;
    /**
     * 创建时间
     */
    private String createTime;

    private Integer userId;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    @Column(columnDefinition = "TEXT")
    private String content;
    /**
     * 姓名
     */
    private String username;
    /**
     * 审核人员
     */
    private Integer checkPersonnelId;

    private String checkPersonnelName;
    /**
     * 审核时间
     */
    private String checkTime;
    /**
     * 审核结果
     */
    private Integer checkStatus=0;

    private Integer resultStatus=0;

    private String resultRemark;
}
