package com.sanye.manage.controller;

import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.DTO.PersonnelSalaryAdvanceDTO;
import com.sanye.manage.DTO.SpendingDTO;
import com.sanye.manage.VO.ResultVO;
import com.sanye.manage.dataobject.*;
import com.sanye.manage.form.CheckForm;
import com.sanye.manage.form.PersonnelSalaryAdvanceForm;
import com.sanye.manage.form.SpendingForm;
import com.sanye.manage.service.*;
import com.sanye.manage.utils.*;
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

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-24 下午 4:12
 * @Description description
 */
@Controller
@RequestMapping("/oa/personnelSalaryAdvance")
public class PersonnelSalaryAdvanceController {
    @Autowired
    private UserService userService;

    @Autowired
    private PersonnelInfoService personnelInfoService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private CheckService checkService;

    @Autowired
    private PersonnelSalaryAdvanceService salaryAdvanceService;

    /**
     *
     * @param map
     * @return
     */
    @GetMapping("/index")
    @RequiresPermissions("personnelSalaryAdvanc:add")
    public ModelAndView index(Map<String, Object> map) {
        PersonnelInfo personnelInfo = personnelInfoService.findByUserId(ShiroGetSession.getUserInfo().getId());
        map.put("personnelInfo", personnelInfo);
        map.put("pageId", 3);
        map.put("pageTitle", "员工工资预支申请");
        if (!PayCheckUtil.check()){
            return new ModelAndView("redirect:/oa/user/info.html");
        }
        return new ModelAndView("view/personnelSalaryAdvanceAdd", map);
    }

    /**
     *
     * @param map
     * @param id
     * @return
     */
    @GetMapping("/img/{id}")
    @RequiresPermissions("personnelSalaryAdvanc:add")
    public ModelAndView img(Map<String, Object> map,
                            @PathVariable Integer id) {
        PersonnelSalaryAdvance personnelSalaryAdvance = salaryAdvanceService.findOne(id);
        map.put("personnelSalaryAdvance", personnelSalaryAdvance);
        map.put("pageId", 3);
        map.put("pageTitle", "图片上传");
        return new ModelAndView("view/personnelSalaryAdvanceImgAdd", map);
    }

    /**
     *
     * @param personnelSalaryAdvanceForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public ResultVO<Map<String, Object>> save(@Valid PersonnelSalaryAdvanceForm personnelSalaryAdvanceForm,
                                              BindingResult bindingResult) {
        Map<String, Object> map = new HashMap<>();
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(100, bindingResult.getFieldError().getDefaultMessage());
        }
        PersonnelSalaryAdvance personnelSalaryAdvance = new PersonnelSalaryAdvance();
        BeanUtils.copyProperties(personnelSalaryAdvanceForm, personnelSalaryAdvance);
        Integer id = salaryAdvanceService.save(personnelSalaryAdvance).getId();
        map.put("id", id);
        return ResultVOUtil.success(map);
    }

    /**
     *
     * @param id
     * @return
     */
    @PostMapping("/check")
    @ResponseBody
    public ResultVO<Map<String, Object>> check(@RequestParam(value = "id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        PersonnelInfo personnelInfo = personnelInfoService.findByUserId(ShiroGetSession.getUserInfo().getId());
        UserInfo userInfo = ShiroGetSession.getUserInfo();

        PersonnelSalaryAdvance personnelSalaryAdvance = salaryAdvanceService.findOne(id);
        if (personnelSalaryAdvance.getImg() == null) {
            salaryAdvanceService.delete(id);
            return ResultVOUtil.error(100, "申请失败！");
        }
        //申请审核
        CheckForm checkForm = new CheckForm();
        checkForm.setApplyId(personnelSalaryAdvance.getId());
        checkForm.setType("工作人员工资预支");
        checkForm.setTitle(personnelSalaryAdvance.getTitle());
        checkForm.setDescription(personnelSalaryAdvance.getDescription());
        checkForm.setSalary(personnelSalaryAdvance.getSalary());
        checkForm.setApplyPersonnelId(userInfo.getId());
        checkForm.setApplyPersonnelName(userInfo.getName());
        checkForm.setApplyTime(personnelSalaryAdvance.getCreateTime());
        checkService.personnelCheckSave(checkForm);
        return ResultVOUtil.success();
    }

    /**
     *
     * @param page
     * @param size
     * @param checkStatus
     * @param resultStatus
     * @param month
     * @param map
     * @return
     */
    @GetMapping("/checkList")
    @RequiresPermissions("personnelAdvanceCheck:list")
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
        Integer pass = salaryAdvanceService.findAllByMonthAndResultStatus(month, 1).size();
        Integer noPass = salaryAdvanceService.findAllByMonthAndResultStatus(month, 0).size();
        PageDTO<PersonnelSalaryAdvanceDTO> pageDTO = salaryAdvanceService.findAllByMonthAndCheckStatusAndResultStatus(pageRequest, month, checkStatus, resultStatus);
        map.put("pageId", 29);
        map.put("pageTitle", "工作人员工资预支列表");
        map.put("month", month);
        map.put("checkStatus", checkStatus);
        map.put("resultStatus", resultStatus);
        map.put("pass", pass);
        map.put("noPass", noPass);
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/personnelSalaryAdvance/checkList.html");
        map.put("size", size);
        map.put("currentPage", page);

        return new ModelAndView("view/personnelSalaryAdvanceCheckList", map);
    }

