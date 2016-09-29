package com.yjh.pss.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yjh.pss.domain.ProductType;

public class ProductTypeServiceTest extends BaseServiceTest{
	@Autowired
	private IProductTypeService productTypeService;

	@Test
	public void all() throws Exception {
		System.out.println(productTypeService.getAll().size());
	}
	
	@Test
	public void delete() throws Exception {
		productTypeService.delete(100L);
	}
	
	@Test
	public void save() throws Exception {
		ProductType productType = new ProductType();
		productType.setName("xxx");
		productTypeService.save(productType);
	}
	
	@Test
	public void update() throws Exception {
		ProductType productType = productTypeService.get(103L);
		productType.setName("song");
		productTypeService.update(productType);
	}
	
}
