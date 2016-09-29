package com.yjh.pss.service;

import static org.junit.Assert.*;import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yjh.pss.domain.Employee;
import com.yjh.pss.domain.Menu;

public class MenuServiceTest extends BaseServiceTest{
	@Autowired
	private IMenuService menuService;
	@Autowired
	private IEmployeeService employeeService;

	@Test
	public void all() throws Exception {
		Employee employee = employeeService.get(1L);
		System.out.println(employee.toString());
		List<Menu> parentMenus = menuService.findByLoginUser(employee);
		System.out.println(Arrays.asList(parentMenus));
//		for (Menu parent : parentMenus) {
//			// 一级菜单
//			System.out.println(parent);
//			// 二级菜单
//			List<Menu> children = parent.getChildren();
//			for (Menu menu : children) {
//				System.out.println(menu);
//			}
//		}


	}
	
	@Test
	public void delete() throws Exception {
		menuService.delete(19L);
	}
	
	@Test
	public void save() throws Exception {
		Menu menu = new Menu();
		menu.setName("xxx");
		menuService.save(menu);
	}
	
	@Test
	public void update() throws Exception {
		Menu menu = menuService.get(18L);
		menu.setName("song");
		menuService.update(menu);
	}
	
}
