package com.sanye.manage.controller;

import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.DTO.PersonnelSalaryDTO;
import com.sanye.manage.VO.ResultVO;
import com.sanye.manage.config.UploadConfig;
import com.sanye.manage.dataobject.FileInfo;
import com.sanye.manage.dataobject.MOMOAnchorSalary;
import com.sanye.manage.dataobject.PersonnelSalary;
import com.sanye.manage.dataobject.UserInfo;
import com.sanye.manage.exception.WebException;
import com.sanye.manage.service.FileInfoService;
import com.sanye.manage.service.PersonnelInfoService;
import com.sanye.manage.service.PersonnelSalaryService;
import com.sanye.manage.service.UserService;
import com.sanye.manage.utils.GetTimeUtil;
import com.sanye.manage.utils.ResultVOUtil;
import com.sanye.manage.utils.SendMessageUtil;
import com.sanye.manage.utils.ShiroGetSession;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-23 下午 6:03
 * @Description description
 */
@Controller
@RequestMapping("/oa/personnelSalary")

public class PersonnelSalaryController {
    @Autowired
    private PersonnelSalaryService personnelSalaryService;
    @Autowired
    private UploadConfig uploadConfig;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private PersonnelInfoService personnelInfoService;

    @GetMapping("/list")
    @RequiresPermissions("personnelSalary:list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "15") Integer size,
                             @RequestParam(value = "month", defaultValue = "") String month,
                             Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        if ("".equals(month)) {
            month = GetTimeUtil.getMonth();
        }
        PageDTO<PersonnelSalaryDTO> personnelSalaryPage = personnelSalaryService.findAllDTOByMonth(pageRequest, month);
        map.put("pageId", 30);
        map.put("pageTitle", "工作人员工资列表");
        map.put("month", month);
        map.put("pageContent", personnelSalaryPage);
        map.put("url", "/oa/personnelSalary/list.html");
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView("view/personnelSalaryList");
    }
    @GetMapping("/list/user")
    @RequiresPermissions("personnelSalary:tag")
    public ModelAndView listUser(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "15") Integer size,
                             Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size);

        Page<PersonnelSalary> personnelSalaryPage = personnelSalaryService.findAllByPersonnelId(pageRequest,ShiroGetSession.getUserInfo().getId());
        map.put("pageId", 37);
        map.put("pageTitle", "工作个人人员工资");
        map.put("pageContent", personnelSalaryPage);
        map.put("url", "/oa/personnelSalary/list/user.html");
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView("view/personnelSalaryListUser");
    }

    @PostMapping("/save")
    @ResponseBody
    public ResultVO<Map<String, Object>> importSalaryExcel(HttpServletRequest request,
                                                           MultipartFile file) {

        String path = uploadConfig.getPath()+ File.separator+"personnelSalary";
        Map<String, Object> map =  personnelSalaryService.importSalaryExcel(file,path);
        String resultPath = (String) map.get("path");
        String month = (String) map.get("month");
        List<PersonnelSalary> list = (List<PersonnelSalary>) map.get("list");
        personnelSalaryService.saveByExcel(list);
        FileInfo fileInfo = new FileInfo();
        fileInfoService.save(fileInfo);
        if (fileInfoService.findByPlatformIdAndMonth(10,month)!=null){
            fileInfo = fileInfoService.findByPlatformIdAndMonth(10,month);
        }
        fileInfo.setMonth(month);
        fileInfo.setPlatformId(100);
        fileInfo.setPath(resultPath);
        fileInfoService.save(fileInfo);
        return ResultVOUtil.success();
    }

    @GetMapping("/downLoad/{month}/{id}")
    public ResponseEntity<FileSystemResource> exportExcel(@PathVariable String month,
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
        headers.add("Content-Disposition", "attachment; filename=" + "personnelSalary"+month + ".xlsx");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new FileSystemResource(file));
    }
   @PostMapping("/confirm")
   @ResponseBody
   public ResultVO<Map<String,Object>> confirm(@RequestParam Integer id){
        PersonnelSalary personnelSalary = personnelSalaryService.findOne(id);
        if (personnelSalary==null){
            ResultVOUtil.error(100,"操作出错请重新登录！");
        }
        personnelSalary.setConfirmStatus(1);
        personnelSalaryService.save(personnelSalary);
        return ResultVOUtil.success();
   }
   @PostMapping("/grants")
   @ResponseBody
   public ResultVO<Map<String,Object>> grants(@RequestParam Integer id){
        PersonnelSalary personnelSalary = personnelSalaryService.findOne(id);
        if (personnelSalary==null){
            ResultVOUtil.error(100,"操作出错！");
        }
        personnelSalary.setGrantsStatus(1);
        PersonnelSalary resultSalary = personnelSalaryService.save(personnelSalary);
       //发送短信
       Map<String,Object> map = new HashMap<>();
       UserInfo messageUser = userService.findOne(personnelInfoService.findById(resultSalary.getPersonnelId()).getUserId());
       String phone = messageUser.getPhone();
       String username = messageUser.getName();
       String type = resultSalary.getMonth()+"月";
       String salary= String.valueOf(resultSalary.getRealSalary());
       if(SendMessageUtil.sendSalaryMessage(phone, username, type, salary)){
           map.put("message","短信发送成功！");
       }else {
           map.put("message","短信发送失败！");
       }
        return ResultVOUtil.success(map);
   }




}
