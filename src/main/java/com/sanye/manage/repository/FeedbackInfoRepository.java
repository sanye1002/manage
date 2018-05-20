package com.sanye.manage.repository;

import com.sanye.manage.dataobject.FeedbackInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-10 下午 9:36
 * @Description description
 */
public interface FeedbackInfoRepository extends JpaRepository<FeedbackInfo,Integer>{
    Page<FeedbackInfo> findAllByBackStatus(Pageable pageable,Integer backStatus);

    List<FeedbackInfo> findAllByFeedbackTypeId(Integer feedbackTypeId);

    Page<FeedbackInfo> findAllBySendUserIdAndBackStatus(Integer sendUserId,Integer backStatus,Pageable pageable);

    Page<FeedbackInfo> findAllByAccentUserIdAndBackStatus(Integer accentUserId,Integer backStatus,Pageable pageable);
}
