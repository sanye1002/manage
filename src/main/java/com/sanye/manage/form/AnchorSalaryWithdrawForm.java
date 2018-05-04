package com.sanye.manage.form;

import com.sanye.manage.utils.GetTimeUtil;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-26 下午 7:25
 * @Description description
 */
@Data
public class AnchorSalaryWithdrawForm {

    /**
     * 创建时间
     */
    private String createTime= GetTimeUtil.getTime();

    private String month=GetTimeUtil.getMonth();
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
     * 审核结果
     */
    private Integer checkStatus = 0;

    private Integer resultStatus = 0;

}
