package com.sanye.manage.DTO;

import lombok.Data;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-10 下午 5:29
 * @Description description
 */
@Data
public class CheckDTO {

    private Integer id;
    //类型
    private String type;
    //申请记录
    private Integer applyId;

    private String title;

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

    private CaseMasterDTO caseMasterDTO;

}
