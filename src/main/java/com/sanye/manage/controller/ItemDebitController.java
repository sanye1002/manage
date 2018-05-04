package com.sanye.manage.controller;

import com.sanye.manage.DTO.ItemDebitDTO;
import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.VO.ResultVO;
import com.sanye.manage.dataobject.*;
import com.sanye.manage.form.CheckForm;
import com.sanye.manage.form.ItemDebitForm;
import com.sanye.manage.form.ItemInfoForm;
import com.sanye.manage.service.CheckService;
import com.sanye.manage.service.ItemDebitService;
import com.sanye.manage.service.ItemInfoService;
import com.sanye.manage.service.PersonnelInfoService;
import com.sanye.manage.utils.GetTimeUtil;
import com.sanye.manage.utils.ResultVOUtil;
import com.sanye.manage.utils.ShiroGetSession;
import com.sanye.manage.utils.SortTools;
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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-27 下午 5:26
 * @Description description
 */
@Controller
@RequestMapping("/oa/itemDebit")
@RequiresAuthentication
public class ItemDebitController {

    @Autowired
    private ItemDebitService itemDebitService;
    @Autowired
    private CheckService checkService;
    @Autowired
    private ItemInfoService itemInfoService;
    @Autowired
    private PersonnelInfoService personnelInfoService;

    @GetMapping("/index")
    @RequiresPermissions("itemDebit:add")
    public ModelAndView index(Map<String, Object> map) {

        UserInfo userInfo = ShiroGetSession.getUserInfo();
        List<ItemInfo> itemInfoList = itemInfoService.findAll();
        map.put("itemInfoList", itemInfoList);
        map.put("userInfo", userInfo);
        map.put("pageId", 10);
        map.put("pageTitle", "物品借记添加");
        return new ModelAndView("view/itemDebitAdd", map);
    }
    @PostMapping("/save")
    @ResponseBody
    public ResultVO<Map<String, Object>> save(@Valid ItemDebitForm itemDebitForm,
                                              BindingResult bindingResult) {
        UserInfo userInfo = ShiroGetSession.getUserInfo();
        Map<String, Object> map = new HashMap<>();
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(100, bindingResult.getFieldError().getDefaultMessage());
        }
        ItemInfo itemInfo = itemInfoService.findOne(itemDebitForm.getItemId());
        if (itemInfo.getBeforeAmount()<itemDebitForm.getNumber()){
            return ResultVOUtil.error(100, "借记数量大于剩余数量！");
        }
        //减库存
        itemInfo.setBeforeAmount(itemInfo.getBeforeAmount()-itemDebitForm.getNumber());
        itemInfoService.save(itemInfo);
        ItemDebit itemDebit = new ItemDebit();
        BeanUtils.copyProperties(itemDebitForm,itemDebit);
        itemDebit.setSumAmount(itemDebitForm.getNumber());
        itemDebit.setItemName(itemInfo.getName());
        itemDebit = itemDebitService.save(itemDebit);
        //申请审核
        CheckForm checkForm = new CheckForm();
        checkForm.setApplyId(itemDebit.getId());
        checkForm.setType("物品借记");
        checkForm.setTitle("物品借记");
        checkForm.setDescription(itemDebit.getRemark());
        checkForm.setSalary(new BigDecimal(itemDebit.getSumAmount()));
        checkForm.setApplyPersonnelId(userInfo.getId());
        checkForm.setApplyPersonnelName(userInfo.getName());
        checkForm.setApplyTime(itemDebit.getCreateTime());
        checkService.personnelCheckSave(checkForm);
        return ResultVOUtil.success(map);
    }
    @GetMapping("/list")
    @RequiresPermissions("itemDebitCheck:list")
    public ModelAndView spendingList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "12") Integer size,
                                     @RequestParam(value = "checkStatus", defaultValue = "1") Integer checkStatus,
                                     @RequestParam(value = "resultStatus", defaultValue = "1") Integer resultStatus,
                                     Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort());
        Integer pass = itemDebitService.findAllByResultStatus(1).size();
        Integer noPass = itemDebitService.findAllByResultStatus(0).size();
        PageDTO<ItemDebitDTO> pageDTO = itemDebitService.findAllByCheckStatusAndResultStatus(pageRequest, checkStatus, resultStatus);
        map.put("pageId", 34);
        map.put("pageTitle", "物品借记发放列表");
        map.put("checkStatus", checkStatus);
        map.put("resultStatus", resultStatus);
        map.put("pass", pass);
        map.put("noPass", noPass);
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/itemDebit/list.html");
        map.put("size", size);
        map.put("currentPage", page);

        return new ModelAndView("view/itemDebitList", map);
    }
    @PostMapping("/grants")
    @ResponseBody
    public ResultVO<Map<String, Object>> grants(@RequestParam("id") Integer id,
                                                @RequestParam("resultStatus") Integer resultStatus,
                                                @RequestParam(value = "remark") String remark) {
        ItemDebit itemDebit = itemDebitService.findOne(id);
        if (itemDebit == null) {
            return ResultVOUtil.error(100, "该物品占不可出库！");
        }
        List<CheckInfo> checkInfoList = checkService.findAllByApplyIdAndType(id, "物品借记", SortTools.basicSort("asc", "orderId"));
        for (CheckInfo c : checkInfoList) {
            if (c.getCheckStatus() == 0) {
                return ResultVOUtil.error(100, "该物品借记还处于审核中！");
            }
            if (c.getResultStatus() == 0) {
                return ResultVOUtil.error(100, "该物品借记审核不通过！请撤销！");
            }
        }
        PersonnelInfo personnelInfo = personnelInfoService.findByUserId(ShiroGetSession.getUserInfo().getId());
        itemDebit.setResultRemark(remark);
        itemDebit.setResultStatus(resultStatus);
        itemDebit.setCheckStatus(1);
        itemDebit.setCheckTime(GetTimeUtil.getTime());
        itemDebit.setCheckPersonnelId(personnelInfo.getId());
        itemDebit.setCheckPersonnelName(personnelInfo.getName());
        itemDebit.setBackStatus(0);
        itemDebitService.save(itemDebit);
        //减库存
        ItemInfo itemInfo = itemInfoService.findOne(itemDebit.getItemId());
        itemInfo.setBeforeAmount(itemInfo.getBeforeAmount()-itemDebit.getSumAmount());
        itemInfoService.save(itemInfo);
        return ResultVOUtil.success();
    }

    /**
     *
     * @param id
     * @return
     */
    @PostMapping("/back")
    @ResponseBody
    public ResultVO<Map<String, Object>> back(@RequestParam("id") Integer id){
        ItemDebit itemDebit = itemDebitService.findOne(id);
        if (itemDebit == null) {
            return ResultVOUtil.error(100, "该物品占不可出库！");
        }
        itemDebit.setBackStatus(1);
        itemDebit.setBackTime(GetTimeUtil.getTime());
        itemDebitService.save(itemDebit);
        //加库存
        ItemInfo itemInfo = itemInfoService.findOne(itemDebit.getItemId());
        itemInfo.setBeforeAmount(itemInfo.getBeforeAmount()+itemDebit.getSumAmount());
        itemInfoService.save(itemInfo);
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
    @RequiresPermissions("itemDebit:userList")
    public ModelAndView listUser(Map<String, Object> map,
                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort());
        Page<ItemDebit> pageDTO = itemDebitService.findAllByUserId(pageRequest, ShiroGetSession.getUserInfo().getId());
        map.put("pageId", 9);
        map.put("pageTitle", "个人申请记录");
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/itemDebit/list/user.html");
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView("view/itemDebitListByUser", map);
    }

}
