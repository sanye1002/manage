package com.sanye.manage.controller;

import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.DTO.PersonnelSalaryAdvanceDTO;
import com.sanye.manage.VO.ResultVO;
import com.sanye.manage.dataobject.AnchorSalaryAdvance;
import com.sanye.manage.dataobject.ItemInfo;
import com.sanye.manage.dataobject.PersonnelSalaryAdvance;
import com.sanye.manage.form.ItemInfoForm;
import com.sanye.manage.form.PersonnelSalaryAdvanceForm;
import com.sanye.manage.service.ItemInfoService;
import com.sanye.manage.utils.GetTimeUtil;
import com.sanye.manage.utils.ResultVOUtil;
import com.sanye.manage.utils.ShiroGetSession;
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
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-27 上午 11:56
 * @Description description
 */
@Controller
@RequestMapping("/oa/item")
@RequiresAuthentication
public class ItemController {

    @Autowired
    private ItemInfoService itemInfoService;

    @GetMapping("/index")
    @RequiresPermissions("item:add")
    public ModelAndView index(@RequestParam(value = "id", defaultValue = "") Integer id,
                              Map<String, Object> map) {
        ItemInfo itemInfo = new ItemInfo();
        if (id != null) {
            itemInfo = itemInfoService.findOne(id);
        }

        map.put("user", ShiroGetSession.getUserInfo());
        map.put("pageId", 32);
        map.put("itemInfo", itemInfo);
        map.put("pageTitle", "物品添加");
        return new ModelAndView("view/itemAdd", map);
    }

    @PostMapping("/save")
    @ResponseBody
    public ResultVO<Map<String, Object>> save(@Valid ItemInfoForm itemInfoForm,
                                              BindingResult bindingResult) {
        Map<String, Object> map = new HashMap<>();
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(100, bindingResult.getFieldError().getDefaultMessage());
        }
        ItemInfo itemInfo = new ItemInfo();
        if (itemInfoForm.getId() != null) {
            itemInfo = itemInfoService.findOne(itemInfoForm.getId());
            Integer before =  itemInfoForm.getSumAmount()-itemInfo.getSumAmount();
            if (before<0){
                return ResultVOUtil.error(100, "总数比以前还少了！！");
            }
            itemInfo.setBeforeAmount(itemInfo.getBeforeAmount()+before);
        }

        BeanUtils.copyProperties(itemInfoForm, itemInfo);
        if (itemInfoForm.getId() == null) {
            itemInfo.setBeforeAmount(itemInfo.getSumAmount());
            itemInfo.setCreateTime(GetTimeUtil.getTime());
            itemInfo.setUpdateTime(GetTimeUtil.getTime());

        } else {
            itemInfo.setUpdateTime(GetTimeUtil.getTime());
        }
        Integer id = itemInfoService.save(itemInfo).getId();
        map.put("id", id);
        return ResultVOUtil.success(map);
    }

    @GetMapping("/img/{id}")
    @RequiresPermissions("item:add")
    public ModelAndView img(Map<String, Object> map,
                            @PathVariable Integer id) {

        ItemInfo itemInfo = itemInfoService.findOne(id);
        map.put("itemInfo", itemInfo);
        map.put("pageId", 32);
        map.put("pageTitle", "图片上传");
        return new ModelAndView("view/itemImgAdd", map);
    }

    @PostMapping("/check")
    @ResponseBody
    public ResultVO<Map<String, Object>> check(@RequestParam(value = "id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        ItemInfo itemInfo = itemInfoService.findOne(id);
        if (itemInfo.getImg() == null) {
            itemInfoService.delete(id);
            return ResultVOUtil.error(100, "添加失败！");
        }
        //申请审核
        return ResultVOUtil.success();
    }

    @GetMapping("/list")
    @RequiresPermissions("item:list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "12") Integer size,
                             Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort());
        Page<ItemInfo> itemInfoPage = itemInfoService.findAll(pageRequest);
        map.put("pageId", 33);
        map.put("pageContent", itemInfoPage);
        map.put("pageTitle", "物品列表");
        map.put("url", "/oa/item/list.html");
        map.put("size", size);
        map.put("currentPage", page);

        return new ModelAndView("view/itemList", map);
    }

}
