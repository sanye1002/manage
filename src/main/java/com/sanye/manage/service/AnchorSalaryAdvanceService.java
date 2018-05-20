package com.sanye.manage.service;

import com.sanye.manage.DTO.AnchorSalaryAdvanceDTO;
import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.dataobject.AnchorSalaryAdvance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-25 下午 4:04
 * @Description description
 */
public interface AnchorSalaryAdvanceService {
    AnchorSalaryAdvance save(AnchorSalaryAdvance anchorSalaryAdvance);

    AnchorSalaryAdvance findOne(Integer id);

    void delete(Integer id);

    Page<AnchorSalaryAdvance> findAllByUserId(Pageable pageable, Integer id);

    List<AnchorSalaryAdvance> findAllByMonthAndResultStatus(String month,Integer resultStatus);

    PageDTO<AnchorSalaryAdvanceDTO> findAllByMonthAndCheckStatusAndResultStatus(Pageable pageable, String month, Integer checkStatus, Integer resultStatus);

    Map<String,Object> revoke(Integer id);
}
