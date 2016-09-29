package com.yjh.pss.domain;

import java.util.HashSet;
import java.util.Set;

public class Role {
	private Long id;
	private String name;
	
	//当前角色拥有的权限列表
	private Set<Permission> permissions = new HashSet<>();
	// 当前角色拥有的菜单列表
    private Set<Menu> menus = new HashSet<Menu>();
	
	public Role() {
		
	}
	
	public Role(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	public Set<Menu> getMenus() {
		return menus;
	}
	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
