package com.sanye.manage.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-11 上午 11:44
 * @Description description
 */
@Data
public class FeedbackTypeForm {

    private Integer id;

    private Integer acceptPersonnelId;
    @NotEmpty(message = "必须选择处理人！")
    private String acceptPersonnelName;
    @NotEmpty(message = "必须输入类型！")
    private String typeName;
}
