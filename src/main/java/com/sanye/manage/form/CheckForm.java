package com.sanye.manage.form;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-20 上午 11:46
 * @Description description
 */
@Data
public class CheckForm {

    private Integer id;
    //审核时间
    private String checkTime;
    //sq时间
    private String applyTime;
    //申请人员
    private Integer applyPersonnelId;
    //申请人员
    private String applyPersonnelName;
    //审核人员
    private Integer checkPersonnelId;
    //审核人员
    private String checkPersonnelName;
    //接受人员
    private Integer acceptPersonnelId;
    //接受人员
    private String acceptPersonnelName;
    //审核结果
    private Integer resultStatus=0;
    private Integer platformId;
    //原因
    private String remark;

    private Integer applyId;

    private String type;

    private String title;

    private BigDecimal salary;

    private String description;


}
