package com.yjh.pss.service;

import java.util.List;

import com.yjh.pss.domain.Employee;

public interface IEmployeeService extends IBaseService<Employee>{
	//验证用户名是否重复
	boolean findByUsername(String username);
	
	//验证登录
	public Employee findByLogin(String username, String password);
	
	//得到需要拦截的action的方法，即是在permission表中的method字段都需要拦截
	List<String> getAllPermissionsMethods();
	
	//得到当前用户的可以访问的权限
	List<String> findMethodsByUserInSession(Long userInSessionId);
	
	//获取采购员，用于采购订单
	List<Employee> getBuyers();
}
