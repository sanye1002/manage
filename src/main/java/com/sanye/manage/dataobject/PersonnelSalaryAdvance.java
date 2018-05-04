package com.sanye.manage.dataobject;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-09 下午 5:58
 * 工作人员工资预支
 */
@Entity
@Data
@Table(name = "personnel_salary_advance")
public class PersonnelSalaryAdvance {
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
     * 人员id
     */
    private Integer personnelId;
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
     * 人员姓名
     */
    private String personnelName;
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
     * 审核结果
     */
    private Integer resultStatus;
    /**
     * 归还时间
     */
    private String backTime;
    /**
     * 归还状态
     */
    private Integer backStatus;

    private String resultRemark;
}
