package com.sanye.manage.controller;

import com.sanye.manage.VO.ImgVO;
import com.sanye.manage.VO.ResultVO;
import com.sanye.manage.dataobject.*;
import com.sanye.manage.service.*;
import com.sanye.manage.utils.ImgVOUtil;
import com.sanye.manage.utils.KeyUtil;
import com.sanye.manage.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-16 下午 6:38
 */
@Controller
@RequestMapping("/layer")
public class LayuiPhotos {
    @Autowired
    private UserService userService;
    @Autowired
    private SpendingService spendingService;

    @Autowired
    private PersonnelSalaryAdvanceService personnelSalaryAdvanceService;

    @Autowired
    private AnchorSalaryAdvanceService anchorSalaryAdvanceService;
    @Autowired
    private ItemInfoService itemInfoService;
    @GetMapping("/photos/{id}")
    @ResponseBody
    public ImgVO<List<Map<String,Object>>> photos(@PathVariable(value = "id")Integer id){
        UserInfo userInfo = userService.findOne(id);
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        map1.put("alt","身份证正面");
        map1.put("pid","1");
        map1.put("src",userInfo.getIdCardPositive());
        map1.put("thumb",userInfo.getIdCardPositive());
        Map<String,Object> map2 = new HashMap<>();
        map2.put("alt","身份证反面");
        map2.put("pid","2");
        map2.put("src",userInfo.getIdCardSide());
        map2.put("thumb",userInfo.getIdCardSide());
        Map<String,Object> map3 = new HashMap<>();
        map3.put("alt","身份证手持照");
        map3.put("pid","3");
        map3.put("src",userInfo.getIdCardHold());
        map3.put("thumb",userInfo.getIdCardHold());
        list.add(map1);
        list.add(map2);
        list.add(map3);
        return ImgVOUtil.success(id,list);
    }
    @GetMapping("/spending/{id}")
    @ResponseBody
    public ImgVO<List<Map<String,Object>>> spending(@PathVariable(value = "id")Integer id){
        List<Map<String,Object>> list = new ArrayList<>();

        SpendingInfo spendingInfo = spendingService.findOne(id);
        String allSrc = spendingInfo.getImg();
        String[] srcList = KeyUtil.splitString(allSrc);
        for (int i=0;i<srcList.length;i++){

            Map<String,Object> map = new HashMap<>();
            map.put("alt","开支图片"+i);
            map.put("pid",i);
            map.put("src",srcList[i]);
            map.put("thumb",srcList[i]);
            list.add(map);
        }
        return ImgVOUtil.success(id,list);
    }
    @GetMapping("/personnelSalaryAdvance/{id}")
    @ResponseBody
    public ImgVO<Map<String,String>> advance(@PathVariable(value = "id")Integer id){
        List<Map<String,Object>> list = new ArrayList<>();
        PersonnelSalaryAdvance personnelSalaryAdvance = personnelSalaryAdvanceService.findOne(id);
        String allSrc = personnelSalaryAdvance.getImg();

        String[] srcList = KeyUtil.splitString(allSrc);
        for (int i=0;i<srcList.length;i++){

            Map<String,Object> map = new HashMap<>();
            map.put("alt","工作人员工资预支"+i);
            map.put("pid",i);
            map.put("src",srcList[i]);
            map.put("thumb",srcList[i]);
            list.add(map);
        }
        return ImgVOUtil.success(id,list);
    }
    @GetMapping("/anchorSalaryAdvance/{id}")
    @ResponseBody
    public ImgVO<Map<String,String>> anchorSalaryAdvance(@PathVariable(value = "id")Integer id){
        List<Map<String,Object>> list = new ArrayList<>();
        AnchorSalaryAdvance anchorSalaryAdvance = anchorSalaryAdvanceService.findOne(id);
        String allSrc = anchorSalaryAdvance.getImg();

        String[] srcList = KeyUtil.splitString(allSrc);
        for (int i=0;i<srcList.length;i++){

            Map<String,Object> map = new HashMap<>();
            map.put("alt","主播工资预支"+i);
            map.put("pid",i);
            map.put("src",srcList[i]);
            map.put("thumb",srcList[i]);
            list.add(map);
        }
        return ImgVOUtil.success(id,list);
    }
    @GetMapping("/item/{id}")
    @ResponseBody
    public ImgVO<Map<String,String>> itemImg(@PathVariable(value = "id")Integer id){
        List<Map<String,Object>> list = new ArrayList<>();
        ItemInfo itemInfo = itemInfoService.findOne(id);
        String allSrc = itemInfo.getImg();

        String[] srcList = KeyUtil.splitString(allSrc);
        for (int i=0;i<srcList.length;i++){

            Map<String,Object> map = new HashMap<>();
            map.put("alt","物品图片信息"+i);
            map.put("pid",i);
            map.put("src",srcList[i]);
            map.put("thumb",srcList[i]);
            list.add(map);
        }
        return ImgVOUtil.success(id,list);
    }
}
