package com.sanye.manage.service.impl;

import com.sanye.manage.DTO.CheckNoticeDTO;
import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.dataobject.*;
import com.sanye.manage.enums.ResultEnum;
import com.sanye.manage.exception.WebException;
import com.sanye.manage.form.CheckForm;
import com.sanye.manage.repository.*;
import com.sanye.manage.service.CheckService;
import com.sanye.manage.utils.GetTimeUtil;
import com.sanye.manage.utils.SortTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-19 下午 7:22
 * @Description description
 */
@Service
@Transactional
@Slf4j
public class CheckServiceImpl implements CheckService {

    @Autowired
    private SpendingInfoRepository spendingInfoRepository;

    @Autowired
    private PersonnelInfoRepository personnelInfoRepository;

    @Autowired
    private DeptInfoRepository deptInfoRepository;

    @Autowired
    private CheckInfoRepository checkInfoRepository;

    @Autowired
    private PlatformInfoRepository platformInfoRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;

    public void checkSave001(CheckInfo Info){

        List<PersonnelInfo> personnelInfoList001 = personnelInfoRepository.findByDeptNo("001");
        DeptInfo deptInfo001 = deptInfoRepository.findByDeptNo("001");

        for (int i = 0; i < personnelInfoList001.size(); i++) {
            if (Info.getApplyPersonnelId() == personnelInfoList001.get(i).getId() && personnelInfoList001.size() > 1) {
                continue;
            }
            CheckInfo checkInfo = new CheckInfo();
            checkInfo.setApplyId(Info.getApplyId());
            checkInfo.setAcceptDeptNo("001");
            checkInfo.setApplyTime(Info.getApplyTime());
            checkInfo.setApplyPersonnelId(Info.getApplyPersonnelId());
            checkInfo.setApplyPersonnelName(Info.getApplyPersonnelName());
            checkInfo.setAcceptPersonnelId(personnelInfoList001.get(i).getId());
            checkInfo.setOrderId(2);
            checkInfo.setAcceptPersonnelName(personnelInfoList001.get(i).getName());
            checkInfo.setType(Info.getType());
            checkInfo.setSalary(Info.getSalary());
            checkInfo.setTitle(Info.getTitle());
            checkInfo.setDescription(Info.getDescription());
            checkInfoRepository.save(checkInfo);
        }
    }
    @Override
    public void personnelCheckSave(CheckForm checkForm) {

        PersonnelInfo applyPerson = personnelInfoRepository.findByUserId(checkForm.getApplyPersonnelId());
        List<PersonnelInfo> personnelInfoList001 = personnelInfoRepository.findByDeptNo("001");
        /*//日常开支
        if (applyPerson.getDeptNo().equals("001")) {
            // 保存001部门的人员
            for (int i = 0; i < personnelInfoList001.size(); i++) {
                if (checkForm.getApplyPersonnelId() == personnelInfoList001.get(i).getId() && personnelInfoList001.size() > 1) {
                    continue;
                }
                CheckInfo checkInfo = new CheckInfo();
                checkInfo.setApplyId(checkForm.getApplyId());
                checkInfo.setAcceptDeptNo("001");
                checkInfo.setApplyTime(checkForm.getApplyTime());
                checkInfo.setApplyPersonnelId(applyPerson.getId());
                checkInfo.setApplyPersonnelName(applyPerson.getName());
                checkInfo.setAcceptPersonnelId(personnelInfoList001.get(i).getId());
                checkInfo.setOrderId(1);
                checkInfo.setAcceptPersonnelName(personnelInfoList001.get(i).getName());
                checkInfo.setType(checkForm.getType());
                checkInfo.setSalary(checkForm.getSalary());
                checkInfo.setTitle(checkForm.getTitle());
                checkInfo.setDescription(checkForm.getDescription());
                checkInfoRepository.save(checkInfo);
            }

        } else {*/
            // 查询该人员是否问部门领导
            DeptInfo applyDept = deptInfoRepository.findByDeptNo(applyPerson.getDeptNo());

            if (applyPerson.getId() == applyDept.getPersonnelId()) {

                for (int i = 0; i < personnelInfoList001.size(); i++) {

                    CheckInfo checkInfo = new CheckInfo();
                    checkInfo.setApplyId(checkForm.getApplyId());
                    checkInfo.setAcceptDeptNo(personnelInfoList001.get(i).getDeptNo());
                    checkInfo.setApplyTime(checkForm.getApplyTime());
                    checkInfo.setAcceptPersonnelId(personnelInfoList001.get(i).getId());
                    checkInfo.setOrderId(1);
                    checkInfo.setAcceptPersonnelName(personnelInfoList001.get(i).getName());
                    checkInfo.setType(checkForm.getType());
                    checkInfo.setTitle(checkForm.getTitle());
                    checkInfo.setSalary(checkForm.getSalary());
                    checkInfo.setDescription(checkForm.getDescription());
                    checkInfo.setApplyPersonnelId(applyPerson.getId());
                    checkInfo.setApplyPersonnelName(applyPerson.getName());
                    checkInfoRepository.save(checkInfo);
                }
            } else {
                // 2. 查看该人员的部门领导
                int order = 1;
                if (applyDept.getPersonnelId() != null) {
                    order = order + 1;
                    CheckInfo checkInfo = new CheckInfo();
                    checkInfo.setApplyId(checkForm.getApplyId());
                    checkInfo.setAcceptDeptNo(applyDept.getDeptNo());

                    checkInfo.setAcceptPersonnelId(applyDept.getPersonnelId());
                    checkInfo.setOrderId(1);
                    checkInfo.setAcceptPersonnelName(applyDept.getPersonnelName());
                    checkInfo.setType(checkForm.getType());
                    checkInfo.setTitle(checkForm.getTitle());
                    checkInfo.setSalary(checkForm.getSalary());
                    checkInfo.setDescription(checkForm.getDescription());
                    checkInfo.setApplyPersonnelId(applyPerson.getId());
                    checkInfo.setApplyTime(checkForm.getApplyTime());
                    checkInfo.setApplyPersonnelName(applyPerson.getName());
                    checkInfoRepository.save(checkInfo);
                }
                //保存001
                /*for (int i = 0; i < personnelInfoList001.size(); i++) {
                    CheckInfo checkInfo = new CheckInfo();
                    checkInfo.setApplyId(checkForm.getApplyId());
                    checkInfo.setAcceptDeptNo(personnelInfoList001.get(i).getDeptNo());
                    checkInfo.setAcceptPersonnelId(personnelInfoList001.get(i).getId());
                    checkInfo.setOrderId(order);
                    checkInfo.setAcceptPersonnelName(personnelInfoList001.get(i).getName());
                    checkInfo.setType(checkForm.getType());
                    checkInfo.setSalary(checkForm.getSalary());
                    checkInfo.setTitle(checkForm.getTitle());
                    checkInfo.setDescription(checkForm.getDescription());
                    checkInfo.setApplyPersonnelId(applyPerson.getId());
                    checkInfo.setApplyPersonnelName(applyPerson.getName());
                    checkInfo.setApplyTime(checkForm.getApplyTime());
                    checkInfoRepository.save(checkInfo);
                }*/

            }
        //}


        // 工资申请


    }

