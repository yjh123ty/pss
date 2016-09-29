package com.yjh.pss.service.impl;

import java.util.List;

import com.yjh.pss.domain.Employee;
import com.yjh.pss.domain.Permission;
import com.yjh.pss.service.IPermissionService;

public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements IPermissionService {
	@Override
	public List<String> getAllPermissionMethods() {
		return baseDao.findCacheByHql("select o.method from Permission o");
	}

	@Override
	public List<String> findPermissionMethodsByLoginUser(Employee loginUser) {
		return baseDao.findByHql("select distinct p.method from Employee e join e.roles r join r.permissions p where e.id=?",loginUser.getId());
	}
}
