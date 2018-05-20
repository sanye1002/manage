package com.sanye.manage.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-13 下午 4:30
 * @Description description
 */
@Data
public class SendNoticeForm {

    @NotNull(message = "内容不能为空")
    private String content;

    private Integer aid;

    private String value;

    private Integer uid;
    @NotNull(message = "标题不能为空")
    private String title;


}
