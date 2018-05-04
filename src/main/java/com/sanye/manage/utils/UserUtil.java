package com.sanye.manage.utils;

import com.sanye.manage.dataobject.UserInfo;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-02 下午 2:53
 * @Description description
 */
public class UserUtil {
    /**
     *  信息判断
     * @param userInfo
     * @return
     */
    private static Boolean checkUser(UserInfo userInfo){
        if (!MobileExactUtil.isMobileExact(userInfo.getPhone())){
            return false;
        }
        if (userInfo.getNikeName()==null){
            return false;
        }
        if (userInfo.getAliPay()==null){
            return false;
        }
        if (userInfo.getBankCardNumber()==null){
            return false;
        }
        if (userInfo.getBankType()==null){
            return false;
        }

        if (userInfo.getBankUserName()==null){
            return false;
        }

        return true;
    }
}
