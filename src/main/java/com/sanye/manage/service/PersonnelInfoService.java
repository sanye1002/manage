package com.sanye.manage.service;

import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.DTO.PersonnelInfoDTO;
import com.sanye.manage.dataobject.PersonnelInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-09 下午 7:38
 * 工作人员service
 */
public interface PersonnelInfoService {

    /**
     * 保存
     * @param personnelInfo
     * @return
     */
    PersonnelInfo save(PersonnelInfo personnelInfo);

    /**
     * 删除
     * @param id
     */
    void delete(Integer id);

    /**
     * id查找
     * @param id
     * @return
     */
    PersonnelInfoDTO findOne(Integer id);

    /**
     * id查找
     * @param id
     * @return
     */
    PersonnelInfo findById(Integer id);

    /**
     *
     * @param pageable
     * @param status
     * @return
     */
    PageDTO<PersonnelInfoDTO> findAllByShowStatusAndStatus(Pageable pageable, Integer status, String deptNo);

    /**
     * 查找手机
     * @param phone
     * @return
     */
    List<PersonnelInfo> findByPhone(String phone);

    List<PersonnelInfo> findAll();

    /**
     * 修改部门编号
     * @param oldDeptNo
     * @param newDeptNo
     */
    void updateByDeptNo(String oldDeptNo,String newDeptNo);

    PersonnelInfo findByUserId(Integer id);

}
