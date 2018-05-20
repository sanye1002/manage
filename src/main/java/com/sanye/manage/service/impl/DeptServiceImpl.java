package com.sanye.manage.service.impl;

import com.sanye.manage.DTO.DeptDTO;
import com.sanye.manage.DTO.PersonnelInfoDTO;
import com.sanye.manage.dataobject.DeptInfo;
import com.sanye.manage.dataobject.PersonnelInfo;
import com.sanye.manage.dataobject.UserInfo;
import com.sanye.manage.repository.DeptInfoRepository;
import com.sanye.manage.repository.PersonnelInfoRepository;
import com.sanye.manage.repository.UserInfoRepository;
import com.sanye.manage.service.DeptService;
import com.sanye.manage.service.PersonnelInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-10 上午 10:50
 */
@Service
@Transactional
public class DeptServiceImpl implements DeptService {
    @Autowired
    private PersonnelInfoService personnelInfoService;

    @Autowired
    private DeptInfoRepository deptInfoRepository;

    @Autowired
    private PersonnelInfoRepository personnelInfoRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Override
    public DeptInfo save(DeptInfo deptInfo) {
        return deptInfoRepository.save(deptInfo);
    }


    @Override
    public DeptDTO findByDeptNo(String deptNo) {
        DeptDTO deptDTO = new DeptDTO();

        /** 查询部门基本信息**/
        DeptInfo deptInfo = deptInfoRepository.findByDeptNo(deptNo);
        /** 查询部门领导**/
        PersonnelInfo personnelInfo = new PersonnelInfo();
        if (deptInfo.getPersonnelId()!=null){
            personnelInfo = personnelInfoRepository.findOne(deptInfo.getPersonnelId());
        }else {
            personnelInfo.setId(0);
        }
        /** 查询下级部门**/
        List<DeptInfo> deptInfoList = new ArrayList<>();
        if (deptNo.equals("001")){
            deptInfoList = deptInfoRepository.findByNextNo("001");
        }
        deptDTO.setDeptName(deptInfo.getDeptName());
        deptDTO.setId(deptInfo.getId());
        deptDTO.setDeptNo(deptInfo.getDeptNo());
        deptDTO.setPersonnelInfo(personnelInfo);
        deptDTO.setDeptInfoList(deptInfoList);
        return deptDTO;
    }


    @Override
    public Page<DeptInfo> findAll(Pageable pageable) {

        return deptInfoRepository.findAll(pageable);
    }

    @Override
    public void delete(Integer id) {
        deptInfoRepository.delete(id);
    }

    @Override
    public DeptInfo findOne(Integer id) {
        return deptInfoRepository.findOne(id);
    }

    @Override
    public List<DeptDTO> findByNextNo(String nextNo) {
       List<DeptInfo> deptInfoList =deptInfoRepository.findByNextNo(nextNo);

        /*return deptInfoList.stream().map(e ->
                findByDeptNo(e.getDeptNo())
        ).collect(Collectors.toList());*/
        List<DeptDTO> deptDTOList = new ArrayList<>();

        for (int i=0;i<deptInfoList.size();i++){
            deptDTOList.add(findByDeptNo(deptInfoList.get(i).getDeptNo()));
        }
        return deptDTOList;
    }

    @Override
    public List<DeptInfo> findByUser(String deptNo) {
        DeptInfo deptInfo = deptInfoRepository.findByDeptNo(deptNo);
        if (deptInfo.getNextNo()==null){
            return deptInfoRepository.findAll();
        }else {
            List<DeptInfo> list = new ArrayList<>();
            list.add(deptInfoRepository.findByDeptNo(deptNo));
            return list;
        }

    }

    @Override
    public DeptInfo findOneByDeptNo(String deptNo) {
        return deptInfoRepository.findByDeptNo(deptNo);
    }

    @Override
    public List<UserInfo> findAllUserInfoByDeptId(Integer deptId) {
        DeptInfo deptInfo = deptInfoRepository.findOne(deptId);
        List<PersonnelInfo> personnelInfoList = personnelInfoRepository.findAllByStatusAndShowStatusAndDeptNo(1,1,deptInfo.getDeptNo());
        List<UserInfo> userInfoList = new ArrayList<>();
        if (!personnelInfoList.isEmpty()){
            personnelInfoList.forEach(p->{
                userInfoList.add(userInfoRepository.findOne(p.getUserId()));
            });
        }
        return userInfoList;
    }

    @Override
    public List<DeptInfo> findAll() {
        return deptInfoRepository.findAll();
    }

}
