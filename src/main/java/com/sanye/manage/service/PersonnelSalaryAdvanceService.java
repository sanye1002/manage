package com.sanye.manage.service;

import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.DTO.PersonnelSalaryAdvanceDTO;
import com.sanye.manage.dataobject.PersonnelSalary;
import com.sanye.manage.dataobject.PersonnelSalaryAdvance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-24 下午 2:45
 * @Description description
 */
public interface PersonnelSalaryAdvanceService {
    /**
     *
     * @param personnelSalaryAdvance
     * @return
     */
    PersonnelSalaryAdvance save(PersonnelSalaryAdvance personnelSalaryAdvance);

    /**
     *
     * @param id
     * @return
     */
    PersonnelSalaryAdvance findOne(Integer id);

    /**
     *
     * @param id
     */
    void delete(Integer id);

    /**
     *
     * @param pageable
     * @param id
     * @return
     */
    Page<PersonnelSalaryAdvance> findAllByPersonnelId(Pageable pageable, Integer id);

    /**
     *
     * @param month
     * @param checkStatus
     * @return
     */
    List<PersonnelSalaryAdvance> findAllByMonthAndCheckStatus(String month, Integer checkStatus);

    /**
     *
     * @param pageable
     * @param month
     * @param checkStatus
     * @param resultStatus
     * @return
     */
    PageDTO<PersonnelSalaryAdvanceDTO> findAllByMonthAndCheckStatusAndResultStatus(Pageable pageable, String month, Integer checkStatus, Integer resultStatus);

    /**
     *
     * @param month
     * @param resultStatus
     * @return
     */
    List<PersonnelSalaryAdvance> findAllByMonthAndResultStatus(String month,Integer resultStatus);
}
