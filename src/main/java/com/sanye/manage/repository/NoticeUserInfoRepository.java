package com.sanye.manage.repository;

import com.sanye.manage.dataobject.NoticeUserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-13 上午 11:24
 * @Description 通知用户
 */
public interface NoticeUserInfoRepository extends JpaRepository<NoticeUserInfo,Integer> {

    Page<NoticeUserInfo> findAllByUserIdAndReadStatus(Integer userId, Integer readStatus, Pageable pageable);

    List<NoticeUserInfo> findAllByUserIdAndReadStatus(Integer userId, Integer readStatus);

    List<NoticeUserInfo> findAllByDetailId(Integer detailId);

    void deleteByDetailId(Integer id);

}
