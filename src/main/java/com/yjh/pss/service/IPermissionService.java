package com.yjh.pss.service;

import java.util.List;

import com.yjh.pss.domain.Employee;
import com.yjh.pss.domain.Permission;

public interface IPermissionService extends IBaseService<Permission> {
	public List<String> getAllPermissionMethods();
	
	public List<String> findPermissionMethodsByLoginUser(Employee loginUser);
}
