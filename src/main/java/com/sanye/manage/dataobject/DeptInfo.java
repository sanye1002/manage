package com.sanye.manage.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-09 下午 5:38
 * 部门表
 */
@Entity
@Data
@Table(name = "dept_info")
public class DeptInfo {
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 部门编号
     */
    private String deptNo;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 部门主管
     */
    private Integer personnelId;
    /**
     * 部门姓名
     */
    private String personnelName;
    /**
     * 下级部门编号
     */
    private String nextNo;

}
