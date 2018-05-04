package com.sanye.manage.dataobject;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-09 下午 6:17
 * 主播工资预支表
 */
@Entity
@Data
@Table(name = "anchor_salary_advance")
public class AnchorSalaryAdvance {
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * month
     */
    private String month;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 主播id
     */
    private Integer userId;
    /**
     * 主播姓名
     */
    private String anchorName;
    /**
     * 平台Id
     */
    private Integer platformId;
    /**
     * 预支金额
     */
    private BigDecimal salary;

    private String title;
    /**
     * 预支原因
     */
    @Column(length = 512)
    private String description;
    /**
     * img
     */
    @Column(length = 512)
    private String img;
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
    private Integer checkStatus;
    /**
     * 归还时间
     */
    private String backTime;
    /**
     * 归还状态
     */
    private Integer backStatus;
    /**
     * 审核结果
     */
    private Integer resultStatus;

    private String resultRemark;
}
