package com.sanye.manage.service.impl;

import com.sanye.manage.dataobject.AnchorInfo;
import com.sanye.manage.dataobject.MOMOAnchorSalary;
import com.sanye.manage.dataobject.PlatformInfo;
import com.sanye.manage.dataobject.UserInfo;
import com.sanye.manage.repository.AnchorInfoRepository;
import com.sanye.manage.repository.MOMOAnchorSalaryRepository;
import com.sanye.manage.repository.PlatformInfoRepository;
import com.sanye.manage.repository.UserInfoRepository;
import com.sanye.manage.service.MOMOAnchorSalaryService;
import com.sanye.manage.utils.Encrypt;
import com.sanye.manage.utils.ExcelUtil;
import com.sanye.manage.utils.GetTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-17 下午 7:30
 * @Description description
 */
@Service
@Transactional
public class MOMOAnchorSalaryServiceImpl implements MOMOAnchorSalaryService {
    @Autowired
    private AnchorInfoRepository anchorInfoRepository;

    @Autowired
    private MOMOAnchorSalaryRepository momoAnchorSalaryRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PlatformInfoRepository platformInfoRepository;

    @Override
    public Map<String, Object> save(List<MOMOAnchorSalary> list) {
        String month = list.get(0).getMonth();
        PlatformInfo platform = new PlatformInfo();
        if (platformInfoRepository.findByName("陌陌")==null){
            platform.setLiveUrl("https://web.immomo.com/live/");
            platform.setName("陌陌");
            platform = platformInfoRepository.save(platform);
        }else {
            platform = platformInfoRepository.findByName("陌陌");
        }
        Integer platformId = platform.getId() ;
        Map<String, Object> map = new HashMap<>();
        map.put("platformId",platformId);
        map.put("month",month);
        if (!list.isEmpty()){
            list.forEach(l -> {
                PlatformInfo platformInfo = platformInfoRepository.findByName("陌陌");

                UserInfo userInfo = new UserInfo();
                l.setName(ExcelUtil.escapeExprSpecialWord(l.getName()));
                l.setBrokerName(ExcelUtil.escapeExprSpecialWord(l.getBrokerName()));

                if (anchorInfoRepository.findByLiveIdAndPlatformId(l.getLiveId(), platformInfo.getId())==null){
                    AnchorInfo anchorInfo =new AnchorInfo();
                    userInfo.setUserType("anchor");
                    userInfo.setShowStatus(1);
                    userInfo.setCreateDate(GetTimeUtil.getTime());
                    userInfo.setUpdateDate(GetTimeUtil.getTime());
                    userInfo.setPhone(l.getLiveId());
                    userInfo.setPassword(Encrypt.md5("888888"));
                    // TODO: 2018/4/17 0017 最低级的权限
                    userInfo.setRoleId(2);
                    userInfo.setNikeName(l.getName());
                    userInfo.setName(l.getName());
                    userInfo.setStatus(1);
                    if (l.getSex().equals("男")){
                        userInfo.setSex(1);
                    }else {
                        userInfo.setSex(0);
                    }
                    UserInfo result = userInfoRepository.save(userInfo);

                    anchorInfo.setPlatformId(platformInfo.getId());
                    anchorInfo.setUserId(result.getId());
                    anchorInfo.setCreateDate(GetTimeUtil.getTime());
                    anchorInfo.setUpdateDate(GetTimeUtil.getTime());
                    anchorInfo.setLiveId(l.getLiveId());
                    anchorInfo.setShowStatus(1);
                    anchorInfo.setName(l.getName());
                    anchorInfo.setLivePlatform(platformInfo.getName());
                    anchorInfo.setNikeName(l.getName());
                    l.setAnchorId(anchorInfoRepository.save(anchorInfo).getId());
                    l.setUserId(result.getId());
                }else {
                    l.setUserId(anchorInfoRepository.findByLiveIdAndPlatformId(l.getLiveId(), platformInfo.getId()).getUserId());
                    l.setAnchorId(anchorInfoRepository.findByLiveIdAndPlatformId(l.getLiveId(), platformInfo.getId()).getId());
                }
                MOMOAnchorSalary momoAnchorSalary = new MOMOAnchorSalary();
                if (momoAnchorSalaryRepository.findByLiveIdAndMonth(l.getLiveId(),l.getMonth())!=null){
                    momoAnchorSalary = momoAnchorSalaryRepository.findByLiveIdAndMonth(l.getLiveId(),l.getMonth());
                    l.setId(momoAnchorSalary.getId());
                    momoAnchorSalaryRepository.save(l);

                }else{
                    momoAnchorSalaryRepository.save(l);
                }


            });
        }

        return map;
    }
    @Override
    public Page<MOMOAnchorSalary> findAllByMonth(Pageable pageable, String month) {
        return momoAnchorSalaryRepository.findAllByMonth(pageable,month);
    }

    @Override
    public Page<MOMOAnchorSalary> findAllByAnchorId(Pageable pageable, Integer anchorId) {
        return momoAnchorSalaryRepository.findAllByAnchorId(pageable,anchorId);
    }

    @Override
    public MOMOAnchorSalary findByAnchorIdAndMonth(Integer anchorId, String month) {
        return momoAnchorSalaryRepository.findByAnchorIdAndMonth(anchorId, month);
    }

    @Override
    public BigDecimal findSalaryByUserId(Integer id) {
        List<MOMOAnchorSalary> momoAnchorSalaryList =  momoAnchorSalaryRepository.findAllByUserId(id);
         BigDecimal[] salary = {new BigDecimal(0)};
        if (!momoAnchorSalaryList.isEmpty()){
            momoAnchorSalaryList.forEach( l->{
                salary[0] = salary[0].add(l.getAfterTax().subtract(l.getTiXian())) ;
            });
        }
        return salary[0];
    }

    @Override
    public Page<MOMOAnchorSalary> findAllByUserId(Pageable pageable, Integer userId) {
        return momoAnchorSalaryRepository.findAllByUserId(pageable, userId);
    }

    @Override
    public MOMOAnchorSalary save(MOMOAnchorSalary momoAnchorSalary) {
        return momoAnchorSalaryRepository.save(momoAnchorSalary);
    }

    @Override
    public MOMOAnchorSalary findOne(Integer id) {
        return momoAnchorSalaryRepository.findOne(id);
    }
}
