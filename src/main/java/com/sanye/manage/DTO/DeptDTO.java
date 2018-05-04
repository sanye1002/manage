package com.sanye.manage.DTO;

import com.sanye.manage.dataobject.DeptInfo;
import com.sanye.manage.dataobject.PersonnelInfo;
import lombok.Data;

import java.util.List;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-09 下午 7:30
 */
@Data
public class DeptDTO {

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
    private PersonnelInfo personnelInfo;
    /**
     * 下级部门
     */
    private List<DeptInfo> deptInfoList;

}

