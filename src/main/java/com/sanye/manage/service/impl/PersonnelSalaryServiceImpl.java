package com.sanye.manage.service.impl;

import com.sanye.manage.dataobject.PersonnelInfo;
import com.sanye.manage.dataobject.PersonnelSalary;
import com.sanye.manage.repository.PersonnelInfoRepository;
import com.sanye.manage.repository.PersonnelSalaryRepository;
import com.sanye.manage.service.PersonnelSalaryService;
import com.sanye.manage.utils.ExcelUtil;
import com.sanye.manage.utils.KeyUtil;
import com.sanye.manage.utils.POIUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-23 下午 4:19
 * @Description description
 */
@Service
@Transactional
public class PersonnelSalaryServiceImpl implements PersonnelSalaryService {

    @Autowired
    private PersonnelSalaryRepository salaryRepository;

    @Autowired
    private PersonnelInfoRepository personnelInfoRepository;
    @Override
    public PersonnelSalary save(PersonnelSalary personnelSalary) {
        return salaryRepository.save(personnelSalary);
    }

    @Override
    public Page<PersonnelSalary> findAllByMonth(Pageable pageable, String month) {
        return salaryRepository.findAllByMonth(pageable, month);
    }

    @Override
    public PersonnelSalary findOne(Integer id) {
        return salaryRepository.findOne(id);
    }

    @Override
    public Map<String, Object> saveByExcel(List<PersonnelSalary> personnelSalaryList) {
        personnelSalaryList.forEach( l ->{
                salaryRepository.save(l);
        });
        return null;
    }

    @Override
    public Map<String,Object> importSalaryExcel(MultipartFile file, String path) {
        File dir = new File(path);
        List<Map<String,Object>> returnList= new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        if (!dir.exists()) {
            // 如果不存在，自动创建
            dir.mkdirs();
        }
        String fileName = KeyUtil.genUniqueKey() + ".xlsx";
        //保存文件
        File saveFile = new File(path + File.separator + fileName);
        String resultPath = null;
        try {
            file.transferTo(saveFile);
            resultPath = saveFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        map.put("path",resultPath);
        Workbook wb = null;
        List<PersonnelSalary> personnelSalaryList = new ArrayList<>();
        try {
            if (ExcelUtil.isExcel2007(file.getOriginalFilename())) {
                wb = new XSSFWorkbook(new FileInputStream(new File(resultPath)));
            } else {
                wb = new HSSFWorkbook(new FileInputStream(new File(resultPath)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sheet sheet = wb.getSheetAt(0);//获取第一张表

        for (int i=1;i<sheet.getLastRowNum()+1;i++){
            Row row = sheet.getRow(i);//获取索引为i的行，以0开始
            String month = POIUtil.getValue(row.getCell(0));//获取第i行的索引为0的单元格数据
            if (month == null) {
                break;
            }
            String personnelName =  POIUtil.getValue(row.getCell(1));
            String phone =  POIUtil.getValue(row.getCell(2));
            BigDecimal baseSalary = POIUtil.getBigDecimal(row.getCell(3));
            BigDecimal JIXIAO = POIUtil.getBigDecimal(row.getCell(4));
            BigDecimal BUTIE= POIUtil.getBigDecimal(row.getCell(5));
            BigDecimal QUANQING= POIUtil.getBigDecimal(row.getCell(6));
            BigDecimal JIANGJING= POIUtil.getBigDecimal(row.getCell(7));
            BigDecimal KAOQINGKOUKUAN= POIUtil.getBigDecimal(row.getCell(8));
            BigDecimal YINGFAHEJI= POIUtil.getBigDecimal(row.getCell(9));
            BigDecimal SHEBAO= POIUtil.getBigDecimal(row.getCell(10));
            BigDecimal GESUI= POIUtil.getBigDecimal(row.getCell(11));
            BigDecimal YINGKOUXIAOJI= POIUtil.getBigDecimal(row.getCell(12));
            BigDecimal realSalary= POIUtil.getBigDecimal(row.getCell(13));
            String remark=POIUtil.getValue(row.getCell(14));
            if (phone == null) {
                break;
            }
            PersonnelSalary personnelSalary = salaryRepository.findByPhoneAndMonth(phone,month);
            if (personnelSalary==null){
                personnelSalary = new PersonnelSalary();
            }

            personnelSalary.setMonth(month);
            personnelSalary.setPersonnelName(personnelName);
            PersonnelInfo personnelInfo =personnelInfoRepository.findByPhone(phone);
            if (personnelInfo!=null){
                personnelSalary.setPersonnelId(personnelInfo.getId());
            }
            personnelSalary.setPhone(phone);
            personnelSalary.setBaseSalary(baseSalary);
            personnelSalary.setJIXIAO(JIXIAO);
            personnelSalary.setBUTIE(BUTIE);
            personnelSalary.setQUANQING(QUANQING);
            personnelSalary.setJIANGJING(JIANGJING);
            personnelSalary.setKAOQINGKOUKUAN(KAOQINGKOUKUAN);
            personnelSalary.setYINGFAHEJI(YINGFAHEJI);
            personnelSalary.setSHEBAO(SHEBAO);
            personnelSalary.setGESUI(GESUI);
            personnelSalary.setYINGKOUXIAOJI(YINGKOUXIAOJI);
            personnelSalary.setRealSalary(realSalary);
            personnelSalary.setRemark(remark);
            personnelSalaryList.add(personnelSalary);
        }
        map.put("list",personnelSalaryList);
        map.put("month",personnelSalaryList.get(0).getMonth());
        return map;
    }

    @Override
    public PersonnelSalary findByPhoneAndMonth(String phone, String month) {
        return salaryRepository.findByPhoneAndMonth(phone, month);
    }

    @Override
    public Page<PersonnelSalary> findAllByPersonnelId(Pageable pageable, Integer id) {
        return salaryRepository.findAllByPersonnelId(pageable, personnelInfoRepository.findByUserId(id).getId());
    }
}
