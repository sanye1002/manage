package com.sanye.manage.service.impl;

import com.sanye.manage.DTO.DeptDTO;
import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.DTO.PersonnelInfoDTO;
import com.sanye.manage.dataobject.PersonnelInfo;
import com.sanye.manage.dataobject.UserInfo;
import com.sanye.manage.enums.ResultEnum;
import com.sanye.manage.exception.WebException;
import com.sanye.manage.repository.PersonnelInfoRepository;
import com.sanye.manage.service.DeptService;
import com.sanye.manage.service.PersonnelInfoService;
import com.sanye.manage.service.RolePermissionService;
import com.sanye.manage.service.UserService;
import com.sanye.manage.utils.GetTimeUtil;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
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
 * @create 2018-04-10 上午 10:25
 */
@Service
@Transactional
public class PersonnelInfoServiceImpl implements PersonnelInfoService {
    @Autowired
    private PersonnelInfoRepository personnelInfoRepository;
    @Autowired
    private DeptService deptService;
    @Autowired
    private UserService userService;

    @Autowired
    private RolePermissionService permissionService;
    @Override
    public PersonnelInfo save(PersonnelInfo personnelInfo) {

        return personnelInfoRepository.save(personnelInfo);
    }

    @Override
    public void delete(Integer id) {
        PersonnelInfo personnelInfo = personnelInfoRepository.findOne(id);
        UserInfo userInfo = userService.findOne(personnelInfo.getUserId());
        personnelInfo.setShowStatus(0);
        userInfo.setShowStatus(0);
        save(personnelInfo);
        userService.save(userInfo);
    }

    @Override
    public PersonnelInfoDTO findOne(Integer id) {
        PersonnelInfoDTO personnelInfoDTO = new PersonnelInfoDTO();
        PersonnelInfo personnelInfo = personnelInfoRepository.findOne(id);
        if (personnelInfo!=null){
            personnelInfoDTO.setPersonnelInfo(personnelInfo);
            personnelInfoDTO.setId(personnelInfo.getId());
            UserInfo userInfo = userService.findOne(personnelInfo.getUserId());
            personnelInfoDTO.setUserInfo(userInfo);
            personnelInfoDTO.setDeptDTO(deptService.findByDeptNo(personnelInfo.getDeptNo()));
            personnelInfoDTO.setRole(permissionService.findRoleById(userService.findOne(personnelInfo.getUserId()).getRoleId()));
        }

        return personnelInfoDTO;
    }

    @Override
    public PersonnelInfo findById(Integer id) {
        return personnelInfoRepository.findOne(id);
    }


    @Override
    public PageDTO<PersonnelInfoDTO> findAllByShowStatusAndStatus(Pageable pageable, Integer status, String deptNo) {

        /** 查询该用户的下级部门**/
        if (deptNo == null) {
            throw new WebException(ResultEnum.PARAM_NULL);
        }

        List<PersonnelInfoDTO> infoDTOList = new ArrayList<>();
        if (deptNo.equals("001")) {
            Page<PersonnelInfo> personnelInfoPage = personnelInfoRepository.findAllByShowStatusAndStatus(pageable, status, 1);
            if (!personnelInfoPage.getContent().isEmpty()){
                infoDTOList = personnelInfoPage.getContent().stream().map(
                        e -> findOne(e.getId())
                ).collect(Collectors.toList());
            }
            PageDTO<PersonnelInfoDTO> pageDTO = new PageDTO<>(personnelInfoPage.getTotalPages(),infoDTOList);
            return pageDTO;
        }else {
            Page<PersonnelInfo> personnelInfoPage = personnelInfoRepository.findAllByShowStatusAndStatusAndDeptNo(pageable, status, 1, deptNo);
            if (!personnelInfoPage.getContent().isEmpty()) {

                infoDTOList = personnelInfoPage
                        .getContent().stream().map(
                                e -> findOne(e.getId())
                        ).collect(Collectors.toList());
            }
            PageDTO<PersonnelInfoDTO> pageDTO = new PageDTO<>(personnelInfoPage.getTotalPages(),infoDTOList);
            return pageDTO;
        }


    }

    @Override
    public List<PersonnelInfo> findByPhone(String phone) {
        return personnelInfoRepository.findAllByPhone(phone);
    }

    @Override
    public List<PersonnelInfo> findAll() {
        return personnelInfoRepository.findAll();
    }

    @Override
    public void updateByDeptNo(String oldDeptNo, String newDeptNo) {
        List<PersonnelInfo> personnelInfoList = personnelInfoRepository.findByDeptNo(oldDeptNo);
        if (!personnelInfoList.isEmpty()){
            for (int i=0;i<personnelInfoList.size();i++){
                personnelInfoList.get(i).setUpdateDate(GetTimeUtil.getTime());
                personnelInfoList.get(i).setDeptNo(newDeptNo);
                personnelInfoRepository.save(personnelInfoList.get(i));
            }
        }
    }

    @Override
    public PersonnelInfo findByUserId(Integer id) {
        return personnelInfoRepository.findByUserId(id);
    }


}
