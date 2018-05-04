package com.sanye.manage.repository;

import com.sanye.manage.dataobject.AnchorSalaryAdvance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-25 下午 4:00
 * @Description description
 */
public interface AnchorSalaryAdvanceRepository extends JpaRepository<AnchorSalaryAdvance,Integer>{

    Page<AnchorSalaryAdvance> findAllByUserId(Pageable pageable, Integer id);

    List<AnchorSalaryAdvance> findAllByMonthAndCheckStatus(String month, Integer checkStatus);

    List<AnchorSalaryAdvance> findAllByMonthAndResultStatus(String month,Integer resultStatus);

    Page<AnchorSalaryAdvance> findAllByMonthAndCheckStatusAndResultStatus(Pageable pageable,String month,Integer checkStatus,Integer resultStatus);
}
