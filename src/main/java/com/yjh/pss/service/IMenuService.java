package com.yjh.pss.service;

import java.util.List;

import com.yjh.pss.domain.Employee;
import com.yjh.pss.domain.Menu;

public interface IMenuService extends IBaseService<Menu> {
	// 获取当前登录用户的一级菜单和包含的二级菜单
	  List<Menu> findByLoginUser(Employee loginUser);
}
