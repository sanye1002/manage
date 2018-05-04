package com.sanye.manage.controller;

import com.sanye.manage.VO.ResultVO;
import com.sanye.manage.dataobject.PersonnelInfo;
import com.sanye.manage.dataobject.PlatformInfo;
import com.sanye.manage.form.PlatformForm;
import com.sanye.manage.service.PersonnelInfoService;
import com.sanye.manage.service.PlatformService;
import com.sanye.manage.utils.ResultVOUtil;
import com.sanye.manage.utils.SortTools;
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
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-12 下午 6:35
 */
@Controller
@RequestMapping("/oa/platform")
public class PlatformController {
    @Autowired
    private PlatformService platformService;

    @Autowired
    private PersonnelInfoService personnelInfoService;

    @GetMapping("/list")
    @RequiresPermissions("platform:list")
    public ModelAndView list(Map<String, Object> map,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size){
        PageRequest pageRequest = new PageRequest(page-1,size, SortTools.basicSort());
        Page<PlatformInfo> infoPage = platformService.findAll(pageRequest);
        map.put("pageId", 20);
        map.put("pageTitle", "直播平台列表");
        map.put("pageContent", infoPage);
        map.put("size", size);
        map.put("currentPage", page);
        map.put("url", "/oa/platform/list.html");
        return new ModelAndView("view/platformList", map);
    }

    @GetMapping("/index")
    @RequiresPermissions("platform:add")
    public ModelAndView index(Map<String, Object> map,
                              @RequestParam(value = "id",defaultValue = "") Integer id){
        map.put("pageId", 19);
        PlatformInfo platformInfo = new PlatformInfo();
        if (id==null){
            map.put("pageTitle", "直播平台添加");
        }
        if (id!=null){
            map.put("pageTitle", "直播平台编辑");
            platformInfo = platformService.findOne(id);
        }
        map.put("platformInfo", platformInfo);
        List<PersonnelInfo> personnelInfoList = personnelInfoService.findAll();
        map.put("personnelInfoList", personnelInfoList);
        return new ModelAndView("view/platformAdd", map);
    }
    @PostMapping("/save")
    @ResponseBody
    public ResultVO<Map<String,Object>> save(@Valid PlatformForm platformForm,
                                             BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(100, bindingResult.getFieldError().getDefaultMessage());
        }
        Map<String,Object> map = new HashMap<>();
        PlatformInfo platformInfo = new PlatformInfo();
        if (platformForm.getId()!=null){
            platformInfo = platformService.findOne(platformForm.getId());
        }
        BeanUtils.copyProperties(platformForm,platformInfo);
        platformService.save(platformInfo);
        return ResultVOUtil.success();

    }
    @PostMapping("/delete")
    @ResponseBody
    public ResultVO<Map<String,Object>> delete(@RequestParam(value = "id")Integer id){
        Map<String,Object> map = new HashMap<>();
        map = platformService.delete(id);
        return ResultVOUtil.success(map);
    }
}