    @Override
    public void anchorCheckSave(CheckForm checkForm) {
        UserInfo applyPerson = userInfoRepository.findOne(checkForm.getApplyPersonnelId());
        PlatformInfo platformInfo = platformInfoRepository.findOne(checkForm.getPlatformId());
        PersonnelInfo platformBoss = personnelInfoRepository.findOne(platformInfo.getUserId());
        //1 平台领导

        if (platformInfo.getUserId() == null) {
            throw new WebException(ResultEnum.PLATFORM_BOOS_NULL);
        } else {

            CheckInfo checkInfo1 = new CheckInfo();
            checkInfo1.setApplyId(checkForm.getApplyId());
            checkInfo1.setAcceptDeptNo(platformBoss.getDeptNo());
            checkInfo1.setApplyTime(checkForm.getApplyTime());
            checkInfo1.setApplyPersonnelId(applyPerson.getId());
            checkInfo1.setApplyPersonnelName(applyPerson.getName());
            checkInfo1.setAcceptPersonnelId(platformBoss.getId());
            checkInfo1.setOrderId(1);
            checkInfo1.setAcceptPersonnelName(platformBoss.getName());
            checkInfo1.setType(checkForm.getType());
            checkInfo1.setSalary(checkForm.getSalary());
            checkInfo1.setTitle(checkForm.getTitle());
            checkInfo1.setDescription(checkForm.getDescription());
            checkInfoRepository.save(checkInfo1);
        }
        /*for (int i = 0; i < personnelInfoList001.size(); i++) {
            if (personnelInfoList001.get(i).getUserId()==platformInfo.getUserId()){
                continue;
            }
            CheckInfo checkInfo = new CheckInfo();
            checkInfo.setApplyId(checkForm.getApplyId());
            checkInfo.setAcceptDeptNo("001");
            checkInfo.setApplyTime(checkForm.getApplyTime());
            checkInfo.setApplyPersonnelId(applyPerson.getId());
            checkInfo.setApplyPersonnelName(applyPerson.getName());
            checkInfo.setAcceptPersonnelId(personnelInfoList001.get(i).getId());
            checkInfo.setOrderId(2);
            checkInfo.setAcceptPersonnelName(personnelInfoList001.get(i).getName());
            checkInfo.setType(checkForm.getType());
            checkInfo.setSalary(checkForm.getSalary());
            checkInfo.setTitle(checkForm.getTitle());
            checkInfo.setDescription(checkForm.getDescription());
            checkInfoRepository.save(checkInfo);
        }*/
    }

