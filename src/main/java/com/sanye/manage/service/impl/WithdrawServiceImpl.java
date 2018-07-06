package com.sanye.manage.service.impl;

import com.sanye.manage.DTO.AnchorSalaryWithdrawDTO;
import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.dataobject.AnchorSalaryWithdraw;
import com.sanye.manage.dataobject.UserInfo;
import com.sanye.manage.repository.AnchorSalaryWithdrawRepository;
import com.sanye.manage.repository.CheckInfoRepository;
import com.sanye.manage.repository.UserInfoRepository;
import com.sanye.manage.service.WithdrawService;
import com.sanye.manage.utils.SortTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-26 下午 7:50
 * @Description description
 */
@Service
@Transactional
public class WithdrawServiceImpl implements WithdrawService {
    @Autowired
    private AnchorSalaryWithdrawRepository withdrawRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private CheckInfoRepository checkInfoRepository;
    @Override
    public AnchorSalaryWithdraw save(AnchorSalaryWithdraw anchorSalaryWithdraw) {
        return withdrawRepository.save(anchorSalaryWithdraw);
    }

    @Override
    public AnchorSalaryWithdraw findOne(Integer id) {
        return withdrawRepository.findOne(id);
    }

    @Override
    public void delete(Integer id) {
        withdrawRepository.delete(id);
    }

    @Override
    public Page<AnchorSalaryWithdraw> findAllByUserId(Pageable pageable, Integer id) {
        return withdrawRepository.findAllByUserId(pageable, id);
    }

    @Override
    public List<AnchorSalaryWithdraw> findAllByMonthAndResultStatus(String month, Integer resultStatus) {
        return withdrawRepository.findAllByMonthAndResultStatus(month, resultStatus);
    }

    @Override
    public PageDTO<AnchorSalaryWithdrawDTO> findAllByMonthAndCheckStatusAndResultStatus(Pageable pageable, String month, Integer checkStatus, Integer resultStatus) {
        PageDTO<AnchorSalaryWithdrawDTO> pageDTO = new PageDTO<>();
        Page<AnchorSalaryWithdraw> page = withdrawRepository.findAllByMonthAndCheckStatusAndResultStatus(pageable, month, checkStatus, resultStatus);
        pageDTO.setTotalPages(page.getTotalPages());
        List<AnchorSalaryWithdrawDTO> withdrawDTOList = new ArrayList<>();
        if (!page.getContent().isEmpty()){
            page.getContent().forEach(l->{
                AnchorSalaryWithdrawDTO withdrawDTO = new AnchorSalaryWithdrawDTO();
                withdrawDTO.setUserInfo(userInfoRepository.findOne(l.getUserId()));
                withdrawDTO.setAnchorSalaryWithdraw(l);
                withdrawDTO.setCheckInfoList(checkInfoRepository.findAllByApplyIdAndType(l.getId(),"主播工资提现", SortTools.basicSort("asc","orderId")));
                withdrawDTOList.add(withdrawDTO);
            });
        }
        pageDTO.setPageContent(withdrawDTOList);
        return pageDTO;
    }

    @Override
    public BigDecimal countAllByMonthAndResultStatus(String month) {
        List<AnchorSalaryWithdraw> anchorSalaryWithdrawList = withdrawRepository.findAllByMonthAndResultStatus(month,1);
        final BigDecimal[] bigDecimal = {new BigDecimal(0.00)};
        if (anchorSalaryWithdrawList.isEmpty()){
            return bigDecimal[0];
        }
        anchorSalaryWithdrawList.forEach( l ->{
            bigDecimal[0] = bigDecimal[0].add(l.getSalary());
        });
        return bigDecimal[0];
    }
}
