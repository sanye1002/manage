package com.sanye.manage.utils;

import com.sanye.manage.dataobject.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-03 下午 6:35
 * @Description description
 */
@Slf4j
public class PayCheckUtil {
    public static Boolean check(){
        UserInfo userInfo = ShiroGetSession.getUserInfo();
        if (userInfo.getBankUserName()==null||userInfo.getBankType()==null||userInfo.getBankCardNumber()==null||userInfo.getAliPay()==null){
            log.info("check={false}");
            return false;
        }
        return true;
    }
}
