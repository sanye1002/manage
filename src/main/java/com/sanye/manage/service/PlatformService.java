package com.sanye.manage.service;

import com.sanye.manage.dataobject.PlatformInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.Id;
import java.util.List;
import java.util.Map;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-12 下午 12:24
 */
public interface PlatformService {
    /**
     *
     * @param platformInfo
     * @return
     */
    PlatformInfo save(PlatformInfo platformInfo);

    /**
     * 查询
     * @param pageable
     * @return
     */
    Page<PlatformInfo> findAll(Pageable pageable);

    /**
     *
     * @return
     */
    List<PlatformInfo> findAll();

    /**
     * 删除
     * @param id
     */
    Map<String,Object> delete(Integer id);

    /**
     * id查找
     * @param id
     * @return
     */
    PlatformInfo findOne(Integer id);
}
