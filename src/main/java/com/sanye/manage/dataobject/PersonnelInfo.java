package com.sanye.manage.dataobject;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-09 下午 1:27
 */
@Entity
@Data
@Table(name = "personnel_info")
public class PersonnelInfo {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer userId;

    private String name;

    private String phone;

    private Integer sex=1;//1男 0女

    private Integer roleId=0;

    private Integer age = 0;

    private String password;

    private Integer status = 1;//状态  1 0
    /**
     * 伪删除
     */
    private Integer showStatus ;

    /**
     * 部门编号
     */
    private String deptNo;

    private String joinTime;//入职时间

    private String createDate; //创建时间

    private String updateDate; //修改时间


}
