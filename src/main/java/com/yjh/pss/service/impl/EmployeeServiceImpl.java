package com.yjh.pss.service.impl;

import java.util.List;

import com.yjh.pss.domain.Employee;
import com.yjh.pss.service.IEmployeeService;

public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements IEmployeeService{

	/**
	 * 根据用户名查找用户是否存在
	 */
	@Override
	public boolean findByUsername(String username) {
		// count :单行单列
		String hql = "select count(o) from Employee o where o.username=?";
		List<Long> countList = baseDao.findByHql(hql, username);	//修改基类实现类中的baseDao为protected,子类才能调用
		// 不能判断countList.size()大于0
		Long count = countList.get(0);// 永远有值0-n
		if (count.intValue() > 0) {// 用户名存在,这里返回false,方便前台验证
			return false;
		}
		return true;
	}
	
	/**
	 * 登录验证
	 * @param username
	 * @param password
	 * @return
	 */
	@Override
	  public Employee findByLogin(String username, String password) {
	    String hql = "select o from Employee o where o.username=? and o.password=?";
	    List<Employee> list = baseDao.findByHql(hql, username, password);
	    if (list.size() > 0) {
	      return list.get(0);
	    }
	    return null;
	  }

	@Override
	public List<String> getAllPermissionsMethods() {
		return baseDao.findCacheByHql("select o.method from Permission o");
	}

	@Override
	public List<String> findMethodsByUserInSession(Long userInSessionId) {
		return baseDao.findByHql("select rp.method from Employee e join e.roles r join r.permissions rp where e.id = ?", userInSessionId);
	}

	/**
	 * 采购人员
	 */
	@Override
	public List<Employee> getBuyers() {
		return baseDao.findByHql("select o from Employee o where o.department.name = ? ", "采购部");
	}
	
}
