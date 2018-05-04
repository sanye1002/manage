package com.sanye.manage.repository;

import com.sanye.manage.dataobject.AnchorSalaryWithdraw;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-26 下午 7:29
 * @Description description
 */
public interface AnchorSalaryWithdrawRepository extends JpaRepository<AnchorSalaryWithdraw,Integer> {

    Page<AnchorSalaryWithdraw> findAllByUserId(Pageable pageable, Integer id);

    List<AnchorSalaryWithdraw> findAllByMonthAndResultStatus(String month,Integer resultStatus);

    Page<AnchorSalaryWithdraw> findAllByMonthAndCheckStatusAndResultStatus(Pageable pageable,String month,Integer checkStatus,Integer resultStatus);
}
