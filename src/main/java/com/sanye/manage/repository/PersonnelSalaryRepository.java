package com.sanye.manage.repository;

import com.sanye.manage.dataobject.PersonnelSalary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-23 下午 4:02
 * @Description description
 */
public interface PersonnelSalaryRepository extends JpaRepository<PersonnelSalary,Integer>{

    Page<PersonnelSalary> findAllByMonth(Pageable pageable,String month);

    PersonnelSalary findByPhoneAndMonth(String phone,String month);
}
