package com.yjh.pss.service;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yjh.pss.dao.BaseDao;
import com.yjh.pss.domain.PurchaseBillItem;
import com.yjh.pss.query.PurchaseBillItemQuery;

public class PurchaseBillItemServiceTest extends BaseServiceTest{
	@Autowired
	private IPurchaseBillItemService purchaseBillItemService;

	@Autowired
	private BaseDao baseDao;

	/**
	 * 不带查询条件的hql，但无论如何，都会带一条默认的分组
	 */
	@Test	
	public void hql() throws Exception {
		// 第1条hql,无查询条件,有默认的分组条件
		String groupBy = " o.bill.supplier.name ";
//		String groupBy = " o.bill.buyer.username ";
//		String groupBy = " month(o.bill.vdate) ";
		String hql = "select "+ groupBy +",count(o) from PurchaseBillItem o group by " + groupBy;
		List<Object[]> list = baseDao.findByHql(hql);
		for(Object[] objects:list){
			System.out.println(Arrays.toString(objects));
			//在第二条hql的基础上，查询第二条hql
			hql = "select o from PurchaseBillItem o where "+ groupBy + " =?";
			List<PurchaseBillItem> items = baseDao.findByHql(hql, objects[0]);
			for (PurchaseBillItem purchaseBillItem : items) {
				System.out.println(purchaseBillItem.toString());
			}
			System.out.println("--------------");
		}
	}
	
	/**
	 * 带查询条件的报表hql
	 */
	@Test
	public void hql2() throws Exception {
		// 准备状态的查询条件
		String where = " where o.bill.status=0 ";
		// 第1条hql,无查询条件,有默认的分组条件
		String groupBy = " o.bill.supplier.name ";
//		String groupBy = " o.bill.buyer.username ";
//		String groupBy = " month(o.bill.vdate) ";
		String hql = "select "+ groupBy +",count(o) from PurchaseBillItem o " + where +" group by " + groupBy;
		List<Object[]> list = baseDao.findByHql(hql);
		for(Object[] objects:list){
			System.out.println(Arrays.toString(objects));
			//在第二条hql的基础上，查询第二条hql
			hql = "select o from PurchaseBillItem o where "+ groupBy + " =?" 
			+ where.replaceAll("where", "and");
			List<PurchaseBillItem> items = baseDao.findByHql(hql, objects[0]);
			for (PurchaseBillItem purchaseBillItem : items) {
				System.out.println(purchaseBillItem.toString());
			}
			System.out.println("--------------");
		}
	}
	
	
	@Test
	public void object() throws Exception {
		
		//实例化查询对象，模拟前台传入的查询参数
		PurchaseBillItemQuery baseQuery =  new PurchaseBillItemQuery();
		//设置where条件：查询状态为1的数据
		baseQuery.setStatus(1);
		//设置分组条件：按供应商名称分组
		baseQuery.setGroupBy(" o.bill.supplier.name ");
		
		//调用业务层方法，根据前台传入的baseQuery进行查询，返回的结果用作参数，进行二次查询，并返回最终需要展示的订单明细数据
		List<Object[]> list = purchaseBillItemService.findByGroup(baseQuery);
		
		for(Object[] objects:list){
			System.out.println(Arrays.toString(objects));
			//在第二条hql的基础上，查询第二条hql
			List<PurchaseBillItem> items = purchaseBillItemService.findItems(baseQuery, objects[0]);
			for (PurchaseBillItem purchaseBillItem : items) {
				System.out.println(purchaseBillItem.toString());
			}
			System.out.println("--------------");
		}
	}
}
