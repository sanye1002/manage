package com.sanye.manage.DTO;

import com.sanye.manage.dataobject.NoticeDetail;
import lombok.Data;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-13 下午 5:34
 * @Description description
 */
@Data
public class NoticeUserDTO {

    private Integer id;

    /**
     * 详情
     */
    private NoticeDetail detail;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 阅读状态
     */
    private Integer readStatus;
    /**
     * 阅读时间
     */
    private String readTime;

}
