package com.yjh.pss.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yjh.pss.domain.Product;
import com.yjh.pss.domain.ProductType;
import com.yjh.pss.domain.SystemDictionaryDetail;

public class ProductServiceTest extends BaseServiceTest{
	@Autowired
	private IProductService productService;
	@Autowired
	private IProductTypeService productTypeService;
	@Autowired
	private ISystemDictionaryDetailService systemDictionaryDetailService; 

	@Test
	public void all() throws Exception {
		System.out.println(productService.getAll().size());
	}
	
	@Test
	public void delete() throws Exception {
		productService.delete(100L);
	}
	
	@Test
	public void save() throws Exception {
		Product product = new Product();
		product.setName("xxx");
		SystemDictionaryDetail unit = systemDictionaryDetailService.get(3L);
		SystemDictionaryDetail brand = systemDictionaryDetailService.get(1L);
		ProductType type = productTypeService.get(2L);
		product.setType(type);
		product.setBrand(brand);
		product.setUnit(unit);
		productService.save(product);
	}
	
	@Test
	public void update() throws Exception {
		Product product = productService.get(103L);
		product.setName("song");
		productService.update(product);
	}
	
}
