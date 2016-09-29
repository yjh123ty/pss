package com.yjh.pss.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yjh.pss.domain.${domain};

public class ${domain}ServiceTest extends BaseServiceTest{
	@Autowired
	private I${domain}Service ${lowerDomain}Service;

	@Test
	public void all() throws Exception {
		System.out.println(${lowerDomain}Service.getAll().size());
	}
	
	@Test
	public void delete() throws Exception {
		${lowerDomain}Service.delete(100L);
	}
	
	@Test
	public void save() throws Exception {
		${domain} ${lowerDomain} = new ${domain}();
		${lowerDomain}.setName("xxx");
		${lowerDomain}Service.save(${lowerDomain});
	}
	
	@Test
	public void update() throws Exception {
		${domain} ${lowerDomain} = ${lowerDomain}Service.get(103L);
		${lowerDomain}.setName("song");
		${lowerDomain}Service.update(${lowerDomain});
	}
	
}
