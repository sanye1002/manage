package com.sanye.manage.DTO;

import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * Created by 超级战机
 * 2018-04-14 15:37
 */
@Data
public class AccountDTO {
    @NotEmpty(message = "手机号码不能为空！")
    private String phone;
    @NotEmpty(message = "密码不能为空！")
    private String password;

}
