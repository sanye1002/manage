package com.sanye.manage.controller;

import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.DTO.RoleDTO;
import com.sanye.manage.VO.ResultVO;
import com.sanye.manage.dataobject.Permission;
import com.sanye.manage.dataobject.Role;
import com.sanye.manage.form.RoleForm;
import com.sanye.manage.service.RolePermissionService;
import com.sanye.manage.utils.ResultVOUtil;
import com.sanye.manage.utils.SortTools;
import lombok.extern.slf4j.Slf4j;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-15 下午 4:13
 */
@Controller
@RequestMapping("/oa/role")
@RequiresAuthentication
public class RoleController {

    @Autowired
    private RolePermissionService rolePermissionService;

    @GetMapping("/index")
    @RequiresPermissions("role:add")
    public ModelAndView index(Map<String, Object> map,
                              @RequestParam(value = "id", defaultValue = "") Integer id) {

        RoleDTO roleDTO = new RoleDTO();
        List<Permission> permissionList = new ArrayList<>();
        if (id != null) {
            roleDTO = rolePermissionService.findOne(id);
            map.put("pageTitle", "角色编辑");
            permissionList = rolePermissionService.findAllPermission();
            permissionList.removeAll(roleDTO.getPermissionList());
            map.put("checkPermissionList",roleDTO.getPermissionList());
        } else {
            roleDTO.setId(0);
            map.put("pageTitle", "角色添加");
            permissionList = rolePermissionService.findAllPermission();
        }

        map.put("pageId", 23);
        map.put("roleDTO", roleDTO);
        map.put("permissionList", permissionList);

        return new ModelAndView("view/roleAdd", map);
    }

    @GetMapping("/list")
    @RequiresPermissions("role:list")
    public ModelAndView list(Map<String, Object> map,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = new PageRequest(page-1,size);
        PageDTO<RoleDTO> pageDTO = rolePermissionService.findRoleDTO(pageRequest);
        map.put("pageId", 24);
        map.put("pageTitle", "角色列表");
        map.put("pageContent", pageDTO);
        map.put("size", size);
        map.put("currentPage", page);
        map.put("url", "/oa/role/list.html");
        return new ModelAndView("view/roleList");
    }

    @ResponseBody
    @PostMapping("/save")
    public ResultVO<Map<String, Object>> save(@RequestBody RoleForm roleForm,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(100, bindingResult.getFieldError().getDefaultMessage());
        }
        Role role = new Role();
        role.setId(roleForm.getId());
        role.setName(roleForm.getName());
        role.setDescription(roleForm.getDescription());
        Role result = rolePermissionService.saveRole(role);
        rolePermissionService.save(result.getId(),roleForm.getIdList());
        return ResultVOUtil.success();
    }
}
