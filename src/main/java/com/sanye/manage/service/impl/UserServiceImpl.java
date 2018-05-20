package com.sanye.manage.service.impl;

import com.sanye.manage.dataobject.AnchorInfo;
import com.sanye.manage.dataobject.UserInfo;
import com.sanye.manage.repository.AnchorInfoRepository;
import com.sanye.manage.repository.PersonnelInfoRepository;
import com.sanye.manage.repository.UserInfoRepository;
import com.sanye.manage.service.AnchorService;
import com.sanye.manage.service.PersonnelInfoService;
import com.sanye.manage.service.UserService;
import com.sanye.manage.utils.Encrypt;
import com.sanye.manage.utils.ShiroGetSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-11 上午 10:24
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PersonnelInfoRepository personnelInfoRepository;

    @Autowired
    private PersonnelInfoService personnelInfoService;
    @Autowired
    private AnchorService anchorService;
    @Autowired
    private AnchorInfoRepository anchorInfoRepository;

    // TODO: 2018/4/12 0012  主播
    // TODO: 2018/4/12 0012  权限
    @Override
    public Map<String, Object> login(HttpServletRequest request, String phone, String password) {
        Map<String, Object> map = new HashMap<>();

        UserInfo userInfo = userInfoRepository.findByPhone(phone);
        if (userInfo == null) {
            map.put("code", 100);
            map.put("message", "用户名不存在");
            return map;
        }
        UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            subject.getSession().setTimeout(-10001); //session永不超时
            if (subject.isAuthenticated()) {
                Session session = subject.getSession();
                UserInfo user = userInfoRepository.findByPhoneAndPassword(phone, Encrypt.md5(password));
                session.setAttribute("user", user);
                UserInfo login = (UserInfo) session.getAttribute("user");
                log.info("登录成功 用户信息={}", login.getName());
                map.put("code", 0);
                map.put("message", "登录成功！使用愉快！");
                request.getSession().setAttribute("user", login);
                return map;
            }
            map.put("code", 100);
            map.put("message", "登录失败!");
            return map;

        } catch (DisabledAccountException e) {
            map.put("code", 100);
            map.put("message", e.getMessage());
            return map;
        } catch (Exception e) {
            map.put("code", 100);
            map.put("message", "登录失败!");
            return map;
        }

    }

    @Override
    public UserInfo findOne(Integer id) {

        return userInfoRepository.findOne(id);
    }

    @Override
    public Map<String, Object> delete(Integer id) {
        Map<String, Object> map = new HashMap<>();
        UserInfo userInfo = userInfoRepository.findOne(id);
        if ("personnel".equals(userInfo.getUserType())) {
            personnelInfoService.delete(personnelInfoRepository.findByUserId(userInfo.getId()).getId());
        }
        if ("anchor".equals(userInfo.getUserType())) {
            List<AnchorInfo> list = anchorInfoRepository.findAllByUserId(id);
            for (int i = 0; i < list.size(); i++) {
                anchorService.delete(list.get(i).getId());
            }

        }
        map.put("code", 0);
        map.put("message", "删除成功！");

        return map;
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    @Override
    public Map<String, Object> findAllByPhone(String phone) {
        List<UserInfo> userInfoList = userInfoRepository.findAllByPhone(phone);
        Map<String, Object> map = new HashMap<>();
        if (userInfoList.isEmpty()) {
            map.put("code", 0);
            map.put("message", "手机号码可以使用！");
            return map;
        }
        map.put("code", 100);
        map.put("message", "手机号码已存在！");
        return map;
    }

    @Override
    public UserInfo findByPhone(String phone) {
        return userInfoRepository.findByPhone(phone);
    }

    @Override
    public List<UserInfo> findAllByUserType(String userType) {
        return userInfoRepository.findAllByUserType(userType);
    }

    @Override
    public Page<UserInfo> findAllByUserTypeAndStatusAndShowStatus(Pageable pageable, String userType, Integer status) {
        return userInfoRepository.findAllByUserTypeAndStatusAndShowStatus(pageable, userType, status, 1);
    }

    @Override
    public List<UserInfo> findAllForNoticeByUserType(String userType) {
        if (userType.equals("all")){
            if (personnelInfoRepository.findByUserId(ShiroGetSession.getUserInfo().getId()).getDeptNo().equals("001")){
                return userInfoRepository.findAllByStatusAndShowStatus(1, 1);
            }else {
                return null;
            }
        }
        return userInfoRepository.findAllByStatusAndShowStatusAndUserType(1, 1,userType);
    }
}
