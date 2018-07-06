package com.sanye.manage.repository;

import com.sanye.manage.dataobject.PersonnelSalaryAdvance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-24 下午 2:43
 * @Description description
 */
public interface PersonnelSalaryAdvanceRepository extends JpaRepository<PersonnelSalaryAdvance,Integer>{

    Page<PersonnelSalaryAdvance> findAllByPersonnelId(Pageable pageable,Integer id);

    List<PersonnelSalaryAdvance> findAllByMonthAndCheckStatus(String month,Integer checkStatus);

    List<PersonnelSalaryAdvance> findAllByMonthAndResultStatus(String month,Integer resultStatus);

    Page<PersonnelSalaryAdvance> findAllByMonthAndCheckStatusAndResultStatus(Pageable pageable,String month,Integer checkStatus,Integer resultStatus);

    List<PersonnelSalaryAdvance> findAllByMonthAndResultStatusAndBackStatus(String month,Integer resultStatus,Integer backStatus);


}
