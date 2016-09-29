package com.yjh.pss.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yjh.pss.domain.ProductStock;

public class ProductStockServiceTest extends BaseServiceTest{
	@Autowired
	private IProductStockService productStockService;

	@Test
	public void all() throws Exception {
		System.out.println(productStockService.getAll().size());
	}
	
	@Test
	public void delete() throws Exception {
		productStockService.delete(100L);
	}
	
	@Test
	public void save() throws Exception {
		ProductStock productStock = new ProductStock();
		productStockService.save(productStock);
	}
	
	@Test
	public void update() throws Exception {
		ProductStock productStock = productStockService.get(103L);
		productStockService.update(productStock);
	}
	
}
