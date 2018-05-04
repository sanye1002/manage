package com.sanye.manage.dataobject;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-09 下午 6:19
 * 开支详情
 */
@Entity
@Data
@Table(name = "spending_info")
public class SpendingInfo {
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 申请人员id
     */
    private Integer personnelId;
    /**
     * 申请金额
     */
    private BigDecimal salary;

    /**
     * 申请原因
     */
    @Column(length = 512)
    private String description;
    /**
     * 申请人员姓名
     */
    private String personnelName;
    /**
     * img
     */
    @Column(columnDefinition = "TEXT")
    private String img;
    /**
     * 审核人员
     */
    private Integer checkPersonnelId;
    /**
     * 审核人员
     */
    private String checkPersonnelName;
    /**
     * 审核时间
     */
    private String checkTime;

    private String month;


    /**
     * 审核结果
     */
    private Integer resultStatus;
    /**
     * 审核状态
     */
    private Integer checkStatus;
    /**
     *
     */
    private String deptNo;

    private String title;

    private String resultRemark;


}
