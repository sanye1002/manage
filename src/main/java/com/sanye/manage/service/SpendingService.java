package com.sanye.manage.service;

import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.DTO.SpendingDTO;
import com.sanye.manage.dataobject.SpendingInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-18 下午 5:53
 * @Description description
 */
public interface SpendingService {
    /**
     *
     * @param id
     */
    void delete(Integer id);

    /**
     *
     * @param id
     * @return
     */
    SpendingInfo findOne(Integer id);

    /**
     *
     * @param spendingInfo
     * @return
     */
    SpendingInfo save(SpendingInfo spendingInfo);

    /**
     *
     * @param id
     * @return
     */
    List<SpendingInfo> findAllByPersonnelId(Integer id);

    /**
     *
     * @param pageable
     * @param id
     * @return
     */
    Page<SpendingInfo> findAllByPersonnelId(Pageable pageable,Integer id);

    /**
     *
     * @param month
     * @param resultStatus
     * @return
     */
    List<SpendingInfo> findAllByMonthAndResultStatus(String month,Integer resultStatus);

    /**
     *
     * @param pageable
     * @param month
     * @param resultStatus
     * @return
     */
    Page<SpendingInfo> findAllByMonthAndResultStatus(Pageable pageable,String month, Integer resultStatus);

    /**
     *
     * @param pageable
     * @param month
     * @param checkStatus
     * @param resultStatus
     * @return
     */
    Page<SpendingInfo> findAllByMonthAndCheckStatusAndResultStatus(Pageable pageable, String month, Integer checkStatus, Integer resultStatus);

    /**
     *
     * @param pageable
     * @param month
     * @return
     */
    Page<SpendingInfo> findAllByMonth(Pageable pageable,String month);


    /**
     * @param pageable
     * @param month
     * @param checkStatus
     * @param resultStatus
     * @return
     */
    PageDTO<SpendingDTO> findAllByMonthAndAllStatus(Pageable pageable, String month, Integer checkStatus, Integer resultStatus);

    /**
     *
     * @param month
     * @return
     */
    List<SpendingInfo> findAllByMonth(String month);

    Map<String,Object> revoke(Integer id);

    Integer findAllByMonthAndResultStatusAndCheckStatus(String month,Integer r,Integer c);

    /**
     * 计算当月开支总金额
     * @param month
     * @return
     */
    BigDecimal countAllByMonthAndResultStatus(String month);

    /**
     * 打印表
     * @param month
     * @param status
     * @return
     */
    HSSFWorkbook downloadToExcelByMonthAndResultStatus(String month,Integer status);

}

