package com.sanye.manage.repository;

import com.sanye.manage.dataobject.PersonnelInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-09 下午 7:08
 */
public interface PersonnelInfoRepository extends JpaRepository<PersonnelInfo,Integer> {

    Page<PersonnelInfo> findAllByShowStatus(Pageable pageable,Integer showStatus);

    Page<PersonnelInfo> findAllByShowStatusAndStatus(Pageable pageable,Integer status,Integer showStatus);

    Page<PersonnelInfo> findAllByShowStatusAndStatusAndDeptNo(Pageable pageable,Integer status,Integer showStatus,String deptNo);

    List<PersonnelInfo> findAllByShowStatus(Integer showStatus);

    Page<PersonnelInfo> findAllByShowStatusAndStatusAndNameLike(Pageable pageable,Integer showStatus,Integer status,String name);

    List<PersonnelInfo> findByDeptNo(String deptNo);

    List<PersonnelInfo> findAllByPhone(String phone);

    PersonnelInfo findByUserId(Integer id);

    PersonnelInfo findByPhone(String phone);
}
