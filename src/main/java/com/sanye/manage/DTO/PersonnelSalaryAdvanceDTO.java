package com.sanye.manage.DTO;

import com.sanye.manage.dataobject.CheckInfo;
import com.sanye.manage.dataobject.PersonnelSalaryAdvance;
import com.sanye.manage.dataobject.UserInfo;
import lombok.Data;

import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-24 下午 3:15
 * @Description description
 */
@Data
public class PersonnelSalaryAdvanceDTO {

    private PersonnelSalaryAdvance personnelSalaryAdvance;

    private UserInfo userInfo;

    private List<CheckInfo> checkInfoList;
}
