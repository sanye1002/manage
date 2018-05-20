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
 * @create 2018-04-09 下午 4:03
 *
 */
@Data
@Entity
@Table(name = "personnel_salary")
public class PersonnelSalary {
    @Id
    @GeneratedValue
    private Integer id;

    private String month; //创建时间
    /**
     * id
     */
    private Integer personnelId;
    /**
     * 实际工资
     */
    private String personnelName;
    /**
     * 基本工资
     */
    private String phone;
    /**
     * 奖金
     */
    private BigDecimal baseSalary;
    /**
     * 绩效
     */
    private BigDecimal JIXIAO;
    /**
     * 绩效
     */
    private BigDecimal JIANGJING;
    /**
     * 补贴
     */
    private BigDecimal BUTIE;
    /**
     * 考勤扣款
     */
    private BigDecimal KAOQINGKOUKUAN;
    /**
     * 全勤
     */
    private BigDecimal QUANQING;
    /**
     * 应发合计
     */
    private BigDecimal YINGFAHEJI;
    /**
     * 社保
     */
    private BigDecimal SHEBAO;
    /**
     * 个税
     */
    private BigDecimal GESUI;
    /**
     * 应扣小计
     */
    private BigDecimal YINGKOUXIAOJI;
     /**
     * 应扣小计
     */
    private BigDecimal realSalary;

    /**
     * 备注
     */
    private String remark;

    private Integer confirmStatus =0;

    private Integer grantsStatus = 0;







}
