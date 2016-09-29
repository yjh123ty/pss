package com.yjh.pss.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yjh.pss.domain.Supplier;

public class SupplierServiceTest extends BaseServiceTest{
	@Autowired
	private ISupplierService supplierService;

	@Test
	public void all() throws Exception {
		System.out.println(supplierService.getAll().size());
	}
	
	@Test
	public void delete() throws Exception {
		supplierService.delete(100L);
	}
	
	@Test
	public void save() throws Exception {
		Supplier supplier = new Supplier();
		supplier.setName("xxx");
		supplierService.save(supplier);
	}
	
	@Test
	public void update() throws Exception {
		Supplier supplier = supplierService.get(103L);
		supplier.setName("song");
		supplierService.update(supplier);
	}
	
}
