package com.sanye.manage.DTO;

import com.sanye.manage.dataobject.UserInfo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-15 上午 11:38
 * @Description description
 */
@Data
public class PersonnelSalaryDTO {
    private Integer id;

    private String month; //创建时间

    private UserInfo userInfo;
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
