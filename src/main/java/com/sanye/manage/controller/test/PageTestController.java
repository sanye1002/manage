package com.sanye.manage.controller.test;

import com.sanye.manage.dataobject.*;
import com.sanye.manage.service.DeptService;
import com.sanye.manage.service.PersonnelInfoService;
import com.sanye.manage.service.RolePermissionService;
import com.sanye.manage.service.UserService;
import com.sanye.manage.utils.Encrypt;
import com.sanye.manage.utils.GetTimeUtil;
import com.sanye.manage.utils.SendMessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-20 下午 2:20
 * @Description description
 */
@Controller
@RequestMapping("/")
@Slf4j
public class PageTestController {
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private PersonnelInfoService personnelInfoService;
    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public ModelAndView show(Map<String, Object> map) {
        map.put("pageId", 3);
        map.put("pageTitle", "审核列表");
        //
        // SendMessageUtil.sendSalaryTypeMessage("18145032533","三爷","工资预支","已拨款");
        // SendMessageUtil.sendNoticeMessage("18145032533","popo","平台线上短信通知不用回复");
        return new ModelAndView("view/test", map);
    }

    /*@GetMapping("/init")*/
    public ModelAndView init(Map<String, Object> map) {
        //权限初始化

        if (rolePermissionService.findAllPermission().isEmpty()) {
            rolePermissionService.savePermission(new Permission("首页标签", "userIndex:tag", "基本权限"));
            rolePermissionService.savePermission(new Permission("系统设置标签", "system:tag", "系统设置主标签"));
            rolePermissionService.savePermission(new Permission("权限标签", "permission:tag", "权限标签"));
            rolePermissionService.savePermission(new Permission("角色标签", "role:tag", "角色标签"));
            rolePermissionService.savePermission(new Permission("权限添加", "permission:add", "系统权限添加"));
            rolePermissionService.savePermission(new Permission("权限列表", "permission:list", "系统权限列表"));
            rolePermissionService.savePermission(new Permission("角色添加", "role:add", "系统角色添加"));
            rolePermissionService.savePermission(new Permission("角色列表", "role:list", "系统角色列表"));
        }
        //角色初始化
        List<Permission> permissionList = rolePermissionService.findAllPermission();
        if (rolePermissionService.findAllRole().isEmpty()) {
            Role role = new Role();
            role.setName("系统初始化");
            role.setDescription("系统初始化");
            Role result1 = rolePermissionService.saveRole(role);
            List<Integer> integerList1 = permissionList.stream().map(e -> e.getId()).collect(Collectors.toList());
            rolePermissionService.save(result1.getId(), integerList1);
            Role role2 = new Role();
            role2.setName("主播用户");
            role2.setDescription("主播人员角色权限");
            Role result2 = rolePermissionService.saveRole(role2);
            List<Integer> list = new ArrayList<>();
            list.add(1);
            rolePermissionService.save(result2.getId(), list);

        }
        //部门
        if (deptService.findOneByDeptNo("001")==null){
            DeptInfo deptInfo = new DeptInfo();
            deptInfo.setDeptName("总经办");
            deptInfo.setDeptNo("001");
            DeptInfo resultDept = deptService.save(deptInfo);
            //人员添加
            if (userService.findByPhone("18188888888")==null){
                UserInfo userInfo = new UserInfo();
                userInfo.setPhone("18188888888");
                userInfo.setName("初始化账号");
                userInfo.setNikeName("初始化账号");
                userInfo.setPassword(Encrypt.md5("18188888888"));
                userInfo.setAvatar("/layui/images/model.jpg");
                userInfo.setAge(20);
                userInfo.setRoleId(1);
                userInfo.setUserType("personnel");
                userInfo.setCreateDate(GetTimeUtil.getTime());
                userInfo.setUpdateDate(GetTimeUtil.getTime());
                UserInfo resultUser = userService.save(userInfo);
                PersonnelInfo personnelInfo = new PersonnelInfo();
                BeanUtils.copyProperties(userInfo,personnelInfo);
                personnelInfo.setDeptNo("001");
                personnelInfoService.save(personnelInfo);
            }
        }

        map.put("message", "初始化成功！");
        map.put("url", "/login.html");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("momoUnion")
    public ModelAndView mmUnion(Map<String, Object> map){
        map.put("pageId",35);
        map.put("pageTitle", "陌陌公会管理");
        return new ModelAndView("view/MMUnion", map);
    }
    @GetMapping("KKUnion")
    public ModelAndView KKUnion(Map<String, Object> map){
        map.put("pageId",36);
        map.put("pageTitle", "KK公会管理");
        return new ModelAndView("view/KKUnion", map);
    }
    public void download(HttpServletRequest request, HttpServletResponse response){

        try {
            String downloadFilename = "图片.zip";//文件的名称
            downloadFilename = URLEncoder.encode(downloadFilename, "UTF-8");//转换中文否则可能会产生乱码
            response.setContentType("application/octet-stream");// 指明response的返回对象是文件流
            response.setHeader("Content-Disposition", "attachment;filename=" + downloadFilename);// 设置在下载框默认显示的文件名
            ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
            String[] files = new String[]{"http://xxxx/xx.jpg","http://xxx/xx.jpg"};
            for (int i=0;i<files.length;i++) {
                //URL url = new URL(files[i]);
                zos.putNextEntry(new ZipEntry(i+".jpg"));
                FileInputStream fis = new FileInputStream(new File(files[i]));
                //InputStream fis = url.openConnection().getInputStream();
                byte[] buffer = new byte[1024];
                int r = 0;
                while ((r = fis.read(buffer)) != -1) {
                    zos.write(buffer, 0, r);
                }
                fis.close();
            }
            zos.flush();
            zos.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
