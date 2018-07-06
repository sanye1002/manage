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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, Object> revoke(Integer id) {
        Integer count = checkInfoRepository.deleteAllByTypeAndApplyId("工作人员工资预支",id);
        Map<String, Object> map  = new HashMap<>();
        System.out.println("删除的条数="+count);
        if (count==0){
           map.put("code",100);
           map.put("message","无审核记录");
        }else {
            map.put("code",0);
            map.put("message","撤回【"+count+"】条记录");
        }
        personnelSalaryAdvanceRepository.delete(id);
        return map;
    }

    @Override
    public Map<String,Object> countAllByMonthAndResultStatus(String month) {
        List<PersonnelSalaryAdvance> anchorSalaryAdvanceListAll = personnelSalaryAdvanceRepository.findAllByMonthAndResultStatus(month,1);
        List<PersonnelSalaryAdvance> anchorSalaryAdvanceListNoBack = personnelSalaryAdvanceRepository.findAllByMonthAndResultStatusAndBackStatus(month,1,0);
        List<PersonnelSalaryAdvance> anchorSalaryAdvanceListBack = personnelSalaryAdvanceRepository.findAllByMonthAndResultStatusAndBackStatus(month,1,1);
        Map<String,Object> map = new HashMap<>();
        final BigDecimal[] allSalary = {new BigDecimal(0.00)};
        final BigDecimal[] backSalary = {new BigDecimal(0.00)};
        final BigDecimal[] noBackSalary = {new BigDecimal(0.00)};
        if (anchorSalaryAdvanceListAll.isEmpty()){
            map.put("allSalary",allSalary[0]);
            if (anchorSalaryAdvanceListNoBack.isEmpty()){
                map.put("noBackSalary",noBackSalary[0]);
            }
            if (anchorSalaryAdvanceListBack.isEmpty()){
                map.put("backSalary",backSalary[0]);

            }
            return map;
        }
        anchorSalaryAdvanceListAll.forEach( l ->{
            allSalary[0] = allSalary[0].add(l.getSalary());
        });
        anchorSalaryAdvanceListNoBack.forEach(l->{

            noBackSalary[0] = noBackSalary[0].add(l.getSalary());
        });
        anchorSalaryAdvanceListBack.forEach(l->{
            backSalary[0] = backSalary[0].add(l.getSalary());
        });

        map.put("allSalary",allSalary[0]);
        map.put("backSalary",backSalary[0]);
        map.put("noBackSalary",noBackSalary[0]);
        return map;
    }
}
