package com.sanye.manage.DTO;

import com.sanye.manage.dataobject.AnchorSalaryWithdraw;
import com.sanye.manage.dataobject.CheckInfo;
import com.sanye.manage.dataobject.UserInfo;
import lombok.Data;

import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-26 下午 7:42
 * @Description description
 */
@Data
public class AnchorSalaryWithdrawDTO {

    private UserInfo userInfo;

    private List<CheckInfo> checkInfoList;

    private AnchorSalaryWithdraw anchorSalaryWithdraw;
}
