package com.sanye.manage.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;


/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-13 下午 4:30
 * @Description description
 */
@Data
public class SendMessageForm {

    @Length(max = 6,message = "内容不能超过6个字！")
    private String content;

    private Integer aid;

    private String value;

    private Integer uid;

}
