package com.sanye.manage.service;

import com.sanye.manage.DTO.ItemDebitDTO;
import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.dataobject.ItemDebit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-27 下午 4:34
 * @Description description
 */
public interface ItemDebitService {

    ItemDebit save(ItemDebit itemDebit);

    ItemDebit findOne(Integer id);

    void delete(Integer id);

    Page<ItemDebit> findAllByUserId(Pageable pageable, Integer id);

    List<ItemDebit> findAllByCheckStatus(Integer checkStatus);

    List<ItemDebit> findAllByResultStatus(Integer resultStatus);

    PageDTO<ItemDebitDTO> findAllByCheckStatusAndResultStatus(Pageable pageable, Integer checkStatus, Integer resultStatus);
}
