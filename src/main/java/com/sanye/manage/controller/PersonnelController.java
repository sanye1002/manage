package com.sanye.manage.controller;

import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.DTO.PersonnelInfoDTO;
import com.sanye.manage.VO.ResultVO;
import com.sanye.manage.dataobject.DeptInfo;
import com.sanye.manage.dataobject.PersonnelInfo;
import com.sanye.manage.dataobject.Role;
import com.sanye.manage.dataobject.UserInfo;
import com.sanye.manage.enums.ResultEnum;
import com.sanye.manage.form.PersonnelForm;
import com.sanye.manage.service.DeptService;
import com.sanye.manage.service.PersonnelInfoService;
import com.sanye.manage.service.RolePermissionService;
import com.sanye.manage.service.UserService;
import com.sanye.manage.utils.GetTimeUtil;
import com.sanye.manage.utils.ResultVOUtil;
import com.sanye.manage.utils.ShiroGetSession;
import com.sanye.manage.utils.SortTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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
import java.util.stream.Collectors;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-10 下午 12:59
 */
@Controller
@RequestMapping("/oa/personnel")
@RequiresAuthentication
public class PersonnelController {

    @Autowired
    private PersonnelInfoService personnelInfoService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private UserService userService;

    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * 列表
     *
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("personnel:list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             @RequestParam(value = "status", defaultValue = "1") Integer status,
                             Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort());
        PersonnelInfo personnelInfo = personnelInfoService.findByUserId(ShiroGetSession.getUserInfo().getId());
        PageDTO<PersonnelInfoDTO> pageDTO = personnelInfoService.findAllByShowStatusAndStatus(pageRequest, status, personnelInfo.getDeptNo());
        map.put("pageId", 12);
        map.put("status", status);
        map.put("pageTitle", "工作人员列表");
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/personnel/list.html");
        map.put("size", size);
        map.put("currentPage", page);

        return new ModelAndView("view/personnelList", map);
    }


    @PostMapping("/save")
    @ResponseBody
    public ResultVO<Map<String, Object>> save(@Valid PersonnelForm personnelForm,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(100, bindingResult.getFieldError().getDefaultMessage());
        }
        PersonnelInfo personnelInfo = new PersonnelInfo();
        UserInfo userInfo = new UserInfo();
        if (personnelForm.getPersonnelId() != null) {
            personnelInfo = personnelInfoService.findById(personnelForm.getPersonnelId());
            userInfo = userService.findOne(personnelInfo.getUserId());
            userInfo.setUpdateDate(GetTimeUtil.getTime());
            personnelInfo.setUpdateDate(GetTimeUtil.getTime());
        }
        BeanUtils.copyProperties(personnelForm, personnelInfo);
        BeanUtils.copyProperties(personnelForm, userInfo);
        if (personnelForm.getPersonnelId() == null) {
            userInfo.setCreateDate(GetTimeUtil.getTime());
            userInfo.setUpdateDate(GetTimeUtil.getTime());
            userInfo.setUserType("personnel");
            userInfo.setShowStatus(1);
            userInfo = userService.save(userInfo);
            personnelInfo.setUserId(userInfo.getId());
            personnelInfo.setShowStatus(1);
            personnelInfo.setCreateDate(GetTimeUtil.getTime());
            personnelInfo.setUpdateDate(GetTimeUtil.getTime());

        }
        userService.save(userInfo);
        personnelInfoService.save(personnelInfo);


        return ResultVOUtil.success();
    }


    /**
     * 首页
     * @param map
     * @return
     */
    @GetMapping("/index")
    @RequiresPermissions("personnel:add")
    public ModelAndView index(Map<String, Object> map,
                              @RequestParam(value = "id", defaultValue = "0") Integer id) {
        PersonnelInfo personnelInfo = new PersonnelInfo();
        UserInfo userInfo = new UserInfo();
        if (id != 0) {
            personnelInfo = personnelInfoService.findById(id);
            userInfo = userService.findOne(personnelInfo.getUserId());
        }
        /**用户信息**/

        String deptNo = deptService.findByDeptNo(personnelInfoService.findByUserId( ShiroGetSession.getUserInfo().getId()).getDeptNo()).getDeptNo();
        /**部门信息**/
        List<DeptInfo> deptInfoList = deptService.findByUser(deptNo);
        /**权限**/
        List<Role> roleList = rolePermissionService.findAllRole();
        map.put("pageId", 11);
        map.put("pageTitle", "工作人员添加");
        map.put("deptInfoList", deptInfoList);
        map.put("personnelInfo", personnelInfo);
        map.put("roleList", roleList);
        map.put("userInfo", userInfo);
        return new ModelAndView("view/personnelAdd", map);
    }

    /**
     * 手机查找
     *
     * @param phone
     * @return
     */
    @PostMapping("/phone")
    @ResponseBody
    public ResultVO<Map<String, Object>> phone(@RequestParam(value = "phone") String phone) {
        Map<String, Object> map = new HashMap<>();
        map = userService.findAllByPhone(phone);
        return ResultVOUtil.success(map);
    }

    /**
     * 删除根据ID
     *
     * @param id
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public ResultVO<Map<String, Object>> delete(@RequestParam(value = "id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        if (personnelInfoService.findById(id) == null) {
            return ResultVOUtil.error(100, "人员不存在！");
        }
        map.put("message", ResultEnum.SUCCESS.getMessage());
        personnelInfoService.delete(id);
        return ResultVOUtil.success(map);
    }

}
