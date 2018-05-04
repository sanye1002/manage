package com.sanye.manage.service.impl;

import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.DTO.SpendingDTO;
import com.sanye.manage.dataobject.SpendingInfo;
import com.sanye.manage.repository.CheckInfoRepository;
import com.sanye.manage.repository.PersonnelInfoRepository;
import com.sanye.manage.repository.SpendingInfoRepository;
import com.sanye.manage.repository.UserInfoRepository;
import com.sanye.manage.service.SpendingService;
import com.sanye.manage.utils.SortTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-18 下午 5:57
 * @Description description
 */
@Service
@Transactional
public class SpendingServiceImpl implements SpendingService {

    @Autowired
    private SpendingInfoRepository repository;

    @Autowired
    private CheckInfoRepository checkInfoRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PersonnelInfoRepository personnelInfoRepository;
    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public SpendingInfo findOne(Integer id) {
        return repository.findOne(id);
    }

    @Override
    public SpendingInfo save(SpendingInfo spendingInfo) {
        return repository.save(spendingInfo);
    }

    @Override
    public List<SpendingInfo> findAllByPersonnelId(Integer id) {
        return repository.findAllByPersonnelId(id);
    }

    @Override
    public Page<SpendingInfo> findAllByPersonnelId(Pageable pageable, Integer id) {
        return repository.findAllByPersonnelId(pageable, id);
    }

    @Override
    public List<SpendingInfo> findAllByMonthAndResultStatus(String month, Integer resultStatus) {
        return repository.findAllByMonthAndResultStatus(month, resultStatus);
    }

    @Override
    public Page<SpendingInfo> findAllByMonthAndResultStatus(Pageable pageable, String month, Integer resultStatus) {
        return repository.findAllByMonthAndResultStatus(pageable, month, resultStatus);
    }

    @Override
    public Page<SpendingInfo> findAllByMonthAndCheckStatusAndResultStatus(Pageable pageable, String month, Integer checkStatus, Integer resultStatus) {
        return repository.findAllByMonthAndCheckStatusAndResultStatus(pageable, month, checkStatus, resultStatus);
    }

    @Override
    public Page<SpendingInfo> findAllByMonth(Pageable pageable, String month) {
        return findAllByMonth(pageable,month);
    }

    @Override
    public PageDTO<SpendingDTO> findAllByMonthAndAllStatus(Pageable pageable, String month, Integer checkStatus, Integer resultStatus) {
        PageDTO pageDTO = new PageDTO();
        Page<SpendingInfo> spendingInfoPage = repository.findAllByMonthAndCheckStatusAndResultStatus(pageable, month, checkStatus, resultStatus);
        pageDTO.setTotalPages(spendingInfoPage.getTotalPages());
        List<SpendingDTO> spendingDTOList = new ArrayList<>();
        if (!spendingInfoPage.getContent().isEmpty()){

            spendingInfoPage.getContent().forEach( l->{
                SpendingDTO spendingDTO = new SpendingDTO();
                spendingDTO.setSpendingInfo(l);
                spendingDTO.setCheckInfoList(checkInfoRepository.findAllByApplyIdAndType(l.getId(),"日常开支", SortTools.basicSort("asc","orderId")));
                spendingDTO.setUserInfo(userInfoRepository.findOne(personnelInfoRepository.findOne(l.getPersonnelId()).getUserId()));
                spendingDTOList.add(spendingDTO);
            });
        }
        pageDTO.setPageContent(spendingDTOList);
        return pageDTO;
    }

    @Override
    public List<SpendingInfo> findAllByMonth(String month) {
        return repository.findAllByMonth(month);
    }

}
