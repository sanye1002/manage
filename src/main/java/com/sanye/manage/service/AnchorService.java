package com.sanye.manage.service;

import com.sanye.manage.DTO.AnchorDTO;
import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.dataobject.AnchorInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-13 上午 11:55
 */
public interface AnchorService {
    /**
     * 保存和更新
     * @param anchorInfo
     * @return
     */
    AnchorInfo save(AnchorInfo anchorInfo);

    /**
     * 删除
     * @param id
     * @return
     */
    Map<String,Object> delete(Integer id);

    /**
     *
     * @param pageable
     * @param platform
     * @param status
     * @return
     */
    PageDTO<AnchorDTO> findAllByPlatformIdAndStatusAndShowStatus(Pageable pageable, Integer platform, Integer status);

    /**
     * 查找一个
     * @param id
     * @return
     */
    AnchorInfo findOne(Integer id);

    /**
     * 转换dto
     * @param id
     * @return
     */
    AnchorDTO findOne2DTO(Integer id);

    /**
     *
     * @param liveId
     * @return
     */
    AnchorInfo findByLiveId(String liveId);

    List<AnchorInfo> findAllByUserId(Integer id);
}
