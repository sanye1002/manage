package com.sanye.manage.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-09 下午 3:59
 * 直播平台
 */
@Data
@Entity
@Table(name = "platform_info")
public class PlatformInfo {

    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 平台名称
     */
    private String name;

    /**
     * 平台直播url
     */
    private String liveUrl;

    private Integer userId;

    private String username;

}
