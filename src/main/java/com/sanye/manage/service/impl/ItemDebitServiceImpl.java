package com.sanye.manage.service.impl;

import com.sanye.manage.DTO.ItemDebitDTO;
import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.dataobject.ItemDebit;
import com.sanye.manage.repository.CheckInfoRepository;
import com.sanye.manage.repository.ItemDebitRepository;
import com.sanye.manage.repository.UserInfoRepository;
import com.sanye.manage.service.ItemDebitService;
import com.sanye.manage.utils.SortTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-27 下午 5:05
 * @Description description
 */
@Service
@Transactional
public class ItemDebitServiceImpl implements ItemDebitService {

    @Autowired
    private CheckInfoRepository checkInfoRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private ItemDebitRepository itemDebitRepository;

    @Override
    public ItemDebit save(ItemDebit itemDebit) {
        return itemDebitRepository.save(itemDebit);
    }

    @Override
    public ItemDebit findOne(Integer id) {
        return itemDebitRepository.findOne(id);
    }

    @Override
    public void delete(Integer id) {
        itemDebitRepository.delete(id);
    }

    @Override
    public Page<ItemDebit> findAllByUserId(Pageable pageable, Integer id) {
        return itemDebitRepository.findAllByUserId(pageable, id);
    }

    @Override
    public List<ItemDebit> findAllByCheckStatus(Integer checkStatus) {
        return itemDebitRepository.findAllByCheckStatus(checkStatus);
    }

    @Override
    public List<ItemDebit> findAllByResultStatus(Integer resultStatus) {
        return itemDebitRepository.findAllByResultStatus(resultStatus);
    }

    @Override
    public PageDTO<ItemDebitDTO> findAllByCheckStatusAndResultStatus(Pageable pageable, Integer checkStatus, Integer resultStatus) {
        PageDTO<ItemDebitDTO> pageDTO = new PageDTO<>();
        Page<ItemDebit> page = itemDebitRepository.findAllByCheckStatusAndResultStatus(pageable, checkStatus, resultStatus);
        pageDTO.setTotalPages(page.getTotalPages());
        List<ItemDebitDTO> list=new ArrayList<>();
        if (!page.getContent().isEmpty()){
            page.getContent().forEach( l->{
                ItemDebitDTO debitDTO = new ItemDebitDTO();
                debitDTO.setItemDebit(l);
                debitDTO.setCheckInfoList(checkInfoRepository.findAllByApplyIdAndType(l.getId(),"物品借记", SortTools.basicSort("asc","orderId")));
                debitDTO.setUserInfo(userInfoRepository.findOne(l.getUserId()));
                list.add(debitDTO);
            });
        }
        pageDTO.setPageContent(list);
        return pageDTO;
    }
}
