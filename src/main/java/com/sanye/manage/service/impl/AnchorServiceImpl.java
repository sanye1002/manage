package com.sanye.manage.service.impl;

import com.sanye.manage.DTO.AnchorDTO;
import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.dataobject.AnchorInfo;
import com.sanye.manage.dataobject.PlatformInfo;
import com.sanye.manage.dataobject.UserInfo;
import com.sanye.manage.repository.AnchorInfoRepository;
import com.sanye.manage.repository.PlatformInfoRepository;
import com.sanye.manage.repository.UserInfoRepository;
import com.sanye.manage.service.AnchorService;
import lombok.extern.slf4j.Slf4j;
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
import java.util.stream.Collectors;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-13 下午 12:03
 */
@Service
@Transactional
public class AnchorServiceImpl implements AnchorService {

    @Autowired
    private AnchorInfoRepository anchorInfoRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PlatformInfoRepository platformInfoRepository;

    @Override
    public AnchorInfo save(AnchorInfo anchorInfo) {
        return anchorInfoRepository.save(anchorInfo);
    }

    @Override
    public Map<String, Object> delete(Integer id) {
        AnchorInfo anchorInfo = anchorInfoRepository.findOne(id);
        anchorInfo.setShowStatus(0);
        anchorInfoRepository.save(anchorInfo);
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("message", "删除成功！");
        return map;
    }

    @Override
    public PageDTO<AnchorDTO> findAllByPlatformIdAndStatusAndShowStatus(Pageable pageable, Integer platform, Integer status) {
        PageDTO<AnchorDTO> pageDTO = new PageDTO<>();
        /**1 查出所有**/
        Page<AnchorInfo> anchorInfoPage = null;
        if (platform == 0) {

            anchorInfoPage = anchorInfoRepository.findAllByStatusAndShowStatus(pageable, status, 1);
        } else {
            anchorInfoPage = anchorInfoRepository.findAllByPlatformIdAndStatusAndShowStatus(pageable, platform, status, 1);
        }
        /**2 设置dto属性**/
        pageDTO.setTotalPages(anchorInfoPage.getTotalPages());

        /**3 对象转换**/
        List<AnchorDTO> anchorDTOList = new ArrayList<>();

        if (!anchorInfoPage.getContent().isEmpty()) {
            anchorDTOList = anchorInfoPage.getContent().stream().map(
                    e -> findOne2DTO(e.getId())
            ).collect(Collectors.toList());
        }
        pageDTO.setPageContent(anchorDTOList);
        return pageDTO;
    }

    @Override
    public AnchorInfo findOne(Integer id) {
        return anchorInfoRepository.findOne(id);
    }

    @Override
    public AnchorDTO findOne2DTO(Integer id) {
        AnchorInfo anchorInfo = anchorInfoRepository.findOne(id);
        UserInfo userInfo = userInfoRepository.findOne(anchorInfo.getUserId());
        PlatformInfo platformInfo = platformInfoRepository.findOne(anchorInfo.getPlatformId());
        AnchorDTO anchorDTO = new AnchorDTO();
        BeanUtils.copyProperties(anchorInfo, anchorDTO);
        anchorDTO.setUserInfo(userInfo);
        anchorDTO.setAnchorInfo(anchorInfo);
        anchorDTO.setPlatformInfo(platformInfo);
        return anchorDTO;
    }

    @Override
    public AnchorInfo findByLiveId(String liveId) {
        return anchorInfoRepository.findByLiveId(liveId);
    }

    @Override
    public List<AnchorInfo> findAllByUserId(Integer id) {
        return anchorInfoRepository.findAllByUserId(id);
    }
}
