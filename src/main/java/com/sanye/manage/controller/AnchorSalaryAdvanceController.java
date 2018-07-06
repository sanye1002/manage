package com.sanye.manage.controller;

import com.sanye.manage.DTO.AnchorSalaryAdvanceDTO;
import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.DTO.PersonnelSalaryAdvanceDTO;
import com.sanye.manage.VO.ResultVO;
import com.sanye.manage.dataobject.*;
import com.sanye.manage.form.AnchorSalaryAdvanceForm;
import com.sanye.manage.form.CheckForm;
import com.sanye.manage.form.PersonnelSalaryAdvanceForm;
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
@RequestMapping("/oa/anchorSalaryAdvance")
public class AnchorSalaryAdvanceController {
    @Autowired
    private UserService userService;

    @Autowired
    private AnchorService anchorService;

    @Autowired
    private PlatformService platformService;
    @Autowired
    private PersonnelInfoService personnelInfoService;

    @Autowired
    private CheckService checkService;

    @Autowired
    private AnchorSalaryAdvanceService salaryAdvanceService;

    /**
     * @param map
     * @return
     */
    @GetMapping("/index")
    @RequiresPermissions("anchorSalaryAdvance:add")
    public ModelAndView index(Map<String, Object> map) {
        UserInfo userInfo = ShiroGetSession.getUserInfo();
        List<AnchorInfo> anchorInfoList = anchorService.findAllByUserId(userInfo.getId());
        map.put("userInfo", userInfo);
        map.put("anchorList", anchorInfoList);
        map.put("pageId", 5);
        map.put("pageTitle", "主播工资预支申请");
        if (!PayCheckUtil.check()) {
            return new ModelAndView("redirect:/oa/user/info.html");
        }
        return new ModelAndView("view/anchorSalaryAdvanceAdd", map);
    }

    /**
     * @param map
     * @param id
     * @return
     */
    @GetMapping("/img/{id}")
    @RequiresPermissions("anchorSalaryAdvance:add")
    public ModelAndView img(Map<String, Object> map,
                            @PathVariable Integer id) {
        AnchorSalaryAdvance anchorSalaryAdvance = salaryAdvanceService.findOne(id);
        map.put("anchorSalaryAdvance", anchorSalaryAdvance);
        map.put("pageId", 5);
        map.put("pageTitle", "图片上传");
        return new ModelAndView("view/anchorSalaryAdvanceImgAdd", map);
    }

    /**
     * @param anchorSalaryAdvanceForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    @RequiresPermissions("anchorSalaryAdvance:add")
    public ResultVO<Map<String, Object>> save(@Valid AnchorSalaryAdvanceForm anchorSalaryAdvanceForm,
                                              BindingResult bindingResult) {
        Map<String, Object> map = new HashMap<>();
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(100, bindingResult.getFieldError().getDefaultMessage());
        }
        if (anchorSalaryAdvanceForm.getPlatformId()==null) {
            return ResultVOUtil.error(100, "请联系运营负责人，原因：尚未将你添加成主播！！");
        }
        AnchorSalaryAdvance anchorSalaryAdvance = new AnchorSalaryAdvance();
        BeanUtils.copyProperties(anchorSalaryAdvanceForm, anchorSalaryAdvance);
        Integer id = salaryAdvanceService.save(anchorSalaryAdvance).getId();
        map.put("id", id);
        return ResultVOUtil.success(map);
    }

    /**
     * @param id
     * @return
     */
    @PostMapping("/check")
    @ResponseBody
    public ResultVO<Map<String, Object>> check(@RequestParam(value = "id") Integer id) {
        Map<String, Object> map = new HashMap<>();

        UserInfo userInfo = ShiroGetSession.getUserInfo();
        AnchorSalaryAdvance anchorSalaryAdvance = salaryAdvanceService.findOne(id);
        if (anchorSalaryAdvance.getImg() == null) {
            salaryAdvanceService.delete(id);
            return ResultVOUtil.error(100, "申请失败！");
        }
        //申请审核
        CheckForm checkForm = new CheckForm();
        checkForm.setApplyId(anchorSalaryAdvance.getId());
        checkForm.setType("主播工资预支");
        checkForm.setTitle(anchorSalaryAdvance.getTitle());
        checkForm.setPlatformId(anchorSalaryAdvance.getPlatformId());
        checkForm.setDescription(anchorSalaryAdvance.getDescription());
        checkForm.setSalary(anchorSalaryAdvance.getSalary());
        checkForm.setApplyPersonnelId(userInfo.getId());
        checkForm.setApplyPersonnelName(userInfo.getName());
        checkForm.setApplyTime(anchorSalaryAdvance.getCreateTime());
        checkService.anchorCheckSave(checkForm);
        return ResultVOUtil.success();
    }

