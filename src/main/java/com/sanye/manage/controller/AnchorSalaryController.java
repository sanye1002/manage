package com.sanye.manage.controller;

import com.sanye.manage.dataobject.MOMOAnchorSalary;
import com.sanye.manage.service.MOMOAnchorSalaryService;
import com.sanye.manage.utils.GetTimeUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-17 下午 4:51
 */
@Controller
@RequestMapping("/oa/anchorSalary")
public class AnchorSalaryController {

    @Autowired
    private MOMOAnchorSalaryService momoAnchorSalaryService;
    @GetMapping("/list/momo")
    @RequiresPermissions("momoSalary:list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "15") Integer size,
                             @RequestParam(value = "month", defaultValue = "") String month,
                             Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page-1,size);
        if ("".equals(month)) {
            month = GetTimeUtil.getMonth();
        }
        Page<MOMOAnchorSalary> momoAnchorSalaryPage = momoAnchorSalaryService.findAllByMonth(pageRequest,month);
        map.put("pageId", 31);
        map.put("pageTitle", "陌陌主播工资列表");
        map.put("month", month);
        map.put("pageContent", momoAnchorSalaryPage);
        map.put("url", "/oa/anchorSalary/list/momo.html");
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView("view/momoAnchorSalaryList");
    }
}
