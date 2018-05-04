package com.sanye.manage.form;

import com.sanye.manage.utils.GetTimeUtil;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-18 下午 6:31
 * @Description description
 */
@Data
public class SpendingForm {

    private Integer id;

    /**
     * 申请人员id
     */
    private Integer personnelId;
    /**
     * 申请金额
     */
    private BigDecimal salary;

    @NotEmpty(message = "标题不能为空！")
    private String title;

    /**
     * 申请原因
     */
    @NotEmpty(message = "原因描述不能为空！")
    private String description;
    /**
     * 申请人员姓名
     */
    private String personnelName;
    /**
     * img
     */

    private String img;

    /**
     * 审核结果
     */
    private Integer resultStatus=0;
    /**
     * 审核状态
     */
    private Integer checkStatus =0;
    /**
     *
     */
    private String deptNo;

    private String createTime = GetTimeUtil.getTime();
    private String month = GetTimeUtil.getMonth();


}
