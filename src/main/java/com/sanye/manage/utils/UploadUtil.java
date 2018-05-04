package com.sanye.manage.utils;

import com.sanye.manage.dataobject.MOMOAnchorSalary;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-03-16 下午 3:39
 * @description: 单文件与多文件的上传
 */
@Slf4j
public class UploadUtil {
    public static List<String> uploadFiles(MultipartFile[] file, String path, String type) {

        File dir = new File(path);
        //判断目录是否存在
        if (!dir.exists()) {
            // 如果不存在，自动创建
            dir.mkdirs();

        }
        List<String> srcList = new ArrayList<>();
        //遍历文件数组,上传
        for (int i = 0; i < file.length; i++) {
            if (file[i] != null) {
                //调用上传 判断
                //得到集合
                if (uploadFile(file[i], path, type) != null) {
                    srcList.add(uploadFile(file[i], path, type));
                }

            }

        }
        return srcList;


    }

    public static String uploadFile(MultipartFile file, String path, String type) {
        File dir = new File(path);
        //判断目录是否存在
        log.info("【文件上传】 path={}", path);
        log.info("【文件上传】 dir.exists()={}", dir.exists());
        //判断目录是否存在
        if (!dir.exists()) {
            // 如果不存在，自动创建
            dir.mkdirs();
        }
        //上传文件名
        String fileName = KeyUtil.genUniqueKey() + file.getOriginalFilename();
        //保存文件
        File saveFile = new File(path + File.separator + fileName);

        try {
            file.transferTo(saveFile);
            log.info("fileName={}", fileName);
            return "/read/img/" + type + "/" + fileName;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Map<String, Object> importMoMoExcel(MultipartFile file, String path, String type) {
        File dir = new File(path);
        List<Map<String, Object>> returnList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
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
            log.info("fileName={}", fileName);
            resultPath = saveFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Workbook wb = null;
        List<MOMOAnchorSalary> arrayList = new ArrayList();
        try {
            if (ExcelUtil.isExcel2007(file.getOriginalFilename())) {
                wb = new XSSFWorkbook(new FileInputStream(new File(resultPath)));
            } else {
                wb = new HSSFWorkbook(new FileInputStream(new File(resultPath)));
            }
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }

        Sheet sheet = wb.getSheetAt(0);//获取第一张表
        for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
            Row row = sheet.getRow(i);//获取索引为i的行，以0开始
            String month = POIUtil.getValue(row.getCell(0));//获取第i行的索引为0的单元格数据
            if (month == null) {
                break;
            }
            String nickname = row.getCell(1).getStringCellValue();

            String liveId = POIUtil.getStringNum(row.getCell(2));

            String sex = row.getCell(3).getStringCellValue();

            Integer grade = Integer.valueOf(new DecimalFormat("#").format(row.getCell(4).getNumericCellValue()));

            String brokerName = POIUtil.getValue(row.getCell(5));

            String brokerId = POIUtil.getStringNum(row.getCell(6));

            BigDecimal lianMaiMoBi = POIUtil.getBigDecimal(row.getCell(7));

            Integer feiLianMaiMoBi = POIUtil.getIntNum(row.getCell(8));
            Integer allMoBi = POIUtil.getIntNum(row.getCell(9));
            String billingMethod = POIUtil.getValue(row.getCell(10));
            BigDecimal boZhuFenChen = POIUtil.getBigDecimal(row.getCell(11));
            BigDecimal gongHuiFenChen = POIUtil.getBigDecimal(row.getCell(12));
            BigDecimal boZhuJiangLi = POIUtil.getBigDecimal(row.getCell(13));
            BigDecimal qiTaJiangLi = POIUtil.getBigDecimal(row.getCell(14));
            BigDecimal ruHuiQian = POIUtil.getBigDecimal(row.getCell(15));
            BigDecimal geSui = POIUtil.getBigDecimal(row.getCell(16));
            BigDecimal tiXian = POIUtil.getBigDecimal(row.getCell(17));
            BigDecimal fengKongKouKuan = POIUtil.getBigDecimal(row.getCell(18));
            BigDecimal billingSalary = POIUtil.getBigDecimal(row.getCell(19));
            BigDecimal realSalary = POIUtil.getBigDecimal(row.getCell(20));
            BigDecimal beforeTax = POIUtil.getBigDecimal(row.getCell(21));
            BigDecimal afterTax = POIUtil.getBigDecimal(row.getCell(22));


            //添加数据
            MOMOAnchorSalary momoAnchorSalary = new MOMOAnchorSalary();

            momoAnchorSalary.setMonth(month);
            momoAnchorSalary.setName(nickname);
            momoAnchorSalary.setLiveId(liveId);
            momoAnchorSalary.setSex(sex);
            momoAnchorSalary.setGrade(grade);
            momoAnchorSalary.setBrokerName(brokerName);
            momoAnchorSalary.setBrokerId(brokerId);
            momoAnchorSalary.setLianMaiMoBi(lianMaiMoBi);
            momoAnchorSalary.setFeiLianMaiMoBi(feiLianMaiMoBi);
            momoAnchorSalary.setAllMoBi(allMoBi);
            momoAnchorSalary.setBillingMethod(billingMethod);
            momoAnchorSalary.setBoZhuFenChen(boZhuFenChen);
            momoAnchorSalary.setGongHuiFenChen(gongHuiFenChen);
            momoAnchorSalary.setBoZhuJiangLi(boZhuJiangLi);
            momoAnchorSalary.setQiTaJiangLi(qiTaJiangLi);
            momoAnchorSalary.setRuHuiQian(ruHuiQian);
            momoAnchorSalary.setGeSui(geSui);
            momoAnchorSalary.setTiXian(tiXian);
            momoAnchorSalary.setFengKongKouKuan(fengKongKouKuan);
            momoAnchorSalary.setBillingSalary(billingSalary);
            momoAnchorSalary.setRealSalary(realSalary);
            momoAnchorSalary.setBeforeTax(beforeTax);
            momoAnchorSalary.setAfterTax(afterTax);

            arrayList.add(momoAnchorSalary);

        }
        try {
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("arrayList", arrayList);
        map.put("path", resultPath);

        return map;
    }

}