    /**
     * @param page
     * @param size
     * @param checkStatus
     * @param resultStatus
     * @param month
     * @param map
     * @return
     */
    @GetMapping("/checkList")
    @RequiresPermissions("anchorSalaryAdvance:list")
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
        PageDTO<AnchorSalaryAdvanceDTO> pageDTO = salaryAdvanceService.findAllByMonthAndCheckStatusAndResultStatus(pageRequest, month, checkStatus, resultStatus);
        map.put("pageId", 27);
        map.put("pageTitle", "主播工资预支列表");
        map.put("month", month);
        map.put("checkStatus", checkStatus);
        map.put("resultStatus", resultStatus);
        map.put("pass", pass);
        map.put("noPass", noPass);
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/anchorSalaryAdvance/checkList.html");
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView("view/anchorSalaryAdvanceCheckList", map);
    }

    /**
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
        AnchorSalaryAdvance anchorSalaryAdvance = salaryAdvanceService.findOne(id);
        if (anchorSalaryAdvance == null) {
            return ResultVOUtil.error(100, "该工资预支暂不可拨款！");
        }
        List<CheckInfo> checkInfoList = checkService.findAllByApplyIdAndType(id, "主播工资预支", SortTools.basicSort("asc", "orderId"));
        for (CheckInfo c : checkInfoList) {
            if (c.getCheckStatus() == 0) {
                return ResultVOUtil.error(100, "该工资预支还处于审核中！");
            }
            if (resultStatus == 1) {
                if (c.getResultStatus() == 0) {
                    return ResultVOUtil.error(100, "该工资预支审核不通过！请撤销！");
                }
            }
        }
        PersonnelInfo anchorInfo = personnelInfoService.findByUserId(ShiroGetSession.getUserInfo().getId());
        anchorSalaryAdvance.setResultRemark(remark);
        anchorSalaryAdvance.setResultStatus(resultStatus);
        anchorSalaryAdvance.setCheckStatus(1);
        anchorSalaryAdvance.setCheckTime(GetTimeUtil.getTime());
        anchorSalaryAdvance.setCheckPersonnelId(anchorInfo.getId());
        anchorSalaryAdvance.setCheckPersonnelName(anchorInfo.getName());
        anchorSalaryAdvance.setBackStatus(0);
        AnchorSalaryAdvance resultAdvance = salaryAdvanceService.save(anchorSalaryAdvance);
        //发送短信
        Map<String,Object> map = new HashMap<>();
        UserInfo messageUser = userService.findOne(resultAdvance.getUserId());
        String phone = messageUser.getPhone();
        String username = messageUser.getName();
        String type = "工资预支";
        String result;
        if (resultStatus == 1) {
            result = "审核通过,已拨款";
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
     * @param id
     * @return
     */
    @PostMapping("/back")
    @ResponseBody
    public ResultVO<Map<String, Object>> back(@RequestParam("id") Integer id) {
        AnchorSalaryAdvance anchorSalaryAdvance = salaryAdvanceService.findOne(id);
        if (anchorSalaryAdvance == null) {
            return ResultVOUtil.error(100, "该工资预支不可归还！");
        }
        anchorSalaryAdvance.setBackStatus(1);
        anchorSalaryAdvance.setBackTime(GetTimeUtil.getTime());
        salaryAdvanceService.save(anchorSalaryAdvance);
        return ResultVOUtil.success();
    }

    /**
     * @param map
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list/user")
    @RequiresPermissions("anchorSalaryAdvance:userList")
    public ModelAndView listUser(Map<String, Object> map,
                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "10") Integer size) {

        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort());
        Page<AnchorSalaryAdvance> pageDTO = salaryAdvanceService.findAllByUserId(pageRequest, ShiroGetSession.getUserInfo().getId());
        map.put("pageId", 4);
        map.put("pageTitle", "个人预支记录");
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/anchorSalaryAdvance/list/user.html");
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView("view/anchorSalaryAdvanceListByUser", map);
    }

    @PostMapping("/revoke")
    @ResponseBody
    public ResultVO<Map<String, Object>> revoke(@RequestParam("id") Integer id){
        return ResultVOUtil.success(salaryAdvanceService.revoke(id));
    }

    @PostMapping("/remove/img")
    @ResponseBody
    public ResultVO<Map<String, Object>> removeImg(@RequestParam("id") Integer id){
        AnchorSalaryAdvance anchorSalaryAdvance = salaryAdvanceService.findOne(id);
        anchorSalaryAdvance.setImg("");
        salaryAdvanceService.save(anchorSalaryAdvance);
        return ResultVOUtil.success();
    }

    @PostMapping("/count/salary")
    @ResponseBody
    public ResultVO<Map<String, Object>> countSalary(@RequestParam("month") String month){
        return ResultVOUtil.success(salaryAdvanceService.countAllByMonthAndResultStatus(month));
    }
}
