package com.sanye.manage.repository;

import com.sanye.manage.dataobject.CheckInfo;
import com.sanye.manage.utils.SortTools;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-19 下午 7:14
 * @Description description
 */
public interface CheckInfoRepository extends JpaRepository<CheckInfo,Integer> {

    List<CheckInfo> findAllByApplyIdAndType(Integer applyId, String type, Sort sort);

    CheckInfo findByApplyIdAndTypeAndAcceptPersonnelId(Integer applyId, String type, Integer acceptPersonnelId);

    Page<CheckInfo> findAllByAcceptPersonnelId(Pageable pageable, Integer acceptPersonnelId);

    Page<CheckInfo> findAllByAcceptPersonnelIdAndCheckStatus(Pageable pageable, Integer acceptPersonnelId, Integer checkStatus);

    List<CheckInfo> findAllByAcceptPersonnelIdAndCheckStatus(Integer acceptPersonnelId, Integer checkStatus);

    List<CheckInfo> findAllByAcceptDeptNoAndApplyIdAndType(String acceptDeptNo,Integer applyId,String type);


}
