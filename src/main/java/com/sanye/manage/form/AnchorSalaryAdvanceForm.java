package com.sanye.manage.form;

import com.sanye.manage.utils.GetTimeUtil;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-25 下午 4:56
 * @Description description
 */
@Data
public class AnchorSalaryAdvanceForm {

    private String month = GetTimeUtil.getMonth();
    /**
     * 创建时间
     */
    private String createTime = GetTimeUtil.getTime();
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
    @NotEmpty(message = "标题不能为空！")
    private String title;
    /**
     * 预支原因
     */
    @NotEmpty(message = "原因描述不能为空！")
    private String description;
    /**
     * 审核结果
     */
    private Integer checkStatus = 0;
    /**
     * 归还状态
     */
    private Integer backStatus = 0;
    /**
     * 审核结果
     */
    private Integer resultStatus = 0;

}
