package com.sanye.manage.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-10 下午 12:21
 * @Description description
 */
@Data
@Entity
@Table(name = "case_master")
public class CaseMaster {
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 申请id
     */
    private Integer applicationId;
    /**
     * 回执id
     */
    private Integer backId;

    /**
     *
     */
    private String checkDeptNo;

    private Integer userId;
    /**
     * 时间
     */
    private String crateTime;

}
