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
 * @create 2018-04-09 下午 4:50
 */
@Data
@Entity
@Table(name = "mm_anchor_salary")
public class MOMOAnchorSalary {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer anchorId;

    private Integer userId;

    private String month;

    /**
     * 昵称
     */
    private String name;
    /**
     * 直播ID
     */
    private String liveId;
    /**
     * 性别
     */
    private String sex;
    /**
     * 播主等级
     */
    private Integer grade;

    /**
     * 所属经纪人
     */
    private String brokerName;
    /**
     * 经纪人陌陌号
     */
    private String brokerId;

    /**
     * 连麦陌币
     */
    private BigDecimal lianMaiMoBi;
    /**
     * 非连麦陌币
     */
    private Integer feiLianMaiMoBi;
    /**
     * 总陌币
     */
    private Integer allMoBi;
    /**
     * 结算方式 对公
     */
    private String billingMethod;
    /**
     * 播主分成金额(元)
     */
    private BigDecimal boZhuFenChen;
    /**
     * 公会分成金额(元)
     */
    private BigDecimal gongHuiFenChen;
    /**
     * 播主奖励(元)
     */
    private BigDecimal boZhuJiangLi;
    /**
     * 其他奖励(元)
     */
    private BigDecimal qiTaJiangLi;
    /**
     * 当月入会前收益(元)
     */
    private BigDecimal ruHuiQian;
    /**
     * 个税(元)
     */
    private BigDecimal geSui;
    /**
     * 提现金额(元)
     */
    private BigDecimal tiXian;
    /**
     * 风控扣款(元)
     */
    private BigDecimal fengKongKouKuan;
    /**
     * 结算金额(元)
     */
    private BigDecimal billingSalary;
    /**
     * 实际收入(元)
     */
    private BigDecimal realSalary;
    /**
     * 税前
     */
    private BigDecimal beforeTax;
    /**
     * 税后
     */
    private BigDecimal afterTax;
    /**
     * 职业返点
     */
    private BigDecimal fanDian;
    /**
     * 节奏代刷
     */
    private BigDecimal daiShua;
    /**
     * 借款
     */
    private BigDecimal jieKuan;
    /**
     * 最后工资
     */
    private BigDecimal salary;

    /**
     * 系统提现
     */
    private BigDecimal systemTX = new BigDecimal(0);
    /**
     * 可提金额
     */
    private BigDecimal mentionable;

    private String remark;
}
