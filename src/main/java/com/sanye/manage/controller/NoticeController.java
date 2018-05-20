package com.sanye.manage.controller;

import com.sanye.manage.DTO.NoticeUserDTO;
import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.VO.ResultVO;
import com.sanye.manage.dataobject.*;
import com.sanye.manage.form.SendMessageForm;
import com.sanye.manage.form.SendNoticeForm;
import com.sanye.manage.service.*;
import com.sanye.manage.utils.ResultVOUtil;
import com.sanye.manage.utils.ShiroGetSession;
import com.sanye.manage.utils.SortTools;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-13 下午 2:33
 * @Description description
 */
@Controller
@RequestMapping("/oa/notice")

public class NoticeController {

    @Autowired
    private NoticeServer noticeServer;

    @Autowired
    private PlatformService platformService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private PersonnelInfoService personnelInfoService;

    @Autowired
    private UserService userService;

    /**
     * @param map
     * @return
     */
    @GetMapping("/message/index")
    @RequiresPermissions("noticeMessage:add")
    public ModelAndView messageIndex(Map<String, Object> map) {
        map.put("pageId", 44);
        map.put("pageTitle", "通知短信发送");
        return new ModelAndView("view/sendNoticeMessage", map);
    }

    @PostMapping("/findArea")
    @ResponseBody
    public ResultVO<Map<String, Object>> findArea(@RequestParam String value) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(value);
        if ("personnel".equals(value)) {
            List<DeptInfo> deptInfoList = deptService.findAll();
            map.put("area", deptInfoList);
        } else {
            List<PlatformInfo> list = platformService.findAll();
            map.put("area", list);
        }
        return ResultVOUtil.success(map);
    }


    @PostMapping("/findUser")
    @ResponseBody
    public ResultVO<Map<String, Object>> findUser(@RequestParam String value, @RequestParam Integer id) {
        Map<String, Object> map = new HashMap<>();
        if ("personnel".equals(value)) {
            List<UserInfo> userInfoList = deptService.findAllUserInfoByDeptId(id);
            map.put("user", userInfoList);
            if (userInfoList.isEmpty()) {
                return ResultVOUtil.error(100, "无数据");
            }
        } else {
            List<UserInfo> userInfoList = platformService.findAllUserInfoByPlatformId(id);
            map.put("user", userInfoList);
            if (userInfoList.isEmpty()) {
                return ResultVOUtil.error(100, "无数据");
            }
        }
        return ResultVOUtil.success(map);
    }


    @PostMapping("/message/send")
    @ResponseBody
    public ResultVO<Map<String, Object>> sendMessage(@Valid SendMessageForm sendMessageForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResultVOUtil.error(100, bindingResult.getFieldError().getDefaultMessage());
        }
        List<UserInfo> userInfoList = new ArrayList<>();
        if (sendMessageForm.getUid() == 0) {
            if ("personnel".equals(sendMessageForm.getValue())) {
                if (sendMessageForm.getAid() == 0) {
                    userInfoList = userService.findAllForNoticeByUserType(sendMessageForm.getValue());
                    if (userInfoList==null) {
                        return ResultVOUtil.error(100, "权限不足，请正确选择！");
                    }
                } else {
                    userInfoList = deptService.findAllUserInfoByDeptId(sendMessageForm.getAid());
                    if (userInfoList.isEmpty()) {
                        return ResultVOUtil.error(100, "该部门/平台无人员！");
                    }
                }

            } else if ("anchor".equals(sendMessageForm.getValue())) {
                if (sendMessageForm.getAid() == 0) {
                    userInfoList = userService.findAllForNoticeByUserType(sendMessageForm.getValue());
                    if (userInfoList==null) {
                        return ResultVOUtil.error(100, "权限不足，请正确选择！");
                    }
                } else {
                    userInfoList = platformService.findAllUserInfoByPlatformId(sendMessageForm.getAid());
                    if (userInfoList.isEmpty()) {
                        return ResultVOUtil.error(100, "该部门/平台无人员！");
                    }
                }
            } else {
                userInfoList = userService.findAllForNoticeByUserType("all");
                if (userInfoList==null) {
                    return ResultVOUtil.error(100, "权限不足，请正确选择！");
                }
            }
        } else {
            userInfoList.add(userService.findOne(sendMessageForm.getUid()));
            if (userInfoList.isEmpty()) {
                return ResultVOUtil.error(100, "该部门/平台无人员！");
            }
        }
        Map<String, Object> map = new HashMap<>();
        map = noticeServer.sendNoticeMessage(userInfoList, sendMessageForm.getContent());
        return ResultVOUtil.success(map);
    }

    @RequiresPermissions("noticeMessage:list")
    @GetMapping("/message/list")
    public ModelAndView InfoList(Map<String, Object> map,
                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "10") Integer size,
                                 @RequestParam(value = "status", defaultValue = "1") Integer status) {
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort());
        Page<NoticeMessage> noticeMessagePage = noticeServer.findAllNoticeMessageByStatus(status, pageRequest);
        map.put("pageTitle", "短信通知列表");
        map.put("pageId", 45);
        map.put("pageContent", noticeMessagePage);
        map.put("size", size);
        map.put("status", status);
        map.put("currentPage", page);
        map.put("url", "/oa/notice/message/list.html");
        return new ModelAndView("view/noticeMessageList", map);
    }



    @RequiresAuthentication
    @GetMapping("/system/index")
    @RequiresPermissions("noticeSystem:add")
    public ModelAndView noticeIndex(Map<String, Object> map) {
        map.put("pageId", 46);
        map.put("pageTitle", "系统通知发送");
        return new ModelAndView("view/sendNotice", map);
    }
    @PostMapping("/system/send")
    @ResponseBody
    public ResultVO<Map<String, Object>> sendNotice(@Valid SendNoticeForm sendNoticeForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResultVOUtil.error(100, bindingResult.getFieldError().getDefaultMessage());
        }
        List<UserInfo> userInfoList = new ArrayList<>();
        if (sendNoticeForm.getUid() == 0) {
            if ("personnel".equals(sendNoticeForm.getValue())) {
                if (sendNoticeForm.getAid() == 0) {
                    userInfoList = userService.findAllForNoticeByUserType(sendNoticeForm.getValue());
                    if (userInfoList==null) {
                        return ResultVOUtil.error(100, "权限不足，请正确选择！");
                    }
                } else {
                    userInfoList = deptService.findAllUserInfoByDeptId(sendNoticeForm.getAid());
                    if (userInfoList.isEmpty()) {
                        return ResultVOUtil.error(100, "该部门无人员！");
                    }
                }
            } else if ("anchor".equals(sendNoticeForm.getValue())) {
                if (sendNoticeForm.getAid() == 0) {
                    userInfoList = userService.findAllForNoticeByUserType(sendNoticeForm.getValue());
                    if (userInfoList==null) {
                        return ResultVOUtil.error(100, "权限不足，请正确选择！");
                    }
                } else {
                    userInfoList = platformService.findAllUserInfoByPlatformId(sendNoticeForm.getAid());
                    if (userInfoList.isEmpty()) {
                        return ResultVOUtil.error(100, "该平台无人员！");
                    }
                }
            } else {
                userInfoList = userService.findAllForNoticeByUserType("all");
                if (userInfoList==null) {
                    return ResultVOUtil.error(100, "权限不足，请正确选择！");
                }
            }
        } else {
            userInfoList.add(userService.findOne(sendNoticeForm.getUid()));
            if (userInfoList.isEmpty()) {
                return ResultVOUtil.error(100, "该部门/平台无人员！");
            }
        }
        Map<String, Object> map = new HashMap<>();
        map = noticeServer.sendNoticeToUser(userInfoList, sendNoticeForm.getContent(),sendNoticeForm.getTitle());
        return ResultVOUtil.success(map);
    }
    @RequiresAuthentication
    @RequiresPermissions("noticeUser:tag")
    @GetMapping("/system/list/user")
    public ModelAndView systemListUser(Map<String, Object> map,
                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "10") Integer size,
                                 @RequestParam(value = "status", defaultValue = "0") Integer status) {
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort());
        PageDTO<NoticeUserDTO> dtoPageDTO = noticeServer.findAllNoticeUserDTOByUserIdAndReadStatus(ShiroGetSession.getUserInfo().getId(),status, pageRequest);
        map.put("pageTitle", "系统通知");
        map.put("pageId", 43);
        map.put("pageContent", dtoPageDTO);
        map.put("size", size);
        map.put("status", status);
        map.put("currentPage", page);
        map.put("url", "/oa/notice/system/list/user.html");
        return new ModelAndView("view/noticeListUser", map);
    }
    @RequiresAuthentication
    @GetMapping("/system/read")
    @RequiresPermissions("noticeUser:tag")
    public ModelAndView showNotice(Map<String, Object> map,@RequestParam Integer id) {
        NoticeUserDTO noticeUserDTO =noticeServer.findOneNoticeUserDTO(id);
        map.put("pageId", 43);
        map.put("pageTitle", "系统通知详情");
        map.put("noticeUserDTO", noticeUserDTO);
        return new ModelAndView("view/noticeInfo", map);
    }
    @RequiresAuthentication
    @PostMapping("/delete/user")
    @ResponseBody
    public ResultVO<Map<String, Object>> deleteUser(@RequestParam Integer id){
        noticeServer.deleteNoticeUserInfo(id);
        return ResultVOUtil.success();
    }
    @RequiresAuthentication
    @PostMapping("/delete/recording")
    @ResponseBody
    public ResultVO<Map<String, Object>> deleteRecording(@RequestParam Integer id){
        noticeServer.deleteNoticeDetail(id);
        return ResultVOUtil.success();
    }
    @RequiresAuthentication
    @RequiresPermissions("noticeSystem:list")
    @GetMapping("/system/list")
    public ModelAndView List(Map<String, Object> map,
                                   @RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort());
        Page<NoticeDetail> noticeDetailPage = noticeServer.findAllByUserId(ShiroGetSession.getUserInfo().getId(),pageRequest);
        map.put("pageTitle", "系统通知发送列表");
        map.put("pageId", 47);
        map.put("pageContent", noticeDetailPage);
        map.put("size", size);
        map.put("currentPage", page);
        map.put("url", "/oa/notice/system/list.html");
        return new ModelAndView("view/noticeList", map);
    }

    @PostMapping("/find/new/recording")
    @ResponseBody
    public ResultVO<Map<String, Object>> findNewReCording(){
        Map<String, Object> map = new HashMap<>();
        List<NoticeUserDTO> noticeUserDTOList = noticeServer.findAllNewNoticeUserDTO();
        if (noticeUserDTOList.isEmpty()){
            map.put("code",1);
        }else {
            map.put("code",0);
        }
        map.put("notice",noticeUserDTOList);
        return ResultVOUtil.success(map);
    }

}
