package com.sanye.manage.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sanye.manage.dataobject.Permission;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-16 上午 11:14
 */
@Data
public class RoleForm {

    private Integer id;
    /**
     * 角色名
     **/
    private String name;
    /**
     * 角色说明
     **/
    private String description;

    private Integer level;

    private List<Integer> idList;




}
