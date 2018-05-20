package com.sanye.manage.repository;

import com.sanye.manage.dataobject.NoticeMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-13 上午 11:07
 * @Description description
 */
public interface NoticeMessageRepository extends JpaRepository<NoticeMessage,Integer> {

    Page<NoticeMessage> findAllByStatus(Integer status, Pageable pageable);

    Page<NoticeMessage> findAllByStatusAndSendUserId(Integer status, Integer userId,Pageable pageable);



}
