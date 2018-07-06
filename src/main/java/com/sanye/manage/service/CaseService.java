package com.sanye.manage.service;

import com.sanye.manage.DTO.CaseMasterDTO;
import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.dataobject.ApplicationCase;
import com.sanye.manage.dataobject.BackCase;
import com.sanye.manage.dataobject.CaseMaster;
import com.sanye.manage.form.ApplicationCaseForm;
import org.springframework.data.domain.Pageable;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-10 下午 12:41
 * @Description description
 */
public interface CaseService {

    /**
     * 保存
     * @param applicationCaseForm
     * @return
     */
    void saveApplicationCase(ApplicationCaseForm applicationCaseForm);

    ApplicationCase saveApplicationCase(ApplicationCase applicationCase);

    /**
     * 查询一个
     * @param id
     * @return
     */
    ApplicationCase findOneApplicationCase(Integer id);

    /**
     * 删除
     * @param id
     */
    void deleteApplicationCaseById(Integer id);

    /**
     * 保存
     * @param backCase
     * @return
     */
    BackCase saveBackCase(BackCase backCase);

    /**
     *
     * @param id
     * @return BackCase
     */
    BackCase findOneBackCase(Integer id);

    /**
     *
     * @param caseMaster
     * @return
     */
    CaseMaster saveCaseMaster(CaseMaster caseMaster);

    CaseMaster findOneCaseMaster(Integer id);

    PageDTO<CaseMasterDTO> findAllByCheckUserId(Pageable pageable,Integer checkUserId);

    PageDTO<CaseMasterDTO> findAllByUserId(Pageable pageable,Integer userId);

    PageDTO<CaseMasterDTO> findAll(Pageable pageable);



}
