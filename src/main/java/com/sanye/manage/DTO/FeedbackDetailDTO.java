package com.sanye.manage.DTO;

import com.sanye.manage.dataobject.UserInfo;
import lombok.Data;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-11 上午 11:08
 * @Description description
 */
@Data
public class FeedbackDetailDTO {

    private Integer id;

    private String content;
    //发送时间
    private String sendTime;
    //发送人
    private UserInfo sendUser;
    //处理人
    private UserInfo backUser;
    //处理时间
    private String backTime;
    //处理内容
    private String backContent;

}
