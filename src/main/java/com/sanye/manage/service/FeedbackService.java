package com.sanye.manage.service;

import com.sanye.manage.DTO.FeedbackDTO;
import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.dataobject.FeedbackDetail;
import com.sanye.manage.dataobject.FeedbackInfo;
import com.sanye.manage.dataobject.FeedbackType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-10 下午 9:43
 * @Description description
 */
public interface FeedbackService {
    /**
     * 保存info
     * @param feedbackInfo
     * @return
     */
    FeedbackInfo saveFeedbackInfo(FeedbackInfo feedbackInfo);

    /**
     * 保存detail
     * @param feedbackDetail
     * @return
     */
    FeedbackDetail saveFeedbackDetail(FeedbackDetail feedbackDetail);


    /**
     *
     * @param pageable
     * @return
     */
    Page<FeedbackInfo> findAllFeedbackInfo(Pageable pageable);

    /**
     *
     * @param pageable
     * @param backStatus
     * @return
     */
    Page<FeedbackInfo> findAllFeedbackInfoByBackStatus(Pageable pageable,Integer backStatus);


    FeedbackDTO finOneFeedbackDTOByFeedbackInfoId(Integer feedbackInfoId);
    /**
     *
     * @param feedbackType
     * @return
     */
    FeedbackType saveFeedbackType(FeedbackType feedbackType);

    /**
     *
     * @return
     */
    List<FeedbackType> finAllFeedbackType();

    /**
     *
     * @param pageable
     * @return
     */
    Page<FeedbackType> finAllFeedbackType(Pageable pageable);

    /**
     *
     * @param id
     * @return
     */
    FeedbackType findOneFeedbackType(Integer id);

    /**
     *
     * @param id
     */
    void deleteFeedbackType(Integer id);

    /**
     *
     * @param infoId
     * @param content
     * @return
     */
    Map<String,Object> detailSend(Integer infoId, String content);

    /**
     *
     * @param id
     * @return
     */
    FeedbackInfo findOneFeedbackInfo(Integer id);

    /**
     *
     * @param userId
     * @param backStatus
     * @param pageable
     * @return
     */
    Page<FeedbackInfo> findAllFeedbackInfoByUserId(Integer userId,Integer backStatus,Pageable pageable);

    /**
     *
     * @param detailId
     * @param backContent
     */
    void detailReply (Integer detailId, String backContent);
}
