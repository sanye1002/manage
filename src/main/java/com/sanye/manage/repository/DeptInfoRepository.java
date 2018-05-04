package com.sanye.manage.repository;

import com.sanye.manage.dataobject.DeptInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-10 上午 10:33
 */
public interface DeptInfoRepository extends JpaRepository<DeptInfo,Integer> {

    DeptInfo findByDeptNo(String deptNo);

    Page<DeptInfo> findAll(Pageable pageable);

    List<DeptInfo> findByNextNo(String nextNo);

}
