package com.sanye.manage.repository;

import com.sanye.manage.dataobject.CaseMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-10 下午 12:37
 * @Description description
 */
public interface CaseMasterRepository extends JpaRepository<CaseMaster,Integer> {
    Page<CaseMaster> findAllByUserId(Pageable pageable,Integer userId);

    Page<CaseMaster> findAllByCheckDeptNo(Pageable pageable,String checkDeptNo);
}