    @Override
    public void check(CheckForm checkForm) {
        CheckInfo checkInfo = checkInfoRepository.findOne(checkForm.getId());

        /**判断是否为001**/
        if (checkInfo.getAcceptDeptNo().equals("001")) {
            List<CheckInfo> checkInfoList = checkInfoRepository.findAllByAcceptDeptNoAndApplyIdAndType("001", checkInfo.getApplyId(), checkInfo.getType());
            checkInfoList.forEach(l -> {
                l.setCheckTime(GetTimeUtil.getTime());
                l.setResultStatus(checkForm.getResultStatus());
                l.setCheckStatus(1);
                l.setRemark(checkForm.getRemark());
                l.setCheckPersonnelId(checkForm.getCheckPersonnelId());
                l.setCheckPersonnelName(checkForm.getCheckPersonnelName());
                checkInfoRepository.save(l);
            });
        } else {
            checkInfo.setCheckTime(GetTimeUtil.getTime());
            checkInfo.setCheckStatus(1);
            checkInfo.setResultStatus(checkForm.getResultStatus());
            checkInfo.setRemark(checkForm.getRemark());
            checkInfo.setCheckPersonnelId(checkForm.getCheckPersonnelId());
            checkInfo.setCheckPersonnelName(checkForm.getCheckPersonnelName());
            checkInfoRepository.save(checkInfo);
            if (checkForm.getResultStatus()==1){
                this.checkSave001(checkInfo);
            }
        }
    }


    @Override
    public void updateCheck(CheckForm checkForm) {
        List<CheckInfo> checkInfoList = checkInfoRepository.findAllByApplyIdAndType(checkForm.getApplyId(), checkForm.getType(), SortTools.basicSort());
        if (checkForm.getResultStatus() == 0) {
            checkInfoList.forEach(l ->
            {
                l.setResultStatus(0);
                l.setRemark("财务驳回！");
                checkInfoRepository.save(l);

            });
        }
    }

    @Override
    public CheckInfo findOne(Integer id) {
        return checkInfoRepository.findOne(id);
    }

    @Override
    public CheckInfo findByApplyIdAndTypeAndAcceptPersonnelId(Integer applyId, String type, Integer acceptPersonnelId) {
        return checkInfoRepository.findByApplyIdAndTypeAndAcceptPersonnelId(applyId, type, acceptPersonnelId);
    }

    @Override
    public List<CheckInfo> findAllByApplyIdAndType(Integer applyId, String type, Sort sort) {
        return checkInfoRepository.findAllByApplyIdAndType(applyId, type, sort);
    }

    @Override
    public Page<CheckInfo> findAllByAcceptPersonnelId(Pageable pageable, Integer acceptPersonnelId) {
        return checkInfoRepository.findAllByAcceptPersonnelId(pageable, acceptPersonnelId);
    }

    @Override
    public Page<CheckInfo> findAllByAcceptPersonnelIdAndCheckStatus(Pageable pageable, Integer acceptPersonnelId, Integer checkStatus) {
        return checkInfoRepository.findAllByAcceptPersonnelIdAndCheckStatus(pageable, acceptPersonnelId, checkStatus);
    }

    @Override
    public List<CheckInfo> findAllByAcceptPersonnelIdAndCheckStatus(Integer acceptPersonnelId, Integer checkStatus) {
        return checkInfoRepository.findAllByAcceptPersonnelIdAndCheckStatus(acceptPersonnelId, checkStatus);
    }

    @Override
    public List<CheckInfo> findAllByAcceptDeptNoAndApplyIdAndType(String acceptDeptNo, Integer applyId, String type) {
        return checkInfoRepository.findAllByAcceptDeptNoAndApplyIdAndType(acceptDeptNo, applyId, type);
    }


}
