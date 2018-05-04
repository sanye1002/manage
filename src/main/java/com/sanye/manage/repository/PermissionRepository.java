package com.sanye.manage.repository;

import com.sanye.manage.dataobject.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-15 上午 10:54
 */
public interface PermissionRepository extends JpaRepository<Permission,Integer> {

}
