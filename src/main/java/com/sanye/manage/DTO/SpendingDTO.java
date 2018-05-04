package com.sanye.manage.DTO;

import com.sanye.manage.dataobject.CheckInfo;
import com.sanye.manage.dataobject.SpendingInfo;
import com.sanye.manage.dataobject.UserInfo;
import lombok.Data;

import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-23 上午 10:22
 * @Description description
 */
@Data
public class SpendingDTO {

    private SpendingInfo spendingInfo;

    private List<CheckInfo> checkInfoList;

    private UserInfo userInfo;
}
