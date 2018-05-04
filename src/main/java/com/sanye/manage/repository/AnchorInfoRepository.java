package com.sanye.manage.repository;

import com.sanye.manage.dataobject.AnchorInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-13 上午 11:48
 */
public interface AnchorInfoRepository extends JpaRepository<AnchorInfo,Integer>{

    Page<AnchorInfo> findAllByPlatformIdAndStatusAndShowStatus(Pageable pageable,Integer platform,Integer status,Integer showStatus);
    Page<AnchorInfo> findAllByStatusAndShowStatus(Pageable pageable,Integer status,Integer showStatus);

    AnchorInfo findByUserId(Integer id);

    List<AnchorInfo> findAllByUserId(Integer id);

    List<AnchorInfo> findAllByPlatformId(Integer platformId);

    AnchorInfo findByLiveId(String liveId);

    AnchorInfo findByLiveIdAndPlatformId(String liveId,Integer PlatformId);


}
