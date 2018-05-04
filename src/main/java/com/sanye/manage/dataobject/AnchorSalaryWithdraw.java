package com.sanye.manage.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-09 下午 6:26
 * 主播工资提现
 */
@Data
@Entity
@Table(name = "anchor_salary_withdraw")
public class AnchorSalaryWithdraw {
    @Id
    @GeneratedValue
    private Integer id;

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
     * 平台Id
     */
    private Integer platformId;
    /**
     * 提现金额
     */
    private BigDecimal salary;
    /**
     * 主播姓名
     */
    private String anchorName;
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

    private Integer resultStatus;

    private String resultRemark;
}
