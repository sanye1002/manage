package com.sanye.manage.service;

import com.sanye.manage.dataobject.MOMOAnchorSalary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-17 下午 7:18
 * @Description description
 */

public interface MOMOAnchorSalaryService  {

    Map<String,Object> save(List<MOMOAnchorSalary> list);

    Page<MOMOAnchorSalary> findAllByMonth(Pageable pageable, String month);

    Page<MOMOAnchorSalary> findAllByAnchorId(Pageable pageable,Integer anchorId);

    MOMOAnchorSalary findByAnchorIdAndMonth(Integer anchorId,String month);

    BigDecimal findSalaryByUserId(Integer id);

    Page<MOMOAnchorSalary> findAllByUserId(Pageable pageable,Integer userId);

    MOMOAnchorSalary save(MOMOAnchorSalary momoAnchorSalary);
    MOMOAnchorSalary findOne(Integer id);
}
