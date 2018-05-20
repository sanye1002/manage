package com.sanye.manage.controller;

import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.DTO.SpendingDTO;
import com.sanye.manage.VO.ResultVO;
import com.sanye.manage.dataobject.CheckInfo;
import com.sanye.manage.dataobject.PersonnelInfo;
import com.sanye.manage.dataobject.SpendingInfo;
import com.sanye.manage.dataobject.UserInfo;
import com.sanye.manage.form.CheckForm;
import com.sanye.manage.form.SpendingForm;
import com.sanye.manage.service.*;
import com.sanye.manage.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-18 下午 6:06
 * @Description description
 */
@Controller
@RequestMapping("/oa/spending")
@RequiresAuthentication
public class SpendingController {
    @Autowired
    private UserService userService;

    @Autowired
    private PersonnelInfoService personnelInfoService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private SpendingService spendingService;

    @Autowired
    private CheckService checkService;

    @GetMapping("/index")
    @RequiresPermissions("spending:add")
    public ModelAndView index(Map<String, Object> map) {
        PersonnelInfo personnelInfo = personnelInfoService.findByUserId(ShiroGetSession.getUserInfo().getId());
        if (!PayCheckUtil.check()){

            return new ModelAndView("redirect:/oa/user/info.html",map);
        }
        map.put("personnelInfo", personnelInfo);
        map.put("pageId", 8);
        map.put("pageTitle", "开支申请");
        return new ModelAndView("view/spendingAdd", map);
    }

    @GetMapping("/img/{id}")
    @RequiresPermissions("spending:add")
    public ModelAndView img(Map<String, Object> map,
                            @PathVariable Integer id) {
        SpendingInfo spendingInfo = spendingService.findOne(id);
        map.put("spendingInfo", spendingInfo);
        map.put("pageId", 8);
        map.put("pageTitle", "图片上传");
        return new ModelAndView("view/spendingImgAdd", map);
    }

