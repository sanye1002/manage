package com.sanye.manage.controller;

import com.sanye.manage.VO.ResultVO;
import com.sanye.manage.dataobject.CheckInfo;
import com.sanye.manage.dataobject.DeptInfo;
import com.sanye.manage.dataobject.PersonnelInfo;
import com.sanye.manage.form.CheckForm;
import com.sanye.manage.service.CheckService;
import com.sanye.manage.service.PersonnelInfoService;
import com.sanye.manage.utils.GetTimeUtil;
import com.sanye.manage.utils.ResultVOUtil;
import com.sanye.manage.utils.ShiroGetSession;
import com.sanye.manage.utils.SortTools;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-20 上午 11:56
 * @Description description
 */
@Controller
@RequestMapping("/oa/check")
@RequiresAuthentication
public class CheckNoticeController {


    @Autowired
    private CheckService checkService;

    @Autowired
    private PersonnelInfoService personnelInfoService;

    @GetMapping("/list")
    @RequiresPermissions("check:list")
    public ModelAndView list(Map<String, Object> map,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "15") Integer size,
                             @RequestParam(value = "checkStatus",defaultValue = "2")Integer checkStatus){
        PageRequest pageRequest = new PageRequest(page-1,size, SortTools.basicSort());
        PersonnelInfo personnelInfo = personnelInfoService.findByUserId(ShiroGetSession.getUserInfo().getId());
        Page<CheckInfo> checkInfoPage = null;

        if (checkStatus==2){
            checkInfoPage = checkService.findAllByAcceptPersonnelId(pageRequest,personnelInfo.getId());
        }else {
            checkInfoPage = checkService.findAllByAcceptPersonnelIdAndCheckStatus(pageRequest,personnelInfo.getId(),checkStatus);

        }
        Integer checked = checkService.findAllByAcceptPersonnelIdAndCheckStatus(personnelInfo.getId(),1).size();
        Integer checking  = checkService.findAllByAcceptPersonnelIdAndCheckStatus(personnelInfo.getId(),0).size();
        Integer allCheck = checking+checked;


        map.put("pageId", 25);
        map.put("checked", checked);
        map.put("checking", checking);
        map.put("allCheck", allCheck);
        map.put("checkStatus", checkStatus);
        map.put("pageTitle", "审核列表");
        map.put("pageContent", checkInfoPage);
        map.put("size", size);
        map.put("currentPage", page);
        map.put("url", "/oa/check/list.html");
        return new ModelAndView("view/checkList", map);
    }

    @GetMapping("/index/{id}")
    @RequiresPermissions("check:list")
    public ModelAndView index(Map<String, Object> map,
                              @PathVariable(value = "id")Integer id){
       // List<CheckInfo> checkInfoList = checkService.findAllByApplyIdAndType(id,type,SortTools.basicSort("asc","orderId"));
        PersonnelInfo personnelInfo = personnelInfoService.findByUserId(ShiroGetSession.getUserInfo().getId());
        CheckInfo checkInfo = checkService.findOne(id);
        String type =null;
        if ("spending".equals(checkInfo.getType())||"日常开支".equals(checkInfo.getType())){
            type="日常开支审核";
        }
        if ("工作人员工资预支".equals(checkInfo.getType())){
            type="工作人员工资预支审核";
        }
        if ("主播工资预支".equals(checkInfo.getType())){
            type="主播工资预支审核";
        }
        if ("主播工资提现".equals(checkInfo.getType())){
            type="主播工资提现审核";
        }
        if ("物品借记".equals(checkInfo.getType())){
            type="物品借记审核";
        }
        map.put("pageId", 25);
        map.put("pageTitle", type);
        map.put("checkInfo", checkInfo);
        map.put("url", "/oa/dept/list.html");
        return new ModelAndView("view/checkInfo", map);
    }

    @PostMapping("/save")
    @ResponseBody
    public ResultVO<Map<String,Object>> save(@Valid CheckForm checkForm,
                                             Map<String,Object> map){
        PersonnelInfo personnelInfo = personnelInfoService.findByUserId(ShiroGetSession.getUserInfo().getId());
        checkForm.setCheckPersonnelId(personnelInfo.getId());
        checkForm.setCheckPersonnelName(personnelInfo.getName());
        checkForm.setCheckTime(GetTimeUtil.getTime());
        checkService.check(checkForm);
        return ResultVOUtil.success();
    }

}
