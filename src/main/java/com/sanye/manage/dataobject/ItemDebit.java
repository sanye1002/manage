package com.sanye.manage.dataobject;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-09 下午 5:17
 * 物品借记表
 */
@Data
@Entity
@Table(name = "item_debit")
public class ItemDebit {

    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 借记人员ID
     */
    private Integer userId;
    /**
     * 借记物品ID
     */
    private Integer itemId;

    private String itemName;

    private Integer sumAmount;
    /**
     * 借记时间
     */
    private String createTime;
    /**
     * 归还备注
     */
    @Column(length = 512)
    private String remark;
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

    private String resultRemark;
    /**
     * 归还时间
     */
    private String backTime;
    /**
     * 归还状态
     */
    private Integer backStatus;

}


