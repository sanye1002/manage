package com.sanye.manage.repository;

import com.sanye.manage.dataobject.FeedbackDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-10 下午 9:41
 * @Description description
 */
public interface FeedbackDebitRepository extends JpaRepository<FeedbackDetail,Integer> {

    List<FeedbackDetail> findAllByFeedbackInfoId(Integer feedbackInfoId, Sort sort);

    List<FeedbackDetail> findAllByFeedbackInfoId(Integer feedbackInfoId);

    Page<FeedbackDetail> findAllByFeedbackInfoId(Pageable pageable,Integer feedbackInfoId);
}
