package com.sanye.manage.service.impl;

import com.sanye.manage.dataobject.FileInfo;
import com.sanye.manage.repository.FileInfoRepository;
import com.sanye.manage.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-18 下午 3:44
 * @Description description
 */
@Service
@Transactional
public class FileInfoServiceImpl implements FileInfoService {

    @Autowired
    private FileInfoRepository repository;

    @Override
    public FileInfo save(FileInfo fileInfo) {
        return repository.save(fileInfo);
    }

    @Override
    public FileInfo findOne(Integer id) {
        return repository.findOne(id);
    }

    @Override
    public FileInfo findByPlatformIdAndMonth(Integer id, String month) {
        return repository.findByPlatformIdAndMonth(id, month);
    }
}
