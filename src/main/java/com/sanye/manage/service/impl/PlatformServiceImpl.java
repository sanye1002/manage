package com.sanye.manage.service.impl;

import com.sanye.manage.dataobject.PlatformInfo;
import com.sanye.manage.repository.PlatformInfoRepository;
import com.sanye.manage.service.PlatformService;
import com.sanye.manage.utils.SortTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-12 下午 12:37
 */
@Service
@Transactional
public class PlatformServiceImpl implements PlatformService {

    @Autowired
    private PlatformInfoRepository platformInfoRepository;

    @Override
    public PlatformInfo save(PlatformInfo platformInfo) {
        return platformInfoRepository.save(platformInfo);
    }

    @Override
    public Page<PlatformInfo> findAll(Pageable pageable) {
        return platformInfoRepository.findAll(pageable);
    }

    @Override
    public List<PlatformInfo> findAll() {
        return platformInfoRepository.findAll(SortTools.basicSort());
    }

    @Override
    public Map<String, Object> delete(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("code",0);
        map.put("message","删除成功！");
        platformInfoRepository.delete(id);
        return map;
    }

    @Override
    public PlatformInfo findOne(Integer id) {
        return platformInfoRepository.findOne(id);
    }
}
