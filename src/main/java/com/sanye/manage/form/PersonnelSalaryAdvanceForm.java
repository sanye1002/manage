package com.sanye.manage.form;

import com.sanye.manage.utils.GetTimeUtil;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-24 下午 5:15
 * @Description description
 */
@Data
public class PersonnelSalaryAdvanceForm {
    /**
     * month
     */
    private String month = GetTimeUtil.getMonth();
    /**
     * 创建时间
     */
    private String createTime = GetTimeUtil.getTime();
    /**
     * 人员id
     */
    private Integer personnelId;
    /**
     * 预支金额
     */
    private BigDecimal salary;

    @NotEmpty(message = "标题不能为空！")
    private String title;

    /**
     * 预支原因
     */
    @NotEmpty(message = "原因描述不能为空！")
    private String description;
    /**
     * 人员姓名
     */
    private String personnelName;

    /**
     * 审核结果
     */
    private Integer checkStatus =0;

    /**
     * 审核结果
     */
    private Integer resultStatus =0;

    private Integer backStatus=0;


}
