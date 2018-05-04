package com.sanye.manage.repository;

import com.sanye.manage.dataobject.MOMOAnchorSalary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-17 下午 7:13
 * @Description description
 */
public interface MOMOAnchorSalaryRepository extends JpaRepository<MOMOAnchorSalary,Integer>  {

    Page<MOMOAnchorSalary> findAllByMonth(Pageable pageable,String month);

    Page<MOMOAnchorSalary> findAllByAnchorId(Pageable pageable,Integer anchorId);

    MOMOAnchorSalary findByLiveIdAndMonth(String liveId,String month);

    MOMOAnchorSalary findByAnchorIdAndMonth(Integer anchorId,String month);

    List<MOMOAnchorSalary> findAllByUserId(Integer id);

    Page<MOMOAnchorSalary> findAllByUserId(Pageable pageable,Integer userId);


}
