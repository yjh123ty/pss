package com.yjh.pss.service;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yjh.pss.domain.Depot;
import com.yjh.pss.domain.Employee;
import com.yjh.pss.domain.Product;
import com.yjh.pss.domain.StockIncomeBill;
import com.yjh.pss.domain.StockIncomeBillItem;
import com.yjh.pss.domain.Supplier;

public class StockIncomeBillServiceTest extends BaseServiceTest{
	@Autowired
	private IStockIncomeBillService stockIncomeBillService;
	@Autowired
	private IEmployeeService employeeService;
	@Autowired
	private IProductService productService;
	@Autowired
	private ISupplierService supplierService;
	
	@Test
	public void all() throws Exception {
		System.out.println(stockIncomeBillService.getAll().size());
	}
	
	@Test
	public void delete() throws Exception {
		stockIncomeBillService.delete(100L);
	}
	
	// 一次性保存1个入库单，2个入库明细
	@Test
	public void save() throws Exception {
		// 一方：1个入库单
		StockIncomeBill stockIncomeBill = new StockIncomeBill();
		stockIncomeBill.setVdate(new Date());
		stockIncomeBill.setInputUser(employeeService.get(99L));
		stockIncomeBill.setSupplier(supplierService.get(2L));
		stockIncomeBill.setKeeper(employeeService.get(2L));
		stockIncomeBill.setDepot(new Depot(1L));
		
		// 多方：2个入库明细
		List<StockIncomeBillItem> items = new ArrayList<StockIncomeBillItem>();
		
		StockIncomeBillItem billItem = new StockIncomeBillItem();
		billItem.setDescs("备注1");
		billItem.setNum(new BigDecimal(1));
		billItem.setPrice(new BigDecimal(1));
		billItem.setProduct(productService.get(1L));
		items.add(billItem);
		
		StockIncomeBillItem billItem2 = new StockIncomeBillItem();
		billItem2.setDescs("备注2");
		billItem2.setNum(new BigDecimal(2));
		billItem2.setPrice(new BigDecimal(2));
		billItem2.setProduct(productService.get(2L));
		items.add(billItem2);
		
		BigDecimal totalAmount = new BigDecimal(0);
		BigDecimal totalNum = new BigDecimal(0);
		for (StockIncomeBillItem stockIncomeBillItem : items) {
			//建立多方与一方的关联
			stockIncomeBillItem.setBill(stockIncomeBill);
			
			stockIncomeBillItem.setAmount(stockIncomeBillItem.getNum().multiply(stockIncomeBillItem.getPrice()));
			totalAmount = totalAmount.add(stockIncomeBillItem.getAmount());
			totalNum = totalNum.add(stockIncomeBillItem.getNum());
		}
		stockIncomeBill.setTotalAmount(totalAmount);
		stockIncomeBill.setTotalNum(totalNum);
		stockIncomeBill.setStatus(0);
		//设置一方到多方的关系
		stockIncomeBill.setItems(items);
		
		//级联保存
		stockIncomeBillService.save(stockIncomeBill);
		
	}
	
	@Test
	public void update() throws Exception {
		StockIncomeBill stockIncomeBill = stockIncomeBillService.get(103L);
		stockIncomeBillService.update(stockIncomeBill);
	}
	
}
