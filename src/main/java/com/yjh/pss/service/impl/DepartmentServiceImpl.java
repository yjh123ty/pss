package com.yjh.pss.service.impl;

import java.util.List;

import com.yjh.pss.domain.Department;
import com.yjh.pss.service.IDepartmentService;

public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements IDepartmentService {

	@Override
	public Department findByName(String name) {
		List<Department> dList = baseDao.findByHql("select o from Department o where o.name=?", name);
		if (dList.size() > 0) {
			return dList.get(0);
		}
		return null;
	}

}
