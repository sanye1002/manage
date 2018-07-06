package com.sanye.manage.dataobject;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-08 下午 3:26
 * @Description 事件申请
 */
@Data
@Entity
@Table(name = "application_case")
public class ApplicationCase {
    @Id
    @GeneratedValue
    private Integer id;

    private String month;
    /**
     * 创建时间
     */
    private String createTime;

    private Integer userId;

    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    @Column(columnDefinition = "TEXT")
    private String content;
    /**
     * 外出
     */
    private Integer goOut=0;
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
