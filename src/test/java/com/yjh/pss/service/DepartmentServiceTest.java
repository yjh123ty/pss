package com.yjh.pss.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yjh.pss.domain.Department;

public class DepartmentServiceTest extends BaseServiceTest{
	@Autowired
	private IDepartmentService departmentService;

	@Test
	public void all() throws Exception {
		System.out.println(departmentService.getAll().size());
	}
	
	@Test
	public void delete() throws Exception {
		departmentService.delete(100L);
	}
	
	@Test
	public void save() throws Exception {
		Department department = new Department();
		department.setName("xxx");
		departmentService.save(department);
	}
	
	@Test
	public void update() throws Exception {
		Department department = departmentService.get(103L);
		department.setName("song");
		departmentService.update(department);
	}
	
}
