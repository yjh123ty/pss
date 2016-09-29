package com.yjh.pss.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yjh.pss.domain.StockIncomeBillItem;

public class StockIncomeBillItemServiceTest extends BaseServiceTest{
	@Autowired
	private IStockIncomeBillItemService stockIncomeBillItemService;

	@Test
	public void all() throws Exception {
		System.out.println(stockIncomeBillItemService.getAll().size());
	}
	
	@Test
	public void delete() throws Exception {
		stockIncomeBillItemService.delete(100L);
	}
	
	@Test
	public void save() throws Exception {
		StockIncomeBillItem stockIncomeBillItem = new StockIncomeBillItem();
		stockIncomeBillItemService.save(stockIncomeBillItem);
	}
	
	@Test
	public void update() throws Exception {
		StockIncomeBillItem stockIncomeBillItem = stockIncomeBillItemService.get(103L);
		stockIncomeBillItemService.update(stockIncomeBillItem);
	}
	
}
