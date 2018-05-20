package com.sanye.manage.service.impl;

import com.sanye.manage.DTO.AnchorSalaryAdvanceDTO;
import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.dataobject.AnchorSalaryAdvance;
import com.sanye.manage.repository.AnchorSalaryAdvanceRepository;
import com.sanye.manage.repository.CheckInfoRepository;
import com.sanye.manage.repository.UserInfoRepository;
import com.sanye.manage.service.AnchorSalaryAdvanceService;
import com.sanye.manage.utils.SortTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-25 下午 4:29
 * @Description description
 */
@Service
@Transactional
public class AnchorSalaryAdvanceServiceImpl implements AnchorSalaryAdvanceService {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private AnchorSalaryAdvanceRepository anchorSalaryAdvanceRepository;

    @Autowired
    private CheckInfoRepository checkInfoRepository;
    @Override
    public AnchorSalaryAdvance save(AnchorSalaryAdvance anchorSalaryAdvance) {
        return anchorSalaryAdvanceRepository.save(anchorSalaryAdvance);
    }

    @Override
    public AnchorSalaryAdvance findOne(Integer id) {
        return anchorSalaryAdvanceRepository.findOne(id);
    }

    @Override
    public void delete(Integer id) {
        anchorSalaryAdvanceRepository.delete(id);
    }

    @Override
    public Page<AnchorSalaryAdvance> findAllByUserId(Pageable pageable, Integer id) {
        return anchorSalaryAdvanceRepository.findAllByUserId(pageable, id);
    }

    @Override
    public List<AnchorSalaryAdvance> findAllByMonthAndResultStatus(String month, Integer resultStatus) {
        return anchorSalaryAdvanceRepository.findAllByMonthAndResultStatus(month, resultStatus);
    }

    @Override
    public PageDTO<AnchorSalaryAdvanceDTO> findAllByMonthAndCheckStatusAndResultStatus(Pageable pageable, String month, Integer checkStatus, Integer resultStatus) {
        Page<AnchorSalaryAdvance> salaryAdvancePage = anchorSalaryAdvanceRepository.findAllByMonthAndCheckStatusAndResultStatus(pageable, month, checkStatus, resultStatus);
        PageDTO<AnchorSalaryAdvanceDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(salaryAdvancePage.getTotalPages());
        List<AnchorSalaryAdvanceDTO> list = new ArrayList<>();
        if (!salaryAdvancePage.getContent().isEmpty()){
            salaryAdvancePage.getContent().forEach(l->{
                AnchorSalaryAdvanceDTO anchorSalaryAdvanceDTO = new AnchorSalaryAdvanceDTO();
                anchorSalaryAdvanceDTO.setAnchorSalaryAdvance(l);
                anchorSalaryAdvanceDTO.setUserInfo(userInfoRepository.findOne(l.getUserId()));
                anchorSalaryAdvanceDTO.setCheckInfoList(checkInfoRepository.findAllByApplyIdAndType(l.getId(),"主播工资预支", SortTools.basicSort("asc","orderId")));
                list.add(anchorSalaryAdvanceDTO);
            });
        }
        pageDTO.setPageContent(list);
        return pageDTO;
    }

    @Override
    public Map<String, Object> revoke(Integer id) {
        Integer count = checkInfoRepository.deleteAllByTypeAndApplyId("主播工资预支",id);
        Map<String, Object> map  = new HashMap<>();
        System.out.println("删除的条数="+count);
        if (count==0){
            map.put("code",100);
            map.put("message","无审核记录");
        }else {
            map.put("code",0);
            map.put("message","撤回【"+count+"】条记录");
        }
        anchorSalaryAdvanceRepository.delete(id);
        return map;
    }
}
