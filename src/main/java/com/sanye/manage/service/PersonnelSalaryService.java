package com.sanye.manage.service;

import com.sanye.manage.dataobject.PersonnelSalary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-23 下午 4:16
 * @Description description
 */
public interface PersonnelSalaryService {

    PersonnelSalary save(PersonnelSalary personnelSalary);

    Page<PersonnelSalary> findAllByMonth(Pageable pageable, String month);

    PersonnelSalary findOne(Integer id);

    Map<String,Object> saveByExcel(List<PersonnelSalary> personnelSalaryList);

    Map<String,Object> importSalaryExcel(MultipartFile file, String path);

    PersonnelSalary findByPhoneAndMonth(String phone,String month);
}
