package com.yjh.pss.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.yjh.pss.domain.Employee;
import com.yjh.pss.domain.Menu;
import com.yjh.pss.service.IMenuService;

public class MenuServiceImpl extends BaseServiceImpl<Menu> implements IMenuService {

	@Override
	public List<Menu> findByLoginUser(Employee loginUser) {
		//方式一：两条HQL
//		// 1、查询当前登录用户的一级菜单(m.parent is null)
//		
//		String hql = 
//			"select distinct m from Employee o join o.roles r join r.menus m where o.id =? and m.parent is null";
//		// 2、通过当前用户的id，拿到当前用户对象.roles，再通过roles.menus 拿到可访问的菜单（List集合）
//		List<Menu> parentMenus = baseDao.findByHql(hql, loginUser.getId());
//		
//		// 3、查询当前登录用户的二级菜单（查询上面返回的菜单集合中的每一个子菜单，因此这里需要遍历）
//		for (Menu parent : parentMenus) {
//			hql = 
//			"select distinct m from Employee o join o.roles r join r.menus m where o.id =? and m.parent.id =?";
//			List<Menu> children = baseDao.findByHql(hql, loginUser.getId(), parent.getId());
//		    parent.setChildren(children);
//		}
//		return parentMenus;
		
		//方式二：一条HQL
		//初始化一级菜单
		List<Menu> list=new ArrayList<Menu>();
		
		//初始化二级菜单
		String hql="select distinct m from Employee e join e.roles r join r.menus m where e.id=?";
		List<Menu> menus = baseDao.findByHql(hql, loginUser.getId());
		
		for (Menu menu : menus) {
			Menu parent = menu.getParent();
			//parent为空是一级菜单
			if(parent==null){
				list.add(menu);
			}
		}
		for (Menu menu : menus) {
			Menu parent = menu.getParent();
			//parent不为空，则为二级菜单
			if(parent!=null&&list.contains(parent)){
				for (Menu parent2 : menus) {
					if(parent2.getId().equals(parent.getId())){
						parent2.getChildren().add(menu);
					}
				}
			}
		}
		return list;
		
	}
	
	

}
