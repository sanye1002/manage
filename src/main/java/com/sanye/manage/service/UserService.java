package com.sanye.manage.service;

import com.sanye.manage.DTO.UserDTO;
import com.sanye.manage.dataobject.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-11 上午 10:20
 */
public interface UserService {

    Map<String,Object> login(HttpServletRequest request,String phone, String password);

    UserInfo findOne(Integer id);

    Map<String,Object> delete(Integer id);

    UserInfo save(UserInfo userInfo);

    Map<String, Object> findAllByPhone(String phone);

    UserInfo findByPhone(String phone);

    List<UserInfo> findAllByUserType(String userType);

    Page<UserInfo> findAllByUserTypeAndStatusAndShowStatus(Pageable pageable,String userType, Integer status);

    List<UserInfo> findAllForNoticeByUserType(String userType);
}
