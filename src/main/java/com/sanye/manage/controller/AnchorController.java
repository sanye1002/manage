package com.sanye.manage.controller;

import com.sanye.manage.DTO.AnchorDTO;
import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.VO.ResultVO;
import com.sanye.manage.dataobject.AnchorInfo;
import com.sanye.manage.dataobject.PlatformInfo;
import com.sanye.manage.dataobject.Role;
import com.sanye.manage.dataobject.UserInfo;
import com.sanye.manage.form.AnchorForm;
import com.sanye.manage.form.AnchorUserForm;
import com.sanye.manage.service.AnchorService;
import com.sanye.manage.service.PlatformService;
import com.sanye.manage.service.RolePermissionService;
import com.sanye.manage.service.UserService;
import com.sanye.manage.utils.GetTimeUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-16 下午 7:47
 */
@Controller
@RequestMapping("/oa/anchor")
@Slf4j
@RequiresAuthentication
public class AnchorController {
    @Autowired
    private UserService userService;

    @Autowired
    private PlatformService platformService;

    @Autowired
    private AnchorService anchorService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @GetMapping("/list")
    @RequiresPermissions("anchor:list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             @RequestParam(value = "status", defaultValue = "1") Integer status,
                             @RequestParam(value = "platform", defaultValue = "0") Integer platform,
                             Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        PageDTO<AnchorDTO> pageDTO = anchorService.findAllByPlatformIdAndStatusAndShowStatus(pageRequest, platform, status);
        List<PlatformInfo> platformInfoList = platformService.findAll();
        map.put("pageId", 16);
        map.put("status", status);
        map.put("platform", platform);
        map.put("pageTitle", "主播人员列表");
        map.put("pageContent", pageDTO);
        map.put("platformInfoList", platformInfoList);
        map.put("url", "/oa/anchor/list.html");
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView("view/anchorList", map);
    }
    @RequiresPermissions("anchorUser:list")
    @GetMapping("/user/list")
    public ModelAndView listUser(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "10") Integer size,
                                 @RequestParam(value = "status", defaultValue = "1") Integer status,
                                 Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<UserInfo> pageDTO = userService.findAllByUserTypeAndStatusAndShowStatus(pageRequest, "anchor", status);
        map.put("pageId", 14);
        map.put("status", status);
        map.put("pageTitle", "主播用户列表");
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/anchor/user/list.html");
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView("view/anchorUserList", map);
    }
    @RequiresPermissions("anchorUser:add")
    @GetMapping("/user/index")
    public ModelAndView userIndex(@RequestParam(value = "id", defaultValue = "") Integer id,
                                  Map<String, Object> map) {
        UserInfo userInfo = new UserInfo();
        if (id != null) {
            userInfo = userService.findOne(id);
            map.put("edit", 1);
        } else {
            map.put("edit", 0);
        }
        /**平台**/
        List<PlatformInfo> platformInfoList = platformService.findAll();
        /**权限**/
        List<Role> roleList = rolePermissionService.findAllRole();
        map.put("pageId", 13);
        map.put("pageTitle", "主播用户添加");
        map.put("platformInfoList", platformInfoList);
        map.put("roleList", roleList);
        map.put("userInfo", userInfo);
        return new ModelAndView("view/anchorUserAdd");
    }
    @RequiresPermissions("anchor:add")
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "id", defaultValue = "") Integer id,
                              Map<String, Object> map) {
        AnchorInfo anchorInfo = new AnchorInfo();
        if (id != null) {
            anchorInfo = anchorService.findOne(id);
            map.put("edit", 1);
        } else {
            map.put("edit", 0);
        }
        List<UserInfo> anchorUser = userService.findAllByUserType("anchor");

        /**平台**/
        List<PlatformInfo> platformInfoList = platformService.findAll();
        /**权限**/
        List<Role> roleList = rolePermissionService.findAllRole();
        map.put("pageId", 15);
        map.put("pageTitle", "平台主播人员添加");
        map.put("anchorInfo", anchorInfo);
        map.put("platformInfoList", platformInfoList);
        map.put("roleList", roleList);
        map.put("anchorUser", anchorUser);
        return new ModelAndView("view/anchorAdd");
    }

    @PostMapping("/user/save")
    @ResponseBody
    public ResultVO<Map<String, Object>> saveAnchorUser(@Valid AnchorUserForm anchorUserForm,
                                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(100, bindingResult.getFieldError().getDefaultMessage());
        }
        UserInfo userInfo = new UserInfo();
        if (anchorUserForm.getId() != null) {
            userInfo = userService.findOne(anchorUserForm.getId());
        }
        BeanUtils.copyProperties(anchorUserForm, userInfo);
        if (anchorUserForm.getId() == null) {
            userInfo.setUserType("anchor");
            userInfo.setCreateDate(GetTimeUtil.getTime());
            userInfo.setUpdateDate(GetTimeUtil.getTime());
        } else {
            userInfo.setUpdateDate(GetTimeUtil.getTime());
        }
        userService.save(userInfo);
        return ResultVOUtil.success();
    }

    @PostMapping("/save")
    @ResponseBody
    public ResultVO<Map<String, Object>> saveAnchor(@Valid AnchorForm anchorForm,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(100, bindingResult.getFieldError().getDefaultMessage());
        }
        AnchorInfo anchorInfo = new AnchorInfo();
        if (anchorForm.getId() != null) {
            anchorInfo = anchorService.findOne(anchorForm.getId());
        }
        anchorInfo.setLiveId(anchorForm.getLiveId());
        anchorInfo.setPlatformId(anchorForm.getPlatformId());
        anchorInfo.setUserId(anchorForm.getUserId());
        anchorInfo.setName(userService.findOne(anchorForm.getUserId()).getName());
        anchorInfo.setNikeName(userService.findOne(anchorForm.getUserId()).getNikeName());
        if (anchorForm.getPlatformId()!=null){
            anchorInfo.setLivePlatform(platformService.findOne(anchorForm.getPlatformId()).getName());
        }
        if (anchorForm.getId() != null) {
            anchorInfo.setUpdateDate(GetTimeUtil.getTime());
        } else {
            anchorInfo.setShowStatus(1);
            anchorInfo.setCreateDate(GetTimeUtil.getTime());
            anchorInfo.setUpdateDate(GetTimeUtil.getTime());
        }
        anchorService.save(anchorInfo);
        return ResultVOUtil.success();
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResultVO<Map<String, Object>> deleteAnchor(Integer id){
        Map<String, Object> map = new HashMap<>();
        map=anchorService.delete(id);
        return ResultVOUtil.success(map);
    }
    @PostMapping("/user/delete")
    @ResponseBody
    public ResultVO<Map<String, Object>> deleteAnchorUser(Integer id){
        Map<String, Object> map = new HashMap<>();
        map=userService.delete(id);
        return ResultVOUtil.success(map);
    }

    @GetMapping("/congruent/{id}")
    public ModelAndView addCongruent(Map<String, Object> map,
                                     @PathVariable Integer id){
        if (id==null){
            return new ModelAndView("error/400", map);
        }
        UserInfo userInfo = userService.findOne(id);

        map.put("userInfo", userInfo);
        map.put("pageId", 13);
        map.put("pageTitle", "劳务合同上传");
        map.put("url","/oa/anchor/user/list.html");
        return new ModelAndView("view/congruentImgAdd", map);
    }

}
