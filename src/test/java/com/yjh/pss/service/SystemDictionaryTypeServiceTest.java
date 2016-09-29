package com.yjh.pss.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yjh.pss.domain.SystemDictionaryType;

public class SystemDictionaryTypeServiceTest extends BaseServiceTest{
	@Autowired
	private ISystemDictionaryTypeService systemDictionaryTypeService;

	@Test
	public void all() throws Exception {
		System.out.println(systemDictionaryTypeService.getAll().size());
	}
	
	@Test
	public void delete() throws Exception {
		systemDictionaryTypeService.delete(100L);
	}
	
	@Test
	public void save() throws Exception {
		SystemDictionaryType systemDictionaryType = new SystemDictionaryType();
		systemDictionaryType.setName("xxx");
		systemDictionaryTypeService.save(systemDictionaryType);
	}
	
	@Test
	public void update() throws Exception {
		SystemDictionaryType systemDictionaryType = systemDictionaryTypeService.get(103L);
		systemDictionaryType.setName("song");
		systemDictionaryTypeService.update(systemDictionaryType);
	}
	
}
