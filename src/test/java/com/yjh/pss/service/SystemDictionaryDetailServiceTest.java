package com.yjh.pss.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yjh.pss.domain.SystemDictionaryDetail;

public class SystemDictionaryDetailServiceTest extends BaseServiceTest{
	@Autowired
	private ISystemDictionaryDetailService systemDictionaryDetailService;

	@Test
	public void all() throws Exception {
		System.out.println(systemDictionaryDetailService.getAll().size());
	}
	
	@Test
	public void delete() throws Exception {
		systemDictionaryDetailService.delete(100L);
	}
	
	@Test
	public void save() throws Exception {
		SystemDictionaryDetail systemDictionaryDetail = new SystemDictionaryDetail();
		systemDictionaryDetail.setName("xxx");
		systemDictionaryDetailService.save(systemDictionaryDetail);
	}
	
	@Test
	public void update() throws Exception {
		SystemDictionaryDetail systemDictionaryDetail = systemDictionaryDetailService.get(103L);
		systemDictionaryDetail.setName("song");
		systemDictionaryDetailService.update(systemDictionaryDetail);
	}
	
}
