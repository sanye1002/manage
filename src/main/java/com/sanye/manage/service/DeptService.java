package com.sanye.manage.service;

import com.sanye.manage.DTO.DeptDTO;
import com.sanye.manage.dataobject.DeptInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-10 上午 10:47
 */
public interface DeptService {

    /**
     * 保存
     * @param deptInfo
     * @return
     */
    DeptInfo save(DeptInfo deptInfo);
    /**
     *
     * @param deptNo
     * @return
     */
    DeptDTO findByDeptNo(String deptNo);

    /**
     *
     * @param pageable
     * @return
     */
    Page<DeptInfo> findAll(Pageable pageable);

    /**
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * @param id
     * @return
     */
    DeptInfo findOne(Integer id);

    /**
     *
     * @param nextNo
     * @return
     */
    List<DeptDTO> findByNextNo(String nextNo);

    /**
     *
     * @param deptNo
     * @return
     */
    List<DeptInfo> findByUser(String deptNo);

    DeptInfo findOneByDeptNo(String deptNo);
}
