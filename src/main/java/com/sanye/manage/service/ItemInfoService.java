package com.sanye.manage.service;

import com.sanye.manage.dataobject.ItemInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-27 上午 11:42
 * @Description description
 */
public interface ItemInfoService {

    ItemInfo save(ItemInfo itemInfo);

    ItemInfo findOne(Integer id);

    void delete(Integer id);

    Page<ItemInfo> findAll(Pageable pageable);

    List<ItemInfo> findAll();


}
