package com.yjh.pss.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yjh.pss.domain.Role;

public class RoleServiceTest extends BaseServiceTest{
	@Autowired
	private IRoleService roleService;

	@Test
	public void all() throws Exception {
		System.out.println(roleService.getAll().size());
	}
	
	@Test
	public void delete() throws Exception {
		roleService.delete(100L);
	}
	
	@Test
	public void save() throws Exception {
		Role role = new Role();
		role.setName("xxx");
		roleService.save(role);
	}
	
	@Test
	public void update() throws Exception {
		Role role = roleService.get(103L);
		role.setName("song");
		roleService.update(role);
	}
	
}
