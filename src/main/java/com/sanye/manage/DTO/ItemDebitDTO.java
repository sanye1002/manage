package com.sanye.manage.DTO;

import com.sanye.manage.dataobject.CheckInfo;
import com.sanye.manage.dataobject.ItemDebit;
import com.sanye.manage.dataobject.UserInfo;
import lombok.Data;

import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-27 下午 4:57
 * @Description description
 */
@Data
public class ItemDebitDTO {
    private UserInfo userInfo;
    private ItemDebit itemDebit;
    private List<CheckInfo> checkInfoList;
}
