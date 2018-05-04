package com.sanye.manage.repository;

import com.sanye.manage.dataobject.ItemInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-27 上午 11:39
 * @Description description
 *
 */

public interface ItemInfoRepository extends JpaRepository<ItemInfo,Integer>{
    Page<ItemInfo> findAll(Pageable pageable);
}
