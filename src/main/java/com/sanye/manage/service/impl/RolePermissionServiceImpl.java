package com.sanye.manage.service.impl;

import com.sanye.manage.DTO.PageDTO;
import com.sanye.manage.DTO.RoleDTO;
import com.sanye.manage.dataobject.Permission;
import com.sanye.manage.dataobject.Role;
import com.sanye.manage.dataobject.RolePermission;
import com.sanye.manage.repository.PermissionRepository;
import com.sanye.manage.repository.RolePermissionRepository;
import com.sanye.manage.repository.RoleRepository;
import com.sanye.manage.service.RolePermissionService;
import com.sanye.manage.utils.SortTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-15 下午 12:23
 */
@Service
@Transactional
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Override
    public RoleDTO findOne(Integer id) {
        RoleDTO roleDTO = new RoleDTO();
        List<Permission> permissionList = null;
        List<RolePermission> list = rolePermissionRepository.findAllByRoleId(id);
        Role role = new Role();
        role = roleRepository.findOne(id);
        if (list!=null){
            permissionList = list.stream().map(e ->
                    permissionRepository.findOne(e.getPermissionId())
            ).collect(Collectors.toList());
        }
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        roleDTO.setDescription(role.getDescription());
        roleDTO.setPermissionList(permissionList);
        return roleDTO;
    }

    @Override
    public PageDTO<RoleDTO> findRoleDTO(Pageable pageable) {
        RoleDTO roleDTO = new RoleDTO();
        List<RoleDTO> roleDTOList = null;
        PageDTO<RoleDTO> pageDTO = new PageDTO<>();
        Page<Role> rolePage = roleRepository.findAll(pageable);
        if (!rolePage.getContent().isEmpty()){
            roleDTOList =rolePage.getContent().stream().map(e ->
                findOne(e.getId())
            ).collect(Collectors.toList());
        }
        pageDTO.setPageContent(roleDTOList);
        pageDTO.setTotalPages(rolePage.getTotalPages());
        return pageDTO;
    }

    @Override
    public Page<Permission> findPermission(Pageable pageable) {
        return permissionRepository.findAll(pageable);
    }

    @Override
    public Map<String, Object> deleteRolePermission(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("code",0);
        map.put("message","删除成功");
        permissionRepository.delete(id);
        return map;
    }

    @Override
    public Map<String, Object> deleteRole(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("code",0);
        map.put("message","删除成功");
        List<RolePermission> list = rolePermissionRepository.findAllByRoleId(id);
        if (!list.isEmpty()){
            for (int i=0;i<list.size();i++){
                rolePermissionRepository.delete(list.get(i).getId());
            }
        }
        roleRepository.delete(id);
        return map;
    }

    @Override
    public Map<String, Object> deletePermission(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("code",0);
        map.put("message","删除成功");
        List<RolePermission> list = rolePermissionRepository.findAllByPermissionId(id);
        if (!list.isEmpty()){
            for (int i=0;i<list.size();i++){
                rolePermissionRepository.delete(list.get(i).getId());
            }
        }
        permissionRepository.delete(id);
        return map;
    }

    @Override
    public Role saveRole(Role role) {

        return roleRepository.save(role);
    }

    @Override
    public Permission savePermission(Permission permission) {

        return permissionRepository.save(permission);
    }

    @Override
    public Permission findPermissionById(Integer id) {
        return permissionRepository.findOne(id);
    }

    @Override
    public List<Permission> findAllPermission() {
        return permissionRepository.findAll();
    }

    @Override
    public Role findRoleById(Integer id) {
        return roleRepository.findOne(id);
    }

    @Override
    public RolePermission save(Integer roleId, List<Integer> permissionId) {
        List<RolePermission> rolePermissionList = rolePermissionRepository.findAllByRoleId(roleId);
        if (!rolePermissionList.isEmpty()){
            for (int i=0;i<rolePermissionList.size();i++){
                rolePermissionRepository.delete(rolePermissionList.get(i).getId());
            }
        }
        for (int i=0;i<permissionId.size();i++){
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermissionId(permissionId.get(i));
            rolePermission.setRoleId(roleId);
            rolePermissionRepository.save(rolePermission);
        }
        return null;
    }

    @Override
    public List<Role> findAllRole() {
        return roleRepository.findAll(SortTools.basicSort());
    }
}
