package com.sanye.manage.service.impl;

import com.sanye.manage.DTO.FeedbackDTO;
import com.sanye.manage.DTO.FeedbackDetailDTO;
import com.sanye.manage.dataobject.FeedbackDetail;
import com.sanye.manage.dataobject.FeedbackInfo;
import com.sanye.manage.dataobject.FeedbackType;
import com.sanye.manage.repository.FeedbackDebitRepository;
import com.sanye.manage.repository.FeedbackInfoRepository;
import com.sanye.manage.repository.FeedbackTypeRepository;
import com.sanye.manage.service.FeedbackService;
import com.sanye.manage.service.UserService;
import com.sanye.manage.utils.GetTimeUtil;
import com.sanye.manage.utils.SortTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-10 下午 9:43
 * @Description description
 */
@Service
@Transactional
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackInfoRepository infoRepository;
    @Autowired
    private FeedbackDebitRepository debitRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private FeedbackTypeRepository typeRepository;

    @Override
    public FeedbackInfo saveFeedbackInfo(FeedbackInfo feedbackInfo) {

        return infoRepository.save(feedbackInfo);
    }

    @Override
    public FeedbackDetail saveFeedbackDetail(FeedbackDetail feedbackDetail) {

        return debitRepository.save(feedbackDetail);
    }

    @Override
    public Page<FeedbackInfo> findAllFeedbackInfo(Pageable pageable) {

        return infoRepository.findAll(pageable);
    }

    @Override
    public Page<FeedbackInfo> findAllFeedbackInfoByBackStatus(Pageable pageable, Integer backStatus) {
        return infoRepository.findAllByBackStatus(pageable, backStatus);
    }

    @Override
    public FeedbackDTO finOneFeedbackDTOByFeedbackInfoId(Integer feedbackInfoId) {
        /*PageRequest pageRequest = new PageRequest(0, 4, SortTools.basicSort());
        Page<FeedbackDetail> detailPage1 = debitRepository.findAllByFeedbackInfoId(pageRequest, feedbackInfoId);
        PageRequest pageRequest1 = new PageRequest(detailPage1.getTotalPages()-1,4);
        Page<FeedbackDetail> detailPage = debitRepository.findAllByFeedbackInfoId(pageRequest1, feedbackInfoId);
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setFeedbackinfo(infoRepository.findOne(feedbackInfoId));
        feedbackDTO.setId(feedbackInfoId);
        List<FeedbackDetailDTO> detailDTOList = new ArrayList<>();
        if (!detailPage.getContent().isEmpty()) {
            detailPage.getContent().forEach(f -> {
                FeedbackDetailDTO feedbackDetailDTO = new FeedbackDetailDTO();
                BeanUtils.copyProperties(f, feedbackDetailDTO);
                feedbackDetailDTO.setSendUser(userService.findOne(f.getSendUserId()));
                feedbackDetailDTO.setBackUser(userService.findOne(f.getBackUserId()));
                detailDTOList.add(feedbackDetailDTO);
            });
        }*/

        List<FeedbackDetail> list = debitRepository.findAllByFeedbackInfoId(feedbackInfoId);
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setFeedbackinfo(infoRepository.findOne(feedbackInfoId));
        List<FeedbackDetailDTO> detailDTOList = new ArrayList<>();
        if (!list.isEmpty()) {
            list.forEach(f -> {
                FeedbackDetailDTO feedbackDetailDTO = new FeedbackDetailDTO();
                BeanUtils.copyProperties(f, feedbackDetailDTO);
                feedbackDetailDTO.setSendUser(userService.findOne(f.getSendUserId()));
                feedbackDetailDTO.setBackUser(userService.findOne(f.getBackUserId()));
                detailDTOList.add(feedbackDetailDTO);
            });
        }
        feedbackDTO.setDetailDTOList(detailDTOList);
        return feedbackDTO;
    }

    @Override
    public FeedbackType saveFeedbackType(FeedbackType feedbackType) {
        return typeRepository.save(feedbackType);
    }

    @Override
    public List<FeedbackType> finAllFeedbackType() {
        return typeRepository.findAll(SortTools.basicSort());
    }

    @Override
    public Page<FeedbackType> finAllFeedbackType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public FeedbackType findOneFeedbackType(Integer id) {
        return typeRepository.findOne(id);
    }

    @Override
    public void deleteFeedbackType(Integer id) {
        List<FeedbackInfo> feedbackInfoList = infoRepository.findAllByFeedbackTypeId(id);
        if (!feedbackInfoList.isEmpty()) {
            feedbackInfoList.forEach(l -> {
                List<FeedbackDetail> detailList = debitRepository.findAllByFeedbackInfoId(l.getId(), SortTools.basicSort());
                if (!detailList.isEmpty()) {
                    detailList.forEach(f -> {
                        debitRepository.delete(f);
                    });
                }
                infoRepository.delete(l);
            });
        }
        typeRepository.delete(id);
    }

    @Override
    public Map<String, Object> detailSend(Integer infoId, String content) {
        //info
        FeedbackInfo feedbackInfo = infoRepository.findOne(infoId);
        List<FeedbackDetail> feedbackDetailList = new ArrayList<>();
        feedbackDetailList = debitRepository.findAllByFeedbackInfoId(infoId, SortTools.basicSort());
        log.info("feedbackDetailList={}", feedbackDetailList.size());
        Map<String, Object> map = new HashMap<>();
        Integer flag = 0;

        if (feedbackDetailList.get(0).getBackContent() == null) {
            map.put("code", 100);
            map.put("message", "有消息未回复，不能发送新消息~");
            flag = 1;

        }
        if (flag == 0) {
            map.put("code", 0);
            map.put("message", "新消息发送成功，马上为你刷新~");
            FeedbackDetail feedbackDetail = new FeedbackDetail();
            feedbackDetail.setFeedbackInfoId(feedbackInfo.getId());
            feedbackDetail.setSendTime(GetTimeUtil.getDate());
            feedbackDetail.setContent(content);
            feedbackDetail.setBackUserId(feedbackInfo.getAccentUserId());
            feedbackDetail.setSendUserId(feedbackInfo.getSendUserId());
            debitRepository.save(feedbackDetail);
        }
        return map;


    }

    @Override
    public FeedbackInfo findOneFeedbackInfo(Integer id) {
        return infoRepository.findOne(id);
    }

    @Override
    public Page<FeedbackInfo> findAllFeedbackInfoByUserId(Integer userId, Integer backStatus, Pageable pageable) {
        if (userService.findOne(userId).getUserType().equals("personnel")) {
            return infoRepository.findAllByAccentUserIdAndBackStatus(userId, backStatus, pageable);
        } else {
            return infoRepository.findAllBySendUserIdAndBackStatus(userId, backStatus, pageable);
        }

    }

    @Override
    public void detailReply(Integer detailId, String backContent) {
        FeedbackDetail feedbackDetail = debitRepository.findOne(detailId);
        feedbackDetail.setBackTime(GetTimeUtil.getDate());
        feedbackDetail.setBackContent(backContent);
        debitRepository.save(feedbackDetail);
    }
}
