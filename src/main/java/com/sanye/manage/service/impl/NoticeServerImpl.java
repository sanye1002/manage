package com.sanye.manage.service.impl;

import com.sanye.manage.DTO.NoticeUserDTO;
import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.dataobject.*;
import com.sanye.manage.repository.*;
import com.sanye.manage.service.NoticeServer;
import com.sanye.manage.utils.GetTimeUtil;
import com.sanye.manage.utils.SendMessageUtil;
import com.sanye.manage.utils.ShiroGetSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
 * @create 2018-05-13 下午 2:01
 * @Description description
 */
@Service
@Transactional
public class NoticeServerImpl implements NoticeServer{
    @Autowired
    private NoticeUserInfoRepository noticeUserInfoRepository;
    @Autowired
    private NoticeDetailRepository noticeDetailRepository;
    @Autowired
    private NoticeMessageRepository noticeMessageRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PersonnelInfoRepository personnelInfoRepository;
    @Autowired
    private DeptInfoRepository deptInfoRepository;
    @Override
    public NoticeMessage saveNoticeMessage(NoticeMessage noticeMessage) {
        return noticeMessageRepository.save(noticeMessage);
    }

    @Override
    public Map<String,Object> sendNoticeMessage(List<UserInfo> userInfoList, String content) {
        Map<String,Object> map = new HashMap<>();
        if (content.length()<=30){
            if (!userInfoList.isEmpty()){
                Integer successNum =userInfoList.size();
                final Integer[] errorNum = {0};
                userInfoList.forEach(id->{
                    NoticeMessage noticeMessage = new NoticeMessage();
                    noticeMessage.setContent(content);
                    noticeMessage.setName(id.getName());
                    noticeMessage.setPhone(id.getPhone());
                    noticeMessage.setSendUserId(ShiroGetSession.getUserInfo().getId());
                    noticeMessage.setTime(GetTimeUtil.getDate());
                    noticeMessage.setUserId(id.getId());
                    synchronized (this){
                        if (SendMessageUtil.sendNoticeMessage(id.getPhone(),id.getName(),content)){
                            noticeMessage.setStatus(1);
                        }else {
                            errorNum[0] = errorNum[0] +1;
                            noticeMessage.setStatus(0);
                        }
                    }
                    noticeMessageRepository.save(noticeMessage);
                });
                map.put("code",0);
                map.put("message","提交发送短信总数："+successNum+";成功发送："+(successNum-errorNum[0])+";失败条数："+errorNum[0]);
            }else {
                map.put("code",100);
                map.put("message","数据为空");
            }
        }else {
            map.put("code",100);
            map.put("message","内容多余30个字");
        }
        return map;
    }
    @Override
    public NoticeMessage findOneNoticeMessage(Integer id) {
        return noticeMessageRepository.findOne(id);
    }

    @Override
    public Page<NoticeMessage> findAllNoticeMessageByStatus(Integer status, Pageable pageable) {
        PersonnelInfo personnelInfo = personnelInfoRepository.findByUserId(ShiroGetSession.getUserInfo().getId());
        if (personnelInfo.getDeptNo().equals("001")){
            return noticeMessageRepository.findAllByStatus(status, pageable);
        }else {
            return noticeMessageRepository.findAllByStatusAndSendUserId(status,personnelInfo.getUserId(),pageable);
        }
    }

    @Override
    public NoticeUserInfo saveNoticeUserInfo(NoticeUserInfo noticeUserInfo) {
        return noticeUserInfoRepository.save(noticeUserInfo);
    }

