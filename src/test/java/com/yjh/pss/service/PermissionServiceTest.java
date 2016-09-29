package com.yjh.pss.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yjh.pss.domain.Permission;

public class PermissionServiceTest extends BaseServiceTest{
	@Autowired
	private IPermissionService permissionService;

	@Test
	public void all() throws Exception {
		System.out.println(permissionService.getAll());
	}
	
	@Test
	public void delete() throws Exception {
		permissionService.delete(100L);
	}
	
	@Test
	public void save() throws Exception {
		Permission permission = new Permission();
		permission.setName("xxx");
		permissionService.save(permission);
	}
	
	@Test
	public void update() throws Exception {
		Permission permission = permissionService.get(103L);
		permission.setName("song");
		permissionService.update(permission);
	}
	
}
