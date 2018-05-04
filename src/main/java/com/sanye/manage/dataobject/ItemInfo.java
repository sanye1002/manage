package com.sanye.manage.dataobject;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-09 下午 5:21
 * 物品表
 */
@Data
@Entity
@Table(name = "item_info")
public class ItemInfo {

    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 编号
     */
    private String number;
    /**
     * 名称
     */
    private String name;

    private String color;
    /**
     * 描述信息，如颜色，成色
     */
    @Column(length = 512)
    private String description;

    /**
     * 剩余数量
     */
    private Integer beforeAmount;
    /**
     * 总计数量
     */
    private Integer sumAmount=0;


    @Column(length = 512)
    private String img;

    private String createTime;

    private Integer userId;

    private String userName;

    private String updateTime;



}