    @Override
    public Map<String, Object> sendNoticeToUser(List<UserInfo> userInfoList, String content,String title) {
        UserInfo userInfo = ShiroGetSession.getUserInfo();
        NoticeDetail noticeDetail = new NoticeDetail();
        noticeDetail.setContent(content);
        noticeDetail.setCreateTime(GetTimeUtil.getDate());
        noticeDetail.setTitle(title);
        noticeDetail.setUserId(userInfo.getId());
        DeptInfo deptInfo = deptInfoRepository.findByDeptNo(personnelInfoRepository.findByUserId(userInfo.getId()).getDeptNo());
        if (deptInfo==null){
            noticeDetail.setUserName("系统通知");
        }else {
            noticeDetail.setUserName(deptInfo.getDeptName());
        }

        NoticeDetail result = noticeDetailRepository.save(noticeDetail);
        Map<String, Object> map = new HashMap<>();
        if (!userInfoList.isEmpty()){
            userInfoList.forEach( u->{
                NoticeUserInfo noticeUserInfo = new NoticeUserInfo();
                noticeUserInfo.setDetailId(result.getId());
                noticeUserInfo.setReadStatus(0);
                noticeUserInfo.setUserId(u.getId());
                noticeUserInfoRepository.save(noticeUserInfo);
            });
            map.put("code",0);
            map.put("message","成功发送通知【"+userInfoList.size()+"】条。");
            return map;
        }else {
            map.put("code",100);
            map.put("message","用户为空！");
            return map;
        }
    }

    @Override
    public PageDTO<NoticeUserDTO> findAllNoticeUserDTOByUserIdAndReadStatus(Integer userId, Integer readStatus, Pageable pageable) {
        PageDTO<NoticeUserDTO>  pageDTO= new PageDTO<>();
        Page<NoticeUserInfo> noticeUserInfoPage = noticeUserInfoRepository.findAllByUserIdAndReadStatus(userId,readStatus,pageable);
        pageDTO.setTotalPages(noticeUserInfoPage.getTotalPages());
        List<NoticeUserDTO> list = new ArrayList<>();
        if (!noticeUserInfoPage.getContent().isEmpty()){
            noticeUserInfoPage.getContent().forEach(l->{
                NoticeUserDTO noticeUserDTO = new NoticeUserDTO();
                noticeUserDTO.setId(l.getId());
                BeanUtils.copyProperties(l,noticeUserDTO);
                noticeUserDTO.setDetail(noticeDetailRepository.findOne(l.getDetailId()));
                list.add(noticeUserDTO);
            });
        }
        pageDTO.setPageContent(list);
        return pageDTO;
    }


    @Override
    public List<NoticeUserDTO> findAllNewNoticeUserDTO() {
        List<NoticeUserInfo> noticeUserInfoList = noticeUserInfoRepository.findAllByUserIdAndReadStatus(ShiroGetSession.getUserInfo().getId(),0);
        List<NoticeUserDTO> list = new ArrayList<>();
        if (!noticeUserInfoList.isEmpty()){
            noticeUserInfoList.forEach(l->{
                NoticeUserDTO noticeUserDTO = new NoticeUserDTO();
                BeanUtils.copyProperties(l,noticeUserDTO);
                noticeUserDTO.setDetail(noticeDetailRepository.findOne(l.getDetailId()));
                list.add(noticeUserDTO);
            });
        }
        return list;
    }

    @Override
    public void deleteNoticeUserInfo(Integer id) {
        noticeUserInfoRepository.delete(id);
    }

    @Override
    public Page<NoticeDetail> findAllByUserId(Integer id, Pageable pageable) {
        return noticeDetailRepository.findAllByUserId(id,pageable);
    }

    @Override
    public void deleteNoticeDetail(Integer id) {
        noticeDetailRepository.findOne(id);
        noticeUserInfoRepository.deleteByDetailId(id);
    }

    @Override
    public NoticeUserDTO findOneNoticeUserDTO(Integer id) {
        NoticeUserDTO noticeUserDTO = new NoticeUserDTO();
        NoticeUserInfo noticeUserInfo = noticeUserInfoRepository.findOne(id);

        if (noticeUserInfo!=null){
            BeanUtils.copyProperties(noticeUserInfo,noticeUserDTO);
            noticeUserDTO.setDetail(noticeDetailRepository.findOne(noticeUserInfo.getDetailId()));
            if (noticeUserInfo.getReadStatus()==0){
                noticeUserInfo.setReadStatus(1);
                noticeUserInfo.setReadTime(GetTimeUtil.getDate());
                noticeUserInfoRepository.save(noticeUserInfo);
            }
        }
        return noticeUserDTO;
    }


}
