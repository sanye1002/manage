package com.sanye.manage.service.impl;

import com.sanye.manage.DTO.CaseMasterDTO;
import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.dataobject.*;
import com.sanye.manage.form.ApplicationCaseForm;
import com.sanye.manage.repository.*;
import com.sanye.manage.service.CaseService;
import com.sanye.manage.utils.GetTimeUtil;
import com.sanye.manage.utils.ShiroGetSession;
import org.springframework.beans.BeanUtils;
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
 * @create 2018-06-10 下午 1:36
 * @Description description
 */
@Service
@Transactional
public class CaseServiceImpl implements CaseService {

    @Autowired
    private ApplicationCaseRepository applicationCaseRepository;
    @Autowired
    private BackCaseRepository backCaseRepository;
    @Autowired
    private CaseMasterRepository caseMasterRepository;
    @Autowired
    private DeptInfoRepository deptInfoRepository;
    @Autowired
    private PersonnelInfoRepository personnelInfoRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Override
    public void saveApplicationCase(ApplicationCaseForm applicationCaseForm) {

        applicationCaseForm.setMonth(GetTimeUtil.getMonth());
        applicationCaseForm.setCreateTime(GetTimeUtil.getDate());
        applicationCaseForm.setUserId(ShiroGetSession.getUserInfo().getId());
        applicationCaseForm.setUsername(ShiroGetSession.getUserInfo().getName());
        ApplicationCase applicationCase = new ApplicationCase();
        if (applicationCaseForm.getId() != null) {
            applicationCase = applicationCaseRepository.findOne(applicationCaseForm.getId());
        }
        BeanUtils.copyProperties(applicationCaseForm, applicationCase);
        ApplicationCase result = applicationCaseRepository.save(applicationCase);
        if (applicationCaseForm.getId() == null) {

            //申请人员
            PersonnelInfo personnelInfo = personnelInfoRepository.findByUserId(ShiroGetSession.getUserInfo().getId());
            //该人员部门
            DeptInfo BoosDept = deptInfoRepository.findByDeptNo(personnelInfo.getDeptNo());
            //该人员部门领导
            PersonnelInfo Boss = personnelInfoRepository.findOne(BoosDept.getPersonnelId());
            List<PersonnelInfo> list = personnelInfoRepository.findByDeptNo("001");
            if (BoosDept.getPersonnelId()==personnelInfo.getId()){
                    CaseMaster caseMaster = new CaseMaster();
                    caseMaster.setApplicationId(result.getId());
                    caseMaster.setUserId(applicationCase.getUserId());
                    caseMaster.setCheckDeptNo("001");
                    caseMasterRepository.save(caseMaster);
            }else {
                    CaseMaster caseMaster = new CaseMaster();
                    caseMaster.setApplicationId(result.getId());
                    caseMaster.setUserId(applicationCase.getUserId());
                    caseMaster.setCheckDeptNo(Boss.getDeptNo());
                    caseMasterRepository.save(caseMaster);
            }
        }
    }

    @Override
    public ApplicationCase saveApplicationCase(ApplicationCase applicationCase) {
        return applicationCaseRepository.save(applicationCase);
    }

    @Override
    public ApplicationCase findOneApplicationCase(Integer id) {
        return applicationCaseRepository.findOne(id);
    }


    @Override
    public void deleteApplicationCaseById(Integer id) {

    }

    @Override
    public BackCase saveBackCase(BackCase backCase) {
        return backCaseRepository.save(backCase);
    }

    @Override
    public BackCase findOneBackCase(Integer id) {
        return backCaseRepository.findOne(id);
    }

    @Override
    public CaseMaster saveCaseMaster(CaseMaster caseMaster) {
        return caseMasterRepository.save(caseMaster);
    }

    @Override
    public CaseMaster findOneCaseMaster(Integer id) {
        return caseMasterRepository.findOne(id);
    }

    @Override
    public PageDTO<CaseMasterDTO> findAllByCheckUserId(Pageable pageable,Integer userId) {
        PersonnelInfo personnelInfo = personnelInfoRepository.findByUserId(userId);
        //该人员部门
        DeptInfo Dept = deptInfoRepository.findByDeptNo(personnelInfo.getDeptNo());
        Page<CaseMaster> page = caseMasterRepository.findAllByCheckDeptNo(pageable,Dept.getDeptNo());
        PageDTO<CaseMasterDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(page.getTotalPages());
        List<CaseMasterDTO> list = new ArrayList<>();
        if (!page.getContent().isEmpty()){
            page.getContent().forEach(l->{
                CaseMasterDTO caseMasterDTO = new CaseMasterDTO();
                caseMasterDTO.setApplicationCase(applicationCaseRepository.findOne(l.getApplicationId()));
                caseMasterDTO.setId(l.getId());
                caseMasterDTO.setUserInfo(userInfoRepository.findOne(l.getUserId()));
                if (l.getBackId()==null){
                    BackCase backCase = new BackCase();
                    backCase.setId(0);
                    caseMasterDTO.setBackCase(backCase);
                }else {
                    caseMasterDTO.setBackCase(backCaseRepository.findOne(l.getBackId()));
                }
                list.add(caseMasterDTO);
            });
        }
        pageDTO.setPageContent(list);
        return pageDTO;
    }

    @Override
    public PageDTO<CaseMasterDTO> findAllByUserId(Pageable pageable, Integer userId) {
        Page<CaseMaster> page = caseMasterRepository.findAllByUserId(pageable,userId);
        PageDTO<CaseMasterDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(page.getTotalPages());
        List<CaseMasterDTO> list = new ArrayList<>();
        if (!page.getContent().isEmpty()){
            page.getContent().forEach(l->{
                CaseMasterDTO caseMasterDTO = new CaseMasterDTO();
                caseMasterDTO.setApplicationCase(applicationCaseRepository.findOne(l.getApplicationId()));
                caseMasterDTO.setId(l.getId());
                caseMasterDTO.setUserInfo(userInfoRepository.findOne(l.getUserId()));
                if (l.getBackId()==null){
                    BackCase backCase = new BackCase();
                    backCase.setId(0);
                    caseMasterDTO.setBackCase(backCase);
                }else {
                    caseMasterDTO.setBackCase(backCaseRepository.findOne(l.getBackId()));
                }
                list.add(caseMasterDTO);
            });
        }
        pageDTO.setPageContent(list);
        return pageDTO;
    }

    @Override
    public PageDTO<CaseMasterDTO> findAll(Pageable pageable) {
        Page<CaseMaster> page = caseMasterRepository.findAll(pageable);
        PageDTO<CaseMasterDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(page.getTotalPages());
        List<CaseMasterDTO> list = new ArrayList<>();
        if (!page.getContent().isEmpty()){
            page.getContent().forEach(l->{
                CaseMasterDTO caseMasterDTO = new CaseMasterDTO();
                caseMasterDTO.setApplicationCase(applicationCaseRepository.findOne(l.getApplicationId()));
                caseMasterDTO.setId(l.getId());
                caseMasterDTO.setUserInfo(userInfoRepository.findOne(l.getUserId()));
                if (l.getBackId()==null){
                    BackCase backCase = new BackCase();
                    backCase.setId(0);
                    caseMasterDTO.setBackCase(backCase);
                }else {
                    caseMasterDTO.setBackCase(backCaseRepository.findOne(l.getBackId()));
                }
                list.add(caseMasterDTO);
            });
        }
        pageDTO.setPageContent(list);
        return pageDTO;
    }
}
