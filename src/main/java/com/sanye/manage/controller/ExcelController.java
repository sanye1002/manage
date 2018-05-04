package com.sanye.manage.controller;

import com.sanye.manage.VO.ResultVO;
import com.sanye.manage.config.UploadConfig;
import com.sanye.manage.dataobject.FileInfo;
import com.sanye.manage.dataobject.MOMOAnchorSalary;
import com.sanye.manage.exception.WebException;
import com.sanye.manage.service.FileInfoService;
import com.sanye.manage.service.MOMOAnchorSalaryService;
import com.sanye.manage.utils.ResultVOUtil;
import com.sanye.manage.utils.UploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-17 下午 4:37
 */
@Controller
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private UploadConfig uploadConfig;
    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private MOMOAnchorSalaryService salaryService;
    @PostMapping("/import/{type}/{platform}")
    @ResponseBody
    public ResultVO<Map<String,Object>> importExcel(HttpServletRequest request,
                                                    @PathVariable Integer platform,
                                                    @PathVariable String type,
                                                    MultipartFile file){
        Map<String, Object> map = new HashMap<>();
        String path = uploadConfig.getPath()+ File.separator+type;
        Map<String, Object> util = UploadUtil.importMoMoExcel(file, path,type);
        List<MOMOAnchorSalary> list = (List<MOMOAnchorSalary>) util.get("arrayList");
        String filePath = (String) util.get("path");
        map = salaryService.save(list);
        String month = (String) map.get("month");
        Integer platformId = (Integer) map.get("platformId");
        FileInfo fileInfo = new FileInfo();
        if (fileInfoService.findByPlatformIdAndMonth(platformId,month)!=null){
            fileInfo = fileInfoService.findByPlatformIdAndMonth(platformId,month);
        }
        fileInfo.setMonth(month);
        fileInfo.setPlatformId(platformId);
        fileInfo.setPath(filePath);
        fileInfoService.save(fileInfo);
        return ResultVOUtil.success();
    }
    @GetMapping("/export/{month}/{id}")
    @ResponseBody
    public ResponseEntity<FileSystemResource> exportExcel(  @PathVariable String month,
                                                            @PathVariable Integer id) {

        FileInfo fileInfo = fileInfoService.findByPlatformIdAndMonth(id, month);
        if (fileInfo==null){
            throw new WebException(400,"文件不存在");
        }
        File file = new File(fileInfo.getPath());
        if (file == null) {
            throw new WebException(400,"文件不存在");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + ".xlsx");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new FileSystemResource(file));
    }
    @GetMapping("/downLoad/{type}")
    @ResponseBody
    public ResponseEntity<FileSystemResource> downLoad(  @PathVariable String type) {
        File file = null;
        if ("momo".equals(type)){
            file = new File("D:\\rysj\\upload\\momo\\momo.xlsx");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + ".xlsx");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new FileSystemResource(file));
    }

}
