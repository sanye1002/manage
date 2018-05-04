package com.sanye.manage.repository;

import com.sanye.manage.dataobject.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-18 下午 3:22
 * @Description description
 */
public interface FileInfoRepository extends JpaRepository<FileInfo,Integer> {

    FileInfo findByPlatformIdAndMonth(Integer id,String month);
}
