package com.sanye.manage.DTO;

import com.sanye.manage.dataobject.ApplicationCase;
import com.sanye.manage.dataobject.BackCase;
import com.sanye.manage.dataobject.UserInfo;
import lombok.Data;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-10 下午 1:01
 * @Description description
 */
@Data
public class CaseMasterDTO {

    private Integer id;
    /**
     * 申请id
     */
    private ApplicationCase applicationCase;
    /**
     * 回执id
     */
    private BackCase backCase;

    private UserInfo userInfo;
    /**
     * 时间
     */
    private String crateTime;
}
