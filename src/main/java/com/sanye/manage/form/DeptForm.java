package com.sanye.manage.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-11 下午 4:20
 */
@Data
public class DeptForm {

    private Integer id;
    /**
     * 部门编号
     */
    @NotEmpty(message = "部门编号不能为空")
    private String deptNo;
    /**
     * 部门名称
     */
    @NotEmpty(message = "部门名称不能为空")
    private String deptName;
    /**
     * 部门主管
     */
    private Integer personnelId;

    /**
     * 部门主管姓名
     */
    private String personnelName;



}