    /**
     *
     * @param id
     * @param resultStatus
     * @param remark
     * @return
     */
    @PostMapping("/grants")
    @ResponseBody
    public ResultVO<Map<String, Object>> grants(@RequestParam("id") Integer id,
                                                @RequestParam("resultStatus") Integer resultStatus,
                                                @RequestParam(value = "remark") String remark) {
        PersonnelSalaryAdvance personnelSalaryAdvance = salaryAdvanceService.findOne(id);
        if (personnelSalaryAdvance == null) {
            return ResultVOUtil.error(100, "该工资预支占不可拨款！");
        }
        List<CheckInfo> checkInfoList = checkService.findAllByApplyIdAndType(id, "工作人员工资预支", SortTools.basicSort("asc", "orderId"));

        for (CheckInfo c : checkInfoList) {
            if (c.getCheckStatus() == 0) {
                return ResultVOUtil.error(100, "该工资预支还处于审核中！");
            }
            if (resultStatus==1){
                if (c.getResultStatus() == 0) {
                    return ResultVOUtil.error(100, "该工资预支审核不通过！请撤销！");
                }
            }
        }
        PersonnelInfo personnelInfo = personnelInfoService.findByUserId(ShiroGetSession.getUserInfo().getId());
        personnelSalaryAdvance.setResultRemark(remark);
        personnelSalaryAdvance.setResultStatus(resultStatus);
        personnelSalaryAdvance.setCheckStatus(1);
        personnelSalaryAdvance.setCheckTime(GetTimeUtil.getTime());
        personnelSalaryAdvance.setCheckPersonnelId(personnelInfo.getId());
        personnelSalaryAdvance.setCheckPersonnelName(personnelInfo.getName());
        personnelSalaryAdvance.setBackStatus(0);
        PersonnelSalaryAdvance resultSalary = salaryAdvanceService.save(personnelSalaryAdvance);
        //发送短信
        Map<String,Object> map = new HashMap<>();
        UserInfo messageUser = userService.findOne(personnelInfoService.findById(resultSalary.getPersonnelId()).getUserId());
        String phone = messageUser.getPhone();
        String username = messageUser.getName();
        String type = "工资预支";
        String result;
        if (resultStatus == 1) {
            result = "通过,已拨款";
        } else {
            result = "审核失败";
        }

        if(SendMessageUtil.sendSalaryTypeMessage(phone, username, type, result)){
            map.put("message","短信发送成功！");
        }else {
            map.put("message","短信发送失败！");
        }
        return ResultVOUtil.success(map);
    }

    /**
     *
     * @param id
     * @return
     */
    @PostMapping("/back")
    @ResponseBody
    public ResultVO<Map<String, Object>> back(@RequestParam("id") Integer id){
        PersonnelSalaryAdvance personnelSalaryAdvance = salaryAdvanceService.findOne(id);
        if (personnelSalaryAdvance == null) {
            return ResultVOUtil.error(100, "该日常开支占不可拨款！");
        }
        personnelSalaryAdvance.setBackStatus(1);
        personnelSalaryAdvance.setBackTime(GetTimeUtil.getTime());
        salaryAdvanceService.save(personnelSalaryAdvance);
        return ResultVOUtil.success();
    }

    /**
     *
     * @param map
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list/user")
    @RequiresPermissions("personnelSalaryAdvanc:userList")
    public ModelAndView listUser(Map<String, Object> map,
                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PersonnelInfo personnelInfo = personnelInfoService.findByUserId(ShiroGetSession.getUserInfo().getId());
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort());
        Page<PersonnelSalaryAdvance> pageDTO = salaryAdvanceService.findAllByPersonnelId(pageRequest, personnelInfo.getId());
        map.put("pageId", 2);
        map.put("personnelInfo", personnelInfo);
        map.put("pageTitle", "个人预支记录");
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/personnelSalaryAdvance/list/user.html");
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView("view/personnelSalaryAdvanceListByUser", map);
    }
    @PostMapping("/revoke")
    @ResponseBody
    public ResultVO<Map<String, Object>> revoke(@RequestParam("id") Integer id){
        return ResultVOUtil.success(salaryAdvanceService.revoke(id));
    }

    @PostMapping("/remove/img")
    @ResponseBody
    public ResultVO<Map<String, Object>> removeImg(@RequestParam("id") Integer id){
        PersonnelSalaryAdvance personnelSalaryAdvance = salaryAdvanceService.findOne(id);
        personnelSalaryAdvance.setImg("");
        salaryAdvanceService.save(personnelSalaryAdvance);
        return ResultVOUtil.success();
    }

}
