package com.sanye.manage.dataobject;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-09 下午 3:26
 */
@Data
@Entity
@Table(name = "anchor_info")
public class AnchorInfo {

    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 昵称
     */
    private String name;

    private String nikeName;

    private String phone;
    /**
     * 直播ID
     */
    private String liveId;
    /**
     * 直播平台
     */
    private String livePlatform;

    /**
     * 直播平台Id
     */
    private Integer platformId;

    /**
     * 性别
     */
    private Integer sex = 1;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 伪删除
     */
    private Integer showStatus;

    private Integer status = 1;//状态  1 0
    private String createDate; //创建时间

    private String updateDate; //修改时间
}
