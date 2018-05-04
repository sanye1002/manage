package com.sanye.manage.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-10 下午 6:06
 */
@Data
public class AnchorUserForm {

    private Integer id;

    @NotEmpty(message = "姓名不能必填")
    private String name;
    @NotEmpty(message = "手机号码必填，以便登录")
    private String phone;
    @NotEmpty(message = "昵称不能必填")
    private String nikeName;

    private Integer sex;//1男 0女

    private Integer age;
    @NotEmpty(message = "密码必须填！")
    private String password;

    private Integer status;//状态  1 0
    /**
     * 角色Id
     */
    private Integer roleId;
    /**
     * 显示
     */
    private Integer showStatus =1;

    private String idCardPositive; //身份证正面

    private String idCardSide; //身份证反面

    private String idCardHold; //身份证手持照
}
