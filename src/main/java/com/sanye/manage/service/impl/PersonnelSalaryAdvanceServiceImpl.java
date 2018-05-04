package com.sanye.manage.service.impl;

import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.DTO.PersonnelSalaryAdvanceDTO;
import com.sanye.manage.dataobject.PersonnelSalary;
import com.sanye.manage.dataobject.PersonnelSalaryAdvance;
import com.sanye.manage.repository.*;
import com.sanye.manage.service.PersonnelSalaryAdvanceService;
import com.sanye.manage.utils.SortTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-24 下午 3:24
 * @Description description
 */
@Service
@Transactional
public class PersonnelSalaryAdvanceServiceImpl implements PersonnelSalaryAdvanceService {
    @Autowired
    private PersonnelSalaryAdvanceRepository personnelSalaryAdvanceRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private CheckInfoRepository checkInfoRepository;
    @Autowired
    private PersonnelInfoRepository personnelInfoRepository;
    @Override
    public PersonnelSalaryAdvance save(PersonnelSalaryAdvance PersonnelSalaryAdvance) {
        return personnelSalaryAdvanceRepository.save(PersonnelSalaryAdvance);
    }

    @Override
    public PersonnelSalaryAdvance findOne(Integer id) {
        return personnelSalaryAdvanceRepository.findOne(id);
    }

    @Override
    public void delete(Integer id) {
        personnelSalaryAdvanceRepository.delete(id);
    }

    @Override
    public Page<PersonnelSalaryAdvance> findAllByPersonnelId(Pageable pageable, Integer id) {
        return personnelSalaryAdvanceRepository.findAllByPersonnelId(pageable, id);
    }

    @Override
    public List<PersonnelSalaryAdvance> findAllByMonthAndCheckStatus(String month, Integer checkStatus) {
        return personnelSalaryAdvanceRepository.findAllByMonthAndCheckStatus(month, checkStatus);
    }

    @Override
    public PageDTO<PersonnelSalaryAdvanceDTO> findAllByMonthAndCheckStatusAndResultStatus(Pageable pageable, String month, Integer checkStatus, Integer resultStatus) {
        PageDTO<PersonnelSalaryAdvanceDTO> pageDTO = new PageDTO<>();
        Page<PersonnelSalaryAdvance> salaryAdvancePage = personnelSalaryAdvanceRepository.findAllByMonthAndCheckStatusAndResultStatus(pageable, month, checkStatus, resultStatus);
        pageDTO.setTotalPages(salaryAdvancePage.getTotalPages());
        List<PersonnelSalaryAdvanceDTO> personnelSalaryAdvanceDTO = new ArrayList<>();
        if (!salaryAdvancePage.getContent().isEmpty()){
            salaryAdvancePage.getContent().forEach( l->{
                PersonnelSalaryAdvanceDTO salaryAdvanceDTO = new PersonnelSalaryAdvanceDTO();
                salaryAdvanceDTO.setPersonnelSalaryAdvance(l);
                salaryAdvanceDTO.setUserInfo(userInfoRepository.findOne(personnelInfoRepository.findOne(l.getPersonnelId()).getUserId()));
                salaryAdvanceDTO.setCheckInfoList(checkInfoRepository.findAllByApplyIdAndType(l.getId(),"工作人员工资预支", SortTools.basicSort("asc","orderId")));
                personnelSalaryAdvanceDTO.add(salaryAdvanceDTO);
            });
        }

        pageDTO.setPageContent(personnelSalaryAdvanceDTO);
        return pageDTO;
    }

    @Override
    public List<PersonnelSalaryAdvance> findAllByMonthAndResultStatus(String month, Integer resultStatus) {
        return personnelSalaryAdvanceRepository.findAllByMonthAndResultStatus(month, resultStatus);
    }
}
