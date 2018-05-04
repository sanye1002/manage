package com.sanye.manage.utils;

import com.sanye.manage.dataobject.PersonnelInfo;
import com.sanye.manage.dataobject.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-15 下午 3:43
 */
@Slf4j
public class ShiroGetSession {

    public static UserInfo getUserInfo(){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        if (userInfo==null){
            throw new UnauthenticatedException();
        }
        log.info("用户信息={}", userInfo.getName());
        return userInfo;
    }


    public static void removeUser(){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.removeAttribute("user");
    }
    public static void setUser(UserInfo user){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("user",user);
    }


}
