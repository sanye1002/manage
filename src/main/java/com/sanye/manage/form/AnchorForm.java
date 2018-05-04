package com.sanye.manage.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-17 上午 11:53
 */
@Data
public class AnchorForm {
    private Integer id;

    private Integer userId;

    private Integer platformId;

    @NotEmpty(message = "直播id不能为空")
    private String liveId;
}