    @GetMapping("/list/user")
    @RequiresPermissions("spending:userList")
    public ModelAndView listUser(Map<String, Object> map,
                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PersonnelInfo personnelInfo = personnelInfoService.findByUserId(ShiroGetSession.getUserInfo().getId());
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort());
        Page<SpendingInfo> pageDTO = spendingService.findAllByPersonnelId(pageRequest, personnelInfo.getId());
        map.put("pageId", 7);
        map.put("personnelInfo", personnelInfo);
        map.put("pageTitle", "个人开支列表");
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/spending/list/user.html");
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView("view/spendingListByUser", map);
    }

    @PostMapping("/save")
    @ResponseBody
    public ResultVO<Map<String, Object>> save(@Valid SpendingForm spendingForm,
                                              BindingResult bindingResult) {
        Map<String, Object> map = new HashMap<>();
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(100, bindingResult.getFieldError().getDefaultMessage());
        }
        SpendingInfo spendingInfo = new SpendingInfo();
        BeanUtils.copyProperties(spendingForm, spendingInfo);
        Integer id = spendingService.save(spendingInfo).getId();
        map.put("id", id);
        return ResultVOUtil.success(map);
    }

    @PostMapping("/check")
    @ResponseBody
    public ResultVO<Map<String, Object>> check(@RequestParam(value = "id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        PersonnelInfo personnelInfo = personnelInfoService.findByUserId(ShiroGetSession.getUserInfo().getId());
        UserInfo userInfo = ShiroGetSession.getUserInfo();
        SpendingInfo spendingInfo = spendingService.findOne(id);
        if (spendingInfo.getImg() == null) {
            spendingService.delete(id);
            return ResultVOUtil.error(100, "申请失败！");
        }
        //申请审核
        CheckForm checkForm = new CheckForm();
        checkForm.setApplyId(spendingInfo.getId());
        checkForm.setType("日常开支");
        checkForm.setTitle(spendingInfo.getTitle());
        checkForm.setDescription(spendingInfo.getDescription());
        checkForm.setCheckPersonnelId(personnelInfo.getId());
        checkForm.setSalary(spendingInfo.getSalary());
        checkForm.setApplyPersonnelId(userInfo.getId());
        checkForm.setApplyTime(spendingInfo.getCreateTime());
        checkService.personnelCheckSave(checkForm);
        return ResultVOUtil.success();
    }


    @GetMapping("/list")
    @RequiresPermissions("spendingCheck:list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "12") Integer size,
                             @RequestParam(value = "checkStatus", defaultValue = "1") Integer checkStatus,
                             @RequestParam(value = "resultStatus", defaultValue = "1") Integer resultStatus,
                             @RequestParam(value = "month", defaultValue = "") String month,
                             Map<String, Object> map) {

        if ("".equals(month)) {
            month = GetTimeUtil.getMonth();
        }
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort());
        Page<SpendingInfo> spendingInfoPage = spendingService.findAllByMonthAndCheckStatusAndResultStatus(pageRequest, month, checkStatus, resultStatus);

        Integer pass = spendingService.findAllByMonthAndResultStatus(month, 1).size();
        Integer noPass = spendingService.findAllByMonthAndResultStatus(month, 0).size();
        map.put("pageId", 26);
        map.put("pageTitle", "日常开支列表");
        map.put("month", month);
        map.put("checkStatus", checkStatus);
        map.put("resultStatus", resultStatus);
        map.put("pass", pass);
        map.put("noPass", noPass);
        map.put("pageContent", spendingInfoPage);
        map.put("url", "/oa/spending/list.html");
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView("view/spendingList", map);
    }

    @GetMapping("/checkList")
    @RequiresPermissions("spendingCheck:list")
    public ModelAndView spendingList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "12") Integer size,
                                     @RequestParam(value = "checkStatus", defaultValue = "1") Integer checkStatus,
                                     @RequestParam(value = "resultStatus", defaultValue = "1") Integer resultStatus,
                                     @RequestParam(value = "month", defaultValue = "") String month,
                                     Map<String, Object> map) {
        if ("".equals(month)) {
            month = GetTimeUtil.getMonth();
        }
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort());
        Integer pass = spendingService.findAllByMonthAndResultStatus(month, 1).size();
        Integer noPass = spendingService.findAllByMonthAndResultStatus(month, 0).size();
        PageDTO<SpendingDTO> spendingDTOPageDTO = spendingService.findAllByMonthAndAllStatus(pageRequest, month, checkStatus, resultStatus);
        map.put("pageId", 26);
        map.put("pageTitle", "日常开支列表");
        map.put("month", month);
        map.put("checkStatus", checkStatus);
        map.put("resultStatus", resultStatus);
        map.put("pass", pass);
        map.put("noPass", noPass);
        map.put("pageContent", spendingDTOPageDTO);
        map.put("url", "/oa/spending/checkList.html");
        map.put("size", size);
        map.put("currentPage", page);

        return new ModelAndView("view/spendingCheckList", map);
    }

    @PostMapping("/grants")
    @ResponseBody
    public ResultVO<Map<String, Object>> grants(@RequestParam("id") Integer id,
                                                @RequestParam("resultStatus") Integer resultStatus,
                                                @RequestParam(value = "remark") String remark) {
        SpendingInfo spendingInfo = spendingService.findOne(id);
        if (spendingInfo == null) {
            return ResultVOUtil.error(100, "该日常开支占不可拨款！");
        }
        List<CheckInfo> checkInfoList = checkService.findAllByApplyIdAndType(id, "日常开支", SortTools.basicSort("asc", "orderId"));
        for (CheckInfo c : checkInfoList) {
            if (c.getCheckStatus() == 0) {
                return ResultVOUtil.error(100, "该日常开支还处于审核中！");
            }
        }
        PersonnelInfo personnelInfo = personnelInfoService.findByUserId(ShiroGetSession.getUserInfo().getId());
        spendingInfo.setResultRemark(remark);
        spendingInfo.setResultStatus(resultStatus);
        spendingInfo.setCheckStatus(1);
        spendingInfo.setCheckTime(GetTimeUtil.getTime());
        spendingInfo.setCheckPersonnelId(personnelInfo.getId());
        spendingInfo.setCheckPersonnelName(personnelInfo.getName());
        SpendingInfo resultSpending = spendingService.save(spendingInfo);
        //发送短信
        Map<String,Object> map = new HashMap<>();
        UserInfo messageUser = userService.findOne(personnelInfoService.findById(resultSpending.getPersonnelId()).getUserId());
        String phone = messageUser.getPhone();
        String username = messageUser.getName();
        String type = "日常开支";
        String result;
        if (resultStatus == 1) {
            result = "通过,已拨款";
        } else {
            result = "失败";
        }

        if(SendMessageUtil.sendSalaryTypeMessage(phone, username, type, result)){
            map.put("message","短信发送成功！");
        }else {
            map.put("message","短信发送失败！");
        }
        return ResultVOUtil.success(map);
    }

    @GetMapping("/download/{month}")
    public void download(HttpServletResponse response,
                         @PathVariable(value = "month") String month) {
        List<SpendingInfo> spendingInfoList = spendingService.findAllByMonth(month);
    }
    @PostMapping("/revoke")
    @ResponseBody
    public ResultVO<Map<String, Object>> revoke(@RequestParam("id") Integer id){
        return ResultVOUtil.success(spendingService.revoke(id));
    }

    @PostMapping("/remove/img")
    @ResponseBody
    public ResultVO<Map<String, Object>> removeImg(@RequestParam("id") Integer id){
        SpendingInfo spendingInfo = spendingService.findOne(id);
        spendingInfo.setImg("");
        spendingService.save(spendingInfo);
        return ResultVOUtil.success();
    }



}
