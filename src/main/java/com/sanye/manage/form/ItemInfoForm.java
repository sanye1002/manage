package com.sanye.manage.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-27 下午 12:02
 * @Description description
 */
@Data
public class ItemInfoForm {

    private Integer id;
    /**
     * 编号
     */
    @NotEmpty(message = "编号不能为空！")
    private String number;
    /**
     * 名称
     */
    @NotEmpty(message = "名称不能为空！")
    private String name;
    /**
     * 描述信息，如颜色，成色
     */
    @NotEmpty(message = "描述信息不能为空！")
    private String description;

    @NotEmpty(message = "颜色！")
    private String color;

    /**
     * 总计数量
     */
    private Integer sumAmount;


    private Integer userId;

    private String userName;

}
