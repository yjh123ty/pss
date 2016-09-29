package com.yjh.pss.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yjh.pss.domain.PurchaseBill;

public class PurchaseBillServiceTest extends BaseServiceTest{
	@Autowired
	private IPurchaseBillService purchaseBillService;

	@Test
	public void all() throws Exception {
		System.out.println(purchaseBillService.getAll().size());
	}
	
	@Test
	public void delete() throws Exception {
		purchaseBillService.delete(100L);
	}
	
	@Test
	public void save() throws Exception {
		PurchaseBill purchaseBill = new PurchaseBill();
		purchaseBillService.save(purchaseBill);
	}
	
	@Test
	public void update() throws Exception {
		PurchaseBill purchaseBill = purchaseBillService.get(103L);
		purchaseBillService.update(purchaseBill);
	}
	
}
