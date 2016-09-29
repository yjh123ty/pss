package com.yjh.pss.service;

import java.util.List;

import com.yjh.pss.domain.PurchaseBillItem;
import com.yjh.pss.query.PurchaseBillItemQuery;

public interface IPurchaseBillItemService extends IBaseService<PurchaseBillItem> {
	//返回报表分组依据
	public List<Object[]> findByGroup(PurchaseBillItemQuery baseQuery);
	
	//返回报表明细（包含返回包边分组依据的结果，在这个方法中作为参数 groupByValue 使用）
	public List<PurchaseBillItem> findItems(PurchaseBillItemQuery baseQuery, Object groupByValue);
	
	//通过这个方法直接让jsp页面调用该方法，将得到的数据以json的形式，传递给js调用，以实现根据数据展示饼图，柱状图或其他图形的功能
	public List<Object[]> findByGroup2(PurchaseBillItemQuery baseQuery);
}
