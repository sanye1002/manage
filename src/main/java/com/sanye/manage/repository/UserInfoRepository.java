package com.sanye.manage.repository;

import com.sanye.manage.dataobject.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-12 下午 3:52
 */
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer>{

    UserInfo findByPhoneAndPassword(String phone,String password);

    List<UserInfo> findAllByPhone(String phone);

    UserInfo findByPhone(String phone);

    List<UserInfo> findAllByUserType(String userType);

    Page<UserInfo> findAllByUserTypeAndStatusAndShowStatus(Pageable pageable, String userType, Integer status, Integer showStatus);
    

}
