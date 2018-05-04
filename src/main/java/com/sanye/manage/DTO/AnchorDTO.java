package com.sanye.manage.DTO;

import com.sanye.manage.dataobject.AnchorInfo;
import com.sanye.manage.dataobject.PlatformInfo;
import com.sanye.manage.dataobject.UserInfo;
import lombok.Data;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-13 下午 12:00
 */
@Data
public class AnchorDTO {
    private Integer id;
    /**
     * 昵称
     */
    private UserInfo userInfo;
    private PlatformInfo platformInfo;
    private AnchorInfo anchorInfo;
    private String createDate; //创建时间
    private String updateDate; //修改时间
}
