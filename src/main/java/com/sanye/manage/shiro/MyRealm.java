package com.sanye.manage.shiro;

import com.sanye.manage.DTO.RoleDTO;
import com.sanye.manage.dataobject.Permission;
import com.sanye.manage.dataobject.UserInfo;
import com.sanye.manage.service.RolePermissionService;
import com.sanye.manage.service.UserService;
import com.sanye.manage.utils.ShiroGetSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-10 上午 9:59
 */
@Component
@Slf4j
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private ShiroGetSession shiroGetSession;
    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("Shiro权限验证执行");
        String username = (String) principalCollection.getPrimaryPrincipal();
        if(username!=null){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            UserInfo user = ShiroGetSession.getUserInfo();
            if(user!=null){
                if(user.getRoleId()!=null){
                    // TODO: 2018/4/14 权限
                    if(!shiroGetSession.permissionList().isEmpty()){
                        shiroGetSession.permissionList().forEach(v-> info.addStringPermission(v.getMethod()));
                    }
                }
                return info;
            }
        }
        throw new DisabledAccountException("用户信息异常，请重新登录！");
    }

    /**
     * 认证登录
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("认证登录......");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        UserInfo userInfo  = userService.findByPhone(token.getUsername());
        if(userInfo==null){
            throw new DisabledAccountException("用户不存在");
        }
        return new SimpleAuthenticationInfo(userInfo.getPhone(),userInfo.getPassword(),getName());
    }
    @PostConstruct
    public void initCredentialsMatcher() {
        //该句作用是重写shiro的密码验证，让shiro用我自己的验证
        setCredentialsMatcher(new CredentialsMatcher());
    }
}
