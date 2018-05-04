package com.sanye.manage.DTO;

import com.sanye.manage.dataobject.AnchorSalaryAdvance;
import com.sanye.manage.dataobject.CheckInfo;
import com.sanye.manage.dataobject.UserInfo;
import lombok.Data;

import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-25 下午 4:27
 * @Description description
 */
@Data
public class AnchorSalaryAdvanceDTO {
    private UserInfo userInfo;

    private List<CheckInfo> checkInfoList;

    private AnchorSalaryAdvance anchorSalaryAdvance;
}
