package com.sanye.manage.service;

import com.sanye.manage.DTO.CheckNoticeDTO;
import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.dataobject.CheckInfo;
import com.sanye.manage.form.CheckForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-19 下午 7:20
 * @Description description
 */
public interface CheckService {

    void personnelCheckSave(CheckForm checkForm);

    void anchorCheckSave(CheckForm checkForm);

    void check(CheckForm checkForm);

    void updateCheck(CheckForm checkForm);

    CheckInfo findOne(Integer id);

    CheckInfo findByApplyIdAndTypeAndAcceptPersonnelId(Integer applyId, String type, Integer acceptPersonnelId);

    List<CheckInfo> findAllByApplyIdAndType(Integer applyId, String type, Sort sort);

    Page<CheckInfo> findAllByAcceptPersonnelId(Pageable pageable, Integer acceptPersonnelId);

    Page<CheckInfo> findAllByAcceptPersonnelIdAndCheckStatus(Pageable pageable, Integer acceptPersonnelId, Integer checkStatus);

    List<CheckInfo> findAllByAcceptPersonnelIdAndCheckStatus(Integer acceptPersonnelId, Integer checkStatus);

    List<CheckInfo> findAllByAcceptDeptNoAndApplyIdAndType(String acceptDeptNo,Integer applyId,String type);


}
