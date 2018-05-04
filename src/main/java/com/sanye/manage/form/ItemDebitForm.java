package com.sanye.manage.form;

import com.sanye.manage.utils.GetTimeUtil;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-27 下午 6:28
 * @Description description
 */
@Data
public class ItemDebitForm {
    /**
     * 借记人员ID
     */
    private Integer userId;
    /**
     * 借记物品ID
     */
    private Integer itemId;


    private String itemName;

    private Integer number;
    /**
     * 借记时间
     */
    private String createTime = GetTimeUtil.getTime();
    /**
     * 归还备注
     */
    @NotEmpty(message = "描述不能为空！")
    private String remark;

    /**
     * 审核结果
     */
    private Integer checkStatus =0;
    /**
     * 审核结果
     */
    private Integer resultStatus =0;
    /**
     * 归还状态
     */
    private Integer backStatus = 0;

}
