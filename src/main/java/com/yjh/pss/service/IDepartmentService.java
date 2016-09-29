package com.yjh.pss.service;

import com.yjh.pss.domain.Department;

public interface IDepartmentService extends IBaseService<Department> {

	Department findByName(String name);

}
