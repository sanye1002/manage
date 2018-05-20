package com.sanye.manage.utils;

import com.sanye.manage.DTO.RoleDTO;
import com.sanye.manage.dataobject.Permission;
import com.sanye.manage.dataobject.PersonnelInfo;
import com.sanye.manage.dataobject.UserInfo;
import com.sanye.manage.service.RolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-15 下午 3:43
 */
@Slf4j
@Component
public class ShiroGetSession {

    @Autowired
    private  RolePermissionService rolePermissionService;
    public static UserInfo getUserInfo(){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        if (userInfo==null){
            session.removeAttribute("permissionList");
            throw new UnauthenticatedException();
        }
        log.info("用户信息={}", userInfo.getName());
        return userInfo;
    }


    public static void removeUser(){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.removeAttribute("user");
        session.removeAttribute("permissionList");
    }
    public static void setUser(UserInfo user){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("user",user);
    }
    public  List<Permission> permissionList(){
        List<Permission> permissionList = new ArrayList<>();


        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        permissionList = (List<Permission>) session.getAttribute("permissionList");
        if (permissionList==null){
            RoleDTO roleDTO = rolePermissionService.findOne(ShiroGetSession.getUserInfo().getRoleId());
            permissionList = roleDTO.getPermissionList();
            session.setAttribute("permissionList",permissionList);
        }
        return permissionList;
    }


}
