package com.sanye.manage.repository;

import com.sanye.manage.dataobject.ItemDebit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-27 下午 4:24
 * @Description description
 */

public interface ItemDebitRepository extends JpaRepository<ItemDebit,Integer>{

    Page<ItemDebit> findAllByUserId(Pageable pageable, Integer id);

    List<ItemDebit> findAllByCheckStatus( Integer checkStatus);

    List<ItemDebit> findAllByResultStatus(Integer resultStatus);

    Page<ItemDebit> findAllByCheckStatusAndResultStatus(Pageable pageable,Integer checkStatus,Integer resultStatus);
}
