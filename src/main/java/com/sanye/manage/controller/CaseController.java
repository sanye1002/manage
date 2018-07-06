package com.sanye.manage.controller;

import com.sanye.manage.DTO.CaseMasterDTO;
import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.VO.ResultVO;
import com.sanye.manage.dataobject.ApplicationCase;
import com.sanye.manage.dataobject.BackCase;
import com.sanye.manage.dataobject.CaseMaster;
import com.sanye.manage.form.ApplicationCaseForm;
import com.sanye.manage.form.BackCaseForm;
import com.sanye.manage.service.CaseService;
import com.sanye.manage.utils.GetTimeUtil;
import com.sanye.manage.utils.ResultVOUtil;
import com.sanye.manage.utils.ShiroGetSession;
import com.sanye.manage.utils.SortTools;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-10 下午 2:02
 * @Description description
 */
@Controller
@RequestMapping("/oa/case")
public class CaseController {
    @Autowired
    private CaseService caseService;

    @RequiresPermissions("case:add")
    @GetMapping("/application/index")
    @RequiresAuthentication
    public ModelAndView applicationIndex(Map<String, Object> map,
                                         @RequestParam(value = "id", defaultValue = "") Integer id) {
        ApplicationCase applicationCase = new ApplicationCase();
        if (id != null) {
            applicationCase = caseService.findOneApplicationCase(id);
        }
        map.put("applicationCase", applicationCase);
        map.put("pageId", 52);
        map.put("pageTitle", "事件申请");
        return new ModelAndView("view/sendApplicationCase", map);
    }

    @RequiresPermissions("case:add")
    @GetMapping("/back/index")
    @RequiresAuthentication
    public ModelAndView backCaseIndex(Map<String, Object> map,
                                      @RequestParam(value = "id", defaultValue = "") Integer id,
                                      @RequestParam(value = "caseId") Integer caseId) {
        BackCase backCase = new BackCase();
        if (id != null) {
            backCase = caseService.findOneBackCase(id);
        }
        map.put("backCase", backCase);
        map.put("pageId", 52);
        map.put("caseId", caseId);
        map.put("pageTitle", "回执事件");
        return new ModelAndView("view/sendBackCase", map);
    }

    @PostMapping("/application/save")
    @ResponseBody
    public ResultVO<Map<String, Object>> saveApplication(@Valid ApplicationCaseForm applicationCaseForm,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(100, bindingResult.getFieldError().getDefaultMessage());
        }
        caseService.saveApplicationCase(applicationCaseForm);
        return ResultVOUtil.success();
    }
    @PostMapping("/back/save")
    @ResponseBody
    public ResultVO<Map<String, Object>> saveBack(@Valid BackCaseForm backCaseForm,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(100, bindingResult.getFieldError().getDefaultMessage());
        }
        backCaseForm.setMonth(GetTimeUtil.getMonth());
        backCaseForm.setCreateTime(GetTimeUtil.getDate());
        backCaseForm.setUserId(ShiroGetSession.getUserInfo().getId());
        backCaseForm.setUsername(ShiroGetSession.getUserInfo().getName());
        BackCase backCase = new BackCase();
        if (backCaseForm.getId() != null) {
            backCase = caseService.findOneBackCase(backCaseForm.getId());
        }
        BeanUtils.copyProperties(backCaseForm, backCase);
        BackCase result = caseService.saveBackCase(backCase);
        if (backCaseForm.getId() == null) {
            CaseMaster caseMaster = caseService.findOneCaseMaster(backCaseForm.getCaseId());
            caseMaster.setBackId(result.getId());
            caseService.saveCaseMaster(caseMaster);
        }
        return ResultVOUtil.success();
    }


    @RequiresPermissions("case:list")
    @GetMapping("/master/list/user")
    public ModelAndView caseMasterListByUser(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                             @RequestParam(value = "size", defaultValue = "12") Integer size,
                                             Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort());
        PageDTO<CaseMasterDTO> pageDTO = caseService.findAllByUserId(pageRequest, ShiroGetSession.getUserInfo().getId());
        map.put("pageTitle", "个人事件列表");
        map.put("pageId", 51);
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/case/master/list/user.html");
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView("view/caseMasterListByUser", map);
    }
    @GetMapping("/master/list/check")
    @RequiresPermissions("caseCheck:List")
    public ModelAndView caseMasterListByCheck(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                             @RequestParam(value = "size", defaultValue = "12") Integer size,
                                             Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort());
        PageDTO<CaseMasterDTO> pageDTO = caseService.findAllByCheckUserId(pageRequest, ShiroGetSession.getUserInfo().getId());
        map.put("pageTitle", "审核事件列表");
        map.put("pageId", 50);
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/case/master/list/check.html");
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView("view/caseMasterListByCheck", map);
    }

    @PostMapping("/master/check")
    @ResponseBody
    public ResultVO<Map<String,Object>> masterCheck(@RequestParam(value = "applicationId",defaultValue = "") Integer applicationId,
                                                    @RequestParam(value = "backCaseId",defaultValue = "") Integer backCaseId,
                                                    @RequestParam(value = "resultStatus") Integer resultStatus,
                                                    @RequestParam(value = "resultRemark") String resultRemark){
        if (applicationId!=null){
            ApplicationCase applicationCase = caseService.findOneApplicationCase(applicationId);
            applicationCase.setCheckStatus(1);
            applicationCase.setResultStatus(resultStatus);
            applicationCase.setResultRemark(resultRemark);
            applicationCase.setCheckPersonnelId(ShiroGetSession.getUserInfo().getId());
            applicationCase.setCheckPersonnelName(ShiroGetSession.getUserInfo().getName());
            applicationCase.setCheckTime(GetTimeUtil.getDate());
            caseService.saveApplicationCase(applicationCase);
        }

        if (backCaseId!=null){
            BackCase backCase = caseService.findOneBackCase(backCaseId);
            backCase.setCheckStatus(1);
            backCase.setResultStatus(resultStatus);
            backCase.setResultRemark(resultRemark);
            backCase.setCheckPersonnelId(ShiroGetSession.getUserInfo().getId());
            backCase.setCheckPersonnelName(ShiroGetSession.getUserInfo().getName());
            backCase.setCheckTime(GetTimeUtil.getDate());
            caseService.saveBackCase(backCase);
        }

        return ResultVOUtil.success();
    }

    @GetMapping("/master/list/manager")
    @RequiresPermissions("caseCheck:manager")
    public ModelAndView caseMasterListByManager(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                              @RequestParam(value = "size", defaultValue = "12") Integer size,
                                              Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort());
        PageDTO<CaseMasterDTO> pageDTO = caseService.findAll(pageRequest);
        map.put("pageTitle", "所有事件列表");
        map.put("pageId", 60);
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/case/master/list/manager.html");
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView("view/caseMasterListByManager", map);
    }




}
