package com.sanye.manage.controller;

import com.sanye.manage.VO.ResultVO;
import com.sanye.manage.dataobject.*;
import com.sanye.manage.service.*;
import com.sanye.manage.utils.Encrypt;
import com.sanye.manage.utils.MobileExactUtil;
import com.sanye.manage.utils.ResultVOUtil;
import com.sanye.manage.utils.ShiroGetSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-26 下午 2:18
 * @Description description
 */
@Controller
@RequestMapping("/oa/user")
@RequiresAuthentication
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AnchorService anchorService;
    @Autowired
    private PersonnelInfoService personnelInfoService;
    @Autowired
    private MOMOAnchorSalaryService momoAnchorSalaryService;
    @Autowired
    private PersonnelSalaryService personnelSalaryService;

    @GetMapping("/index")
    public ModelAndView index(Map<String, Object> map) {
        UserInfo userInfo = ShiroGetSession.getUserInfo();
        map.put("pageId", 1);
        map.put("pageTitle", "首页");
        map.put("userInfo", userInfo);
        //工资处理
        if (userInfo.getUserType().equals("anchor")) {
            List<AnchorInfo> anchorInfoList = anchorService.findAllByUserId(userInfo.getId());
            map.put("anchorInfoList", anchorInfoList);
            if (!anchorInfoList.isEmpty()) {
                anchorInfoList.forEach(l -> {
                    // TODO: 2018/4/26 0026  添加平台 添加方法
                    if ("陌陌".equals(l.getLivePlatform())) {
                        BigDecimal MMSalary = momoAnchorSalaryService.findSalaryByUserId(userInfo.getId());
                        if (MMSalary != null) {
                            map.put("MMSalary", MMSalary);
                        }
                    }
                });
            }
        }


        return new ModelAndView("view/userIndex", map);

    }

    @PostMapping("/info")
    @ResponseBody
    public ResultVO<Map<String, Object>> info() {
        Map<String, Object> map = new HashMap<>();
        map.put("user", ShiroGetSession.getUserInfo());
        return ResultVOUtil.success(map);
    }

    @GetMapping("/info")
    public ModelAndView userInfo(Map<String, Object> map) {
        map.put("pageId", 1);
        map.put("pageTitle", "个人资料");
        map.put("userInfo", ShiroGetSession.getUserInfo());
        return new ModelAndView("view/userInfo", map);
    }

    @PostMapping("/avatar/save")
    @ResponseBody
    public ResultVO<Map<String, Object>> saveAvatar(@RequestParam(value = "id") Integer id,
                                                    @RequestParam(value = "avatar") String avatar) {
        UserInfo userInfo = userService.findOne(id);
        userInfo.setAvatar(avatar);
        UserInfo user = userService.save(userInfo);
        ShiroGetSession.setUser(user);
        return ResultVOUtil.success();
    }

    @PostMapping("/info/save")
    @ResponseBody
    public ResultVO<Map<String, Object>> saveUser(@RequestParam(value = "name") String name,
                                                  @RequestParam(value = "QQ") String QQ,
                                                  @RequestParam(value = "phone") String phone,
                                                  @RequestParam(value = "nikeName") String nikeName) {
        UserInfo userInfo = ShiroGetSession.getUserInfo();
        userInfo.setName(name);
        userInfo.setNikeName(nikeName);
        userInfo.setQQ(QQ);
        userInfo.setPhone(phone);
        UserInfo user = userService.save(userInfo);
        ShiroGetSession.setUser(user);
        return ResultVOUtil.success();
    }

    @PostMapping("/bank/save")
    @ResponseBody
    public ResultVO<Map<String, Object>> saveBank(@RequestParam(value = "aliPay") String aliPay,
                                                  @RequestParam(value = "bankCardNumber") String bankCardNumber,
                                                  @RequestParam(value = "bankUserName") String bankUserName,
                                                  @RequestParam(value = "bankType") String bankType) {
        UserInfo userInfo = ShiroGetSession.getUserInfo();
        userInfo.setAliPay(aliPay);
        userInfo.setBankType(bankType);
        userInfo.setBankUserName(bankUserName);
        userInfo.setBankCardNumber(bankCardNumber);
        UserInfo user = userService.save(userInfo);
        ShiroGetSession.setUser(user);
        return ResultVOUtil.success();
    }

    @PostMapping("/password/save")
    @ResponseBody
    public ResultVO<Map<String, Object>> saveUser(@RequestParam(value = "oldPassword") String oldPassword,
                                                  @RequestParam(value = "newPassword") String newPassword) {
        UserInfo userInfo = ShiroGetSession.getUserInfo();
        if (!Encrypt.md5(oldPassword).equals(userInfo.getPassword())) {
            return ResultVOUtil.error(100, "输入的原密码不正确");
        }
        userInfo.setPassword(Encrypt.md5(newPassword));
        UserInfo user = userService.save(userInfo);
        ShiroGetSession.removeUser();
        return ResultVOUtil.success();
    }

    @PostMapping("/phone")
    @ResponseBody
    public ResultVO<Map<String, Object>> findPhone(@RequestParam(value = "phone") String phone) {
        Map<String, Object> map = new HashMap<>();
        if (ShiroGetSession.getUserInfo().getPhone().equals(phone)) {
            map.put("code", 0);
            map.put("message", "无变化");
            return ResultVOUtil.success(map);
        }
        if (MobileExactUtil.isMobileExact(phone)) {
            map = userService.findAllByPhone(phone);
        } else {
            map.put("code", 100);
            map.put("message", "请输入正确的手机号！");
        }
        return ResultVOUtil.success(map);

    }
    @PostMapping("/congruent")
    @ResponseBody
    public ResultVO<Map<String, Object>> congruent(@RequestParam(value = "id") Integer id) {
        UserInfo userInfo = userService.findOne(id);
        if (userInfo.getCongruentImgs()==null){

            return ResultVOUtil.error(100,"合同无图片！");
        }
        return ResultVOUtil.success();

    }





}
