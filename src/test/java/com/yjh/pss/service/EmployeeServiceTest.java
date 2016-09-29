package com.yjh.pss.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yjh.pss.domain.Employee;

public class EmployeeServiceTest extends BaseServiceTest{
	@Autowired
	private IEmployeeService employeeService;

	@Test
	public void all() throws Exception {
		System.out.println(employeeService.getAll());
	}
	
	@Test
	public void delete() throws Exception {
		employeeService.delete(100L);
	}
	
	@Test
	public void save() throws Exception {
		Employee employee = new Employee();
		employee.setPassword("111");
		employee.setUsername("xxx");
		employeeService.save(employee);
	}
	
	@Test
	public void update() throws Exception {
		Employee employee = employeeService.get(103L);
		employee.setUsername("song");
		employeeService.update(employee);
	}
	
	@Test
	public void findUsername() throws Exception {
		boolean b = employeeService.findByUsername("admin1");
		System.out.println("查找是否存在员工："+b);
	}

}
