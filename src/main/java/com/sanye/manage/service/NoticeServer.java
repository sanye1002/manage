package com.sanye.manage.service;

import com.sanye.manage.DTO.NoticeUserDTO;
import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.dataobject.NoticeDetail;
import com.sanye.manage.dataobject.NoticeMessage;
import com.sanye.manage.dataobject.NoticeUserInfo;
import com.sanye.manage.dataobject.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-13 上午 11:13
 * @Description description
 */
public interface NoticeServer {
    /**
     *
     * @param noticeMessage
     * @return
     */
    NoticeMessage saveNoticeMessage(NoticeMessage noticeMessage);

    /**
     *
     * @param userInfoList
     * @param content
     */
    Map<String,Object> sendNoticeMessage(List<UserInfo> userInfoList, String content);

    /**
     *
     * @param id
     * @return
     */
    NoticeMessage findOneNoticeMessage(Integer id);

    /**
     * 如果查询人员为001部门 可以查询all
     * @param status
     * @return
     */
    Page<NoticeMessage> findAllNoticeMessageByStatus(Integer status, Pageable pageable);

    /**
     *
     * @param noticeUserInfo
     * @return
     */
    NoticeUserInfo saveNoticeUserInfo(NoticeUserInfo noticeUserInfo);

    /**
     *
     * @param userInfoList
     * @param content
     * @param title
     * @return
     */
    Map<String,Object> sendNoticeToUser(List<UserInfo> userInfoList, String content,String title);

    /**
     *
     * @param readStatus
     * @param pageable
     * @return
     */
    PageDTO<NoticeUserDTO> findAllNoticeUserDTOByUserIdAndReadStatus(Integer userId,Integer readStatus,Pageable pageable);

    /**
     *
     * @return
     */
    List<NoticeUserDTO> findAllNewNoticeUserDTO();

    /**
     *
     * @param id
     */
    void deleteNoticeUserInfo(Integer id);

    /**
     *
     * @param id
     * @param pageable
     * @return
     */
    Page<NoticeDetail> findAllByUserId(Integer id, Pageable pageable);

    /**
     *
     * @param id
     */
    void deleteNoticeDetail(Integer id);

    NoticeUserDTO findOneNoticeUserDTO(Integer id);

}
