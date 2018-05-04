package com.sanye.manage.DTO;


import lombok.Data;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-11 上午 10:21
 */
@Data
public class UserDTO {

    private Integer id;
    private String name;

    private String phone;

    private String avatar;//头像

    private Integer sex;//1男 0女

    private Integer age;

    private String password;

    private Integer status;//状态  1 0

    /**
     * 角色Id
     */
    private RoleDTO roleDTO;

    private String userType;
}
