package com.sanye.manage.dataobject;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-19 下午 6:40
 * @Description description
 */
@Entity
@Data
@Table(name = "check_info")
public class CheckInfo {

    @Id
    @GeneratedValue
    private Integer id;
    //类型
    private String type;
    //申请记录
    private Integer applyId;

    private String title;

    private BigDecimal salary;

    private String description;

    //申请时间
    private String applyTime;
    //申请人员
    private Integer applyPersonnelId;
    //申请人员
    private String applyPersonnelName;
    //接受人员
    private Integer acceptPersonnelId;
    //接受人员
    private String acceptPersonnelName;
    //接受人员部门
    private String acceptDeptNo;
    //审核顺序
    private Integer orderId;
    //审核时间
    private String checkTime;
    //审核人员
    private Integer checkPersonnelId;
    //审核人员
    private String checkPersonnelName;

    //审核结果
    private Integer resultStatus=0;
    //审核状态
    private Integer checkStatus=0;
    //原因
    private String remark;

}
