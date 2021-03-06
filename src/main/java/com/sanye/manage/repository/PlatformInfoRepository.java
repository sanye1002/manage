package com.sanye.manage.repository;

import com.sanye.manage.dataobject.PlatformInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-12 下午 12:19
 *
 */
public interface PlatformInfoRepository extends JpaRepository<PlatformInfo,Integer>{

    Page<PlatformInfo> findAll(Pageable pageable);

    PlatformInfo findByName(String name);
}
