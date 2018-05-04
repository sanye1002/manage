package com.sanye.manage.service;

import com.sanye.manage.DTO.AnchorSalaryWithdrawDTO;
import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.dataobject.AnchorSalaryWithdraw;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-26 下午 7:35
 * @Description description
 */
public interface WithdrawService {

    AnchorSalaryWithdraw save(AnchorSalaryWithdraw anchorSalaryWithdraw);

    AnchorSalaryWithdraw findOne(Integer id);

    void delete(Integer id);

    Page<AnchorSalaryWithdraw> findAllByUserId(Pageable pageable, Integer id);

    List<AnchorSalaryWithdraw> findAllByMonthAndResultStatus(String month, Integer resultStatus);

    PageDTO<AnchorSalaryWithdrawDTO> findAllByMonthAndCheckStatusAndResultStatus(Pageable pageable, String month, Integer checkStatus, Integer resultStatus);
}
