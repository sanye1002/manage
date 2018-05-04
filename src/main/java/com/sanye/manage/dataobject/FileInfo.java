package com.sanye.manage.dataobject;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-09 下午 5:38
 * 部门表
 */
@Entity
@Data
@Table(name = "file_info")
public class FileInfo {
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 地址
     */
    @Column(length = 512)
    private String path;

    private String month;

    private Integer platformId;




}
