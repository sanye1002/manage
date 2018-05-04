package com.sanye.manage.controller;

import com.sanye.manage.VO.ResultVO;
import com.sanye.manage.dataobject.DeptInfo;
import com.sanye.manage.dataobject.PersonnelInfo;
import com.sanye.manage.form.DeptForm;
import com.sanye.manage.service.DeptService;
import com.sanye.manage.service.PersonnelInfoService;
import com.sanye.manage.utils.ResultVOUtil;
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
import java.util.List;
import java.util.Map;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-11 下午 3:14
 */
@Controller
@RequestMapping("/oa/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @Autowired
    private PersonnelInfoService personnelInfoService;

    /**
     * 部门添加
     *
     * @param map
     * @return
     */
    @RequiresPermissions("dept:add")
    @GetMapping("/index")
    public ModelAndView index(Map<String, Object> map) {
        map.put("pageId", 17);
        map.put("pageTitle", "部门添加");
        List<PersonnelInfo> personnelInfo = personnelInfoService.findAll();
        map.put("personnelInfo", personnelInfo);
        return new ModelAndView("view/deptAdd", map);
    }

    /**
     * 部门编辑
     *
     * @param map
     * @return
     */
    @RequiresPermissions("dept:add")
    @GetMapping("/edit")
    public ModelAndView edit(Map<String, Object> map,
                             @RequestParam(value = "id") Integer id) {
        DeptInfo deptInfo = deptService.findOne(id);
        map.put("pageId", 17);
        map.put("pageTitle", "部门编辑");
        map.put("deptInfo", deptInfo);
        List<PersonnelInfo> personnelInfoList = personnelInfoService.findAll();
        map.put("personnelInfoList", personnelInfoList);
        return new ModelAndView("view/deptEdit", map);
    }

    /**
     * 保存和更新
     *
     * @param deptForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public ResultVO<Map<String, Object>> save(@Valid DeptForm deptForm,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(100, bindingResult.getFieldError().getDefaultMessage());
        }
        DeptInfo deptInfo = new DeptInfo();

        if (deptForm.getId() != null) {
            deptInfo = deptService.findOne(deptForm.getId());
        }
        /**修改对应人员的部门编号**/
        if(deptForm.getDeptNo()!=deptInfo.getDeptNo()){
            personnelInfoService.updateByDeptNo(deptInfo.getDeptNo(),deptForm.getDeptNo());
        }
        BeanUtils.copyProperties(deptForm, deptInfo);
        if (deptForm.getId()==null){
            deptInfo.setNextNo("001");
        }
        deptService.save(deptInfo);
        return ResultVOUtil.success();
    }

    /**
     * 部门列表
     *
     * @param map
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("dept:list")
    public ModelAndView list(Map<String, Object> map,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort());

        map.put("pageId", 18);
        map.put("pageTitle", "部门列表");
        Page<DeptInfo> deptInfoPage = deptService.findAll(pageRequest);
        map.put("pageContent", deptInfoPage);
        map.put("size", size);
        map.put("currentPage", page);
        map.put("url", "/oa/dept/list.html");
        return new ModelAndView("view/deptInfo", map);
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResultVO<Map<String, Object>> delete(@RequestParam(value = "id") Integer id){
        DeptInfo deptInfo = deptService.findOne(id);
        /**修改部门信息**/
        personnelInfoService.updateByDeptNo(deptInfo.getDeptNo(),null);
        deptService.delete(id);
        return ResultVOUtil.success();
    }
}
