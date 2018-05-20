package com.sanye.manage.repository;

import com.sanye.manage.dataobject.NoticeDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-13 上午 11:15
 * @Description 通知详情
 */
public interface NoticeDetailRepository extends JpaRepository<NoticeDetail,Integer> {

    Page<NoticeDetail> findAllByUserId(Integer id, Pageable pageable);
}
