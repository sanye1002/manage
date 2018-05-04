package com.sanye.manage.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-13 上午 10:06
 */
@Data
public class PlatformForm {

    private Integer id;

    /**
     * 平台名称
     */
    @NotEmpty(message = "平台名称不能为空！")
    private String name;

    /**
     * 平台直播url
     */
    @NotEmpty(message = "直播地址不能为空！")
    private String liveUrl;

    private Integer userId;

    private String username;
}
