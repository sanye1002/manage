package com.sanye.manage.service;

import com.sanye.manage.dataobject.FileInfo;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-18 下午 3:39
 * @Description description
 */
public interface FileInfoService {
    /**
     *
     * @param fileInfo
     * @return
     */
    FileInfo save(FileInfo fileInfo);

    /**
     *
     * @param id
     * @return
     */
    FileInfo findOne(Integer id);

    /**
     *
     * @param id
     * @param month
     * @return
     */
    FileInfo findByPlatformIdAndMonth(Integer id,String month);


}
