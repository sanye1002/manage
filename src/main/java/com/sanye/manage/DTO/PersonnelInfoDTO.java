package com.sanye.manage.DTO;

import com.sanye.manage.dataobject.PersonnelInfo;
import com.sanye.manage.dataobject.Role;
import com.sanye.manage.dataobject.UserInfo;
import lombok.Data;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-09 下午 7:28
 */
@Data
public class PersonnelInfoDTO {

    private Integer id;
    private UserInfo userInfo;
    private PersonnelInfo personnelInfo;
    private Role role;
    private DeptDTO deptDTO;
    private String createDate; //创建时间
    private String updateDate; //修改时间
}
