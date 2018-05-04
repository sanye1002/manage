package com.sanye.manage.service.impl;

import com.sanye.manage.dataobject.ItemInfo;
import com.sanye.manage.repository.ItemInfoRepository;
import com.sanye.manage.service.ItemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-27 上午 11:52
 * @Description description
 */
@Service
@Transactional
public class ItemInfoServiceImpl implements ItemInfoService {
    @Autowired
    private ItemInfoRepository itemInfoRepository;

    @Override
    public ItemInfo save(ItemInfo itemInfo) {
        return itemInfoRepository.save(itemInfo);
    }

    @Override
    public ItemInfo findOne(Integer id) {
        return itemInfoRepository.findOne(id);
    }

    @Override
    public void delete(Integer id) {
        itemInfoRepository.delete(id);
    }

    @Override
    public Page<ItemInfo> findAll(Pageable pageable) {

        return itemInfoRepository.findAll(pageable);
    }

    @Override
    public List<ItemInfo> findAll() {
        return itemInfoRepository.findAll();
    }
}
