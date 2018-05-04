package com.sanye.manage.repository;

import com.sanye.manage.dataobject.SpendingInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-18 下午 5:42
 * @Description description
 */
public interface SpendingInfoRepository extends JpaRepository<SpendingInfo,Integer> {

    List<SpendingInfo> findAllByPersonnelId(Integer id);

    List<SpendingInfo> findAllByMonthAndResultStatus(String month,Integer resultStatus);

    Page<SpendingInfo> findAllByPersonnelId(Pageable pageable,Integer id);

    Page<SpendingInfo> findAllByMonthAndCheckStatusAndResultStatus(Pageable pageable,String month,Integer checkStatus,Integer resultStatus);

    Page<SpendingInfo> findAllByMonthAndResultStatus(Pageable pageable,String month, Integer resultStatus);

    Page<SpendingInfo> findAllByMonth(Pageable pageable,String month);

    List<SpendingInfo> findAllByMonth(String month);


}
