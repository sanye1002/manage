package com.sanye.manage.controller;

import com.sanye.manage.DTO.AnchorSalaryWithdrawDTO;
import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.VO.ResultVO;
import com.sanye.manage.dataobject.*;
import com.sanye.manage.form.AnchorSalaryWithdrawForm;
import com.sanye.manage.form.CheckForm;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-26 下午 4:57
 * @Description description
 */
@Controller
@RequestMapping("/oa/withdraw")
@RequiresAuthentication
public class AnchorSalaryWithdrawController {
    @Autowired
    private MOMOAnchorSalaryService momoAnchorSalaryService;

    @Autowired
    private PlatformService platformService;
    @Autowired
    private AnchorService anchorService;
    @Autowired
    private WithdrawService withdrawService;
    @Autowired
    private CheckService checkService;
    @Autowired
    private PersonnelInfoService personnelInfoService;
    @Autowired
    private UserService userService;
    @PostMapping("/salary")
    @ResponseBody
    public ResultVO<Map<String,Object>> salary(@RequestParam BigDecimal salary,
                                               @RequestParam Integer id,
                                               @RequestParam Integer platformId){
        Map<String,Object> map = new HashMap<>();
        UserInfo userInfo = ShiroGetSession.getUserInfo();
        PlatformInfo platformInfo = platformService.findOne(platformId);
        // TODO: 2018/4/26 0026 判断平台主播工资
        if (platformInfo.getName().equals("陌陌")){
            MOMOAnchorSalary momoAnchorSalary = momoAnchorSalaryService.findOne(id);
            if (momoAnchorSalary.getMentionable().compareTo(salary)==-1){
                return ResultVOUtil.error(100, "申请金额大于剩余金额！");
            }
            momoAnchorSalary.setSystemTX(momoAnchorSalary.getSystemTX().add(salary));
            momoAnchorSalary.setMentionable(momoAnchorSalary.getMentionable().subtract(salary));
            momoAnchorSalaryService.save(momoAnchorSalary);
        }

        // 提现保存
        AnchorSalaryWithdrawForm salaryWithdrawForm = new AnchorSalaryWithdrawForm();
        salaryWithdrawForm.setAnchorName(userInfo.getName());
        salaryWithdrawForm.setUserId(userInfo.getId());
        salaryWithdrawForm.setPlatformId(platformId);
        salaryWithdrawForm.setSalary(salary);
        AnchorSalaryWithdraw anchorSalaryWithdraw = new AnchorSalaryWithdraw();
        BeanUtils.copyProperties(salaryWithdrawForm,anchorSalaryWithdraw);
        anchorSalaryWithdraw =  withdrawService.save(anchorSalaryWithdraw);
        // 提现审核
        CheckForm checkForm = new CheckForm();
        checkForm.setApplyId(anchorSalaryWithdraw.getId());
        checkForm.setType("主播工资提现");
        checkForm.setTitle("主播工资提现");
        checkForm.setPlatformId(anchorSalaryWithdraw.getPlatformId());
        checkForm.setDescription("主播工资提现金额为"+anchorSalaryWithdraw.getSalary());
        checkForm.setSalary(anchorSalaryWithdraw.getSalary());
        checkForm.setApplyPersonnelId(userInfo.getId());
        checkForm.setApplyPersonnelName(userInfo.getName());
        checkForm.setApplyTime(anchorSalaryWithdraw.getCreateTime());
        checkService.anchorCheckSave(checkForm);
        return ResultVOUtil.success();
    }

    @GetMapping("/list/user")

    public ModelAndView listUser(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "12") Integer size,
                             @RequestParam(value = "platform") Integer platform,
                             Map<String, Object> map){
        UserInfo userInfo = ShiroGetSession.getUserInfo();
        PlatformInfo platformInfo = platformService.findOne(platform);
        if (!PayCheckUtil.check()){
            return new ModelAndView("redirect:/oa/user/info.html");
        }
        // TODO: 2018/4/26 0026 判断平台主播工资
        PageRequest pageRequest = new PageRequest(page-1,size, SortTools.basicSort());
        if (platformInfo.getName().equals("陌陌")){
            Page<MOMOAnchorSalary> momoAnchorSalaryPage = momoAnchorSalaryService.findAllByUserId(pageRequest,userInfo.getId());
            map.put("pageContent", momoAnchorSalaryPage);
        }
        List<AnchorInfo> anchorInfoList = anchorService.findAllByUserId(userInfo.getId());
        map.put("anchorInfoList",anchorInfoList);
        map.put("pageId", 1);
        map.put("pageTitle", "个人工资列表");
        map.put("userInfo", userInfo);
        map.put("platform", platform);
        map.put("url", "/oa/withdraw/list/user.html");
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView("view/anchorSalaryWithdrawList", map);
    }

    @GetMapping("/list")
    @RequiresPermissions("withdrawCheck:list")
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
        Integer pass = withdrawService.findAllByMonthAndResultStatus(month, 1).size();
        Integer noPass = withdrawService.findAllByMonthAndResultStatus(month, 0).size();
        PageDTO<AnchorSalaryWithdrawDTO> pageDTO = withdrawService.findAllByMonthAndCheckStatusAndResultStatus(pageRequest, month, checkStatus, resultStatus);
        map.put("pageId", 28);
        map.put("pageTitle", "主播工资提现列表");
        map.put("month", month);
        map.put("checkStatus", checkStatus);
        map.put("resultStatus", resultStatus);
        map.put("pass", pass);
        map.put("noPass", noPass);
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/withdraw/list.html");
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView("view/anchorSalaryWithdrawCheckList", map);
    }
    @PostMapping("/grants")
    @ResponseBody
    public ResultVO<Map<String, Object>> grants(@RequestParam("id") Integer id,
                                                @RequestParam("resultStatus") Integer resultStatus,
                                                @RequestParam(value = "remark") String remark) {
        AnchorSalaryWithdraw anchorSalaryWithdraw = withdrawService.findOne(id);
        if (anchorSalaryWithdraw == null) {
            return ResultVOUtil.error(100, "该工资提现暂不可拨款！");
        }
        List<CheckInfo> checkInfoList = checkService.findAllByApplyIdAndType(id, "主播工资提现", SortTools.basicSort("asc", "orderId"));
        for (CheckInfo c : checkInfoList) {
            if (c.getCheckStatus() == 0) {
                return ResultVOUtil.error(100, "该工资提现还处于审核中！");
            }
            if (resultStatus==1){
                if (c.getResultStatus() == 0) {
                    return ResultVOUtil.error(100, "该工资提现审核不通过！请撤销！");
                }
            }
        }
        PersonnelInfo personnelInfo = personnelInfoService.findByUserId(ShiroGetSession.getUserInfo().getId());
        anchorSalaryWithdraw.setResultRemark(remark);
        anchorSalaryWithdraw.setResultStatus(resultStatus);
        anchorSalaryWithdraw.setCheckStatus(1);
        anchorSalaryWithdraw.setCheckTime(GetTimeUtil.getTime());
        anchorSalaryWithdraw.setCheckPersonnelId(personnelInfo.getId());
        anchorSalaryWithdraw.setCheckPersonnelName(personnelInfo.getName());
        AnchorSalaryWithdraw resultWithdraw = withdrawService.save(anchorSalaryWithdraw);
        //发送短信
        Map<String,Object> map = new HashMap<>();
        UserInfo messageUser = userService.findOne(resultWithdraw.getUserId());
        String phone = messageUser.getPhone();
        String username = messageUser.getName();
        String type = "工资提现审核";
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



}
