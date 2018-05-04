package com.sanye.manage.dataobject;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-12 下午 12:54
 */
@Data
@Entity
@Table(name = "user_info")
public class UserInfo {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String nikeName;

    private String phone;

    /**
     * 头像
     */
    @Column(length = 512)
    private String avatar;

    private Integer sex=1;

    private Integer age=0;

    private String password;

    /**
     * 状态  1 0
     */
    private Integer status =0;
    /**
     * 角色Id
     */
    private Integer roleId;
    /**
     * 伪删除
     */
    private Integer showStatus;

    private String QQ;

    private String idCardPositive; //身份证正面

    private String idCardSide; //身份证反面

    private String idCardHold; //身份证手持照

    private String createDate; //创建时间

    private String updateDate; //修改时间

    private String userType;

    private String BankCardNumber;//银行卡号

    private String bankType;//开户银行

    private String bankUserName;//开户姓名

    private String AliPay;//支付宝
}
