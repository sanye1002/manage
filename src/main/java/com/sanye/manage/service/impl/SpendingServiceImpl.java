package com.sanye.manage.service.impl;

import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.DTO.SpendingDTO;
import com.sanye.manage.dataobject.SpendingInfo;
import com.sanye.manage.repository.CheckInfoRepository;
import com.sanye.manage.repository.PersonnelInfoRepository;
import com.sanye.manage.repository.SpendingInfoRepository;
import com.sanye.manage.repository.UserInfoRepository;
import com.sanye.manage.service.SpendingService;
import com.sanye.manage.utils.ExcelUtil;
import com.sanye.manage.utils.SortTools;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        return findAllByMonth(pageable, month);
    }

    @Override
    public PageDTO<SpendingDTO> findAllByMonthAndAllStatus(Pageable pageable, String month, Integer checkStatus, Integer resultStatus) {
        PageDTO pageDTO = new PageDTO();
        Page<SpendingInfo> spendingInfoPage = repository.findAllByMonthAndCheckStatusAndResultStatus(pageable, month, checkStatus, resultStatus);
        pageDTO.setTotalPages(spendingInfoPage.getTotalPages());
        List<SpendingDTO> spendingDTOList = new ArrayList<>();
        if (!spendingInfoPage.getContent().isEmpty()) {

            spendingInfoPage.getContent().forEach(l -> {
                SpendingDTO spendingDTO = new SpendingDTO();
                spendingDTO.setSpendingInfo(l);
                spendingDTO.setCheckInfoList(checkInfoRepository.findAllByApplyIdAndType(l.getId(), "日常开支", SortTools.basicSort("asc", "orderId")));
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

    @Override
    public Map<String, Object> revoke(Integer id) {
        Integer count = checkInfoRepository.deleteAllByTypeAndApplyId("日常开支", id);
        Map<String, Object> map = new HashMap<>();
        System.out.println("删除的条数=" + count);
        if (count == 0) {
            map.put("code", 100);
            map.put("message", "无审核记录");
        } else {
            map.put("code", 0);
            map.put("message", "撤回【" + count + "】条记录");
        }
        repository.delete(id);
        return map;
    }

    @Override
    public Integer findAllByMonthAndResultStatusAndCheckStatus(String month, Integer r, Integer c) {
        List<SpendingInfo> list = repository.findAllByMonthAndResultStatusAndCheckStatus(month, r, c);
        if (list.isEmpty()) {
            return 0;
        }
        return list.size();
    }

    @Override
    public BigDecimal countAllByMonthAndResultStatus(String month) {
        List<SpendingInfo> spendingInfoList = repository.findAllByMonthAndResultStatusAndCheckStatus(month, 1, 1);
        final BigDecimal[] bigDecimal = {new BigDecimal(0.00)};
        if (spendingInfoList.isEmpty()) {
            return bigDecimal[0];
        }
        spendingInfoList.forEach(l -> {
            bigDecimal[0] = bigDecimal[0].add(l.getSalary());
        });
        return bigDecimal[0];
    }

    @Override
    public HSSFWorkbook downloadToExcelByMonthAndResultStatus(String month, Integer status) {
        List<SpendingInfo> spendingInfoList = repository.findAllByMonthAndResultStatusAndCheckStatus(month, status, 1);
        List<SpendingDTO> spendingDTOList = new ArrayList<>();
        //第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        //第二步，在webbook中添加一个sheet，对应Excel文件中的 sheet
        HSSFSheet sheet = wb.createSheet(month + "月日常开支表");
        //第三步，在sheet中添加表头第0行，注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);
        //第四步，创建单元格样式：居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        //第五步，创建表头单元格，并设置样式
        String[] header = {"月份", "申请人", "申请时间", "标题", "内容", "金额", "审核流程结果"};
        HSSFCell cell = ExcelUtil.getHSSFCell(row, header);
        cell.setCellStyle(style);
        if (!spendingInfoList.isEmpty()) {
            spendingInfoList.forEach(l -> {
                SpendingDTO spendingDTO = new SpendingDTO();
                spendingDTO.setSpendingInfo(l);
                spendingDTO.setCheckInfoList(checkInfoRepository.findAllByApplyIdAndType(l.getId(), "日常开支", SortTools.basicSort("asc", "orderId")));
                spendingDTO.setUserInfo(userInfoRepository.findOne(personnelInfoRepository.findOne(l.getPersonnelId()).getUserId()));
                spendingDTOList.add(spendingDTO);
            });
            for (int i = 0; i < spendingDTOList.size(); i++) {
                row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(spendingDTOList.get(i).getSpendingInfo().getMonth());
                row.createCell(1).setCellValue(spendingDTOList.get(i).getUserInfo().getName());
                row.createCell(2).setCellValue(spendingDTOList.get(i).getSpendingInfo().getCreateTime());
                row.createCell(3).setCellValue(spendingDTOList.get(i).getSpendingInfo().getTitle());
                row.createCell(4).setCellValue(spendingDTOList.get(i).getSpendingInfo().getDescription());
                row.createCell(5).setCellValue(String.valueOf(spendingDTOList.get(i).getSpendingInfo().getSalary()));
                String check = "";
                for (int c = 0; c < spendingDTOList.get(i).getCheckInfoList().size(); c++) {
                    if (c+1>c){
                        check = check+"审核人@"+spendingDTOList.get(i).getCheckInfoList().get(c).getCheckPersonnelName()+"--审核时间--"+spendingDTOList.get(i).getCheckInfoList().get(c).getCheckTime()+"||";
                    }else {
                        check = check+"审核人@"+spendingDTOList.get(i).getCheckInfoList().get(c).getCheckPersonnelName()+"--审核时间--"+spendingDTOList.get(i).getCheckInfoList().get(c).getCheckTime();
                    }

                }
                row.createCell(6).setCellValue(check);
            }
            return wb;

        }
        return null;
    }

}
