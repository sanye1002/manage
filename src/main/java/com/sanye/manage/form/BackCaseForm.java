package com.sanye.manage.form;

import com.sanye.manage.dataobject.UserInfo;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-10 下午 12:46
 * @Description description
 */
@Data
public class BackCaseForm {

    private Integer id;
    private Integer caseId;
    private String month;
    /**
     * 创建时间
     */
    private String createTime;

    private Integer userId;
    /**
     * 标题
     */
    @NotNull(message = "标题不能为空")
    private String title;
    /**
     * 内容
     */
    @NotNull(message = "内容不能为空")
    private String content;
    /**
     * 姓名
     */
    private String username;
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
    private Integer checkStatus=0;

    private Integer resultStatus=0;

    private String resultRemark;
}
