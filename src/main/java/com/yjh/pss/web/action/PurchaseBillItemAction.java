package com.yjh.pss.web.action;

import java.util.List;


import com.yjh.pss.domain.PurchaseBillItem;
import com.yjh.pss.query.PurchaseBillItemQuery;
import com.yjh.pss.service.IPurchaseBillItemService;

/**
 * 采购报表
 * @author Administrator
 *
 */
public class PurchaseBillItemAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	
	//依赖注入
	private IPurchaseBillItemService purchaseBillItemService;
	public void setPurchaseBillItemService(IPurchaseBillItemService purchaseBillItemService) {
		this.purchaseBillItemService = purchaseBillItemService;
	}
	
	// 查询对象，必须实例化,getter,setter方法
	private PurchaseBillItemQuery baseQuery = new PurchaseBillItemQuery();
	
	// 显示查询页面
	@Override
	public String execute() throws Exception {
		 List<Object[]> list = purchaseBillItemService.findByGroup(baseQuery);
		    putContext("list", list);
		    return SUCCESS;
	}
	
	// 从查询jsp页面访问此方法
	public List<PurchaseBillItem> findItems(Object groupByValue) {
		return purchaseBillItemService.findItems(baseQuery, groupByValue);
	}

	
	// 查询对象的封装
	public PurchaseBillItemQuery getBaseQuery() {
		return baseQuery;
	}
	public void setBaseQuery(PurchaseBillItemQuery baseQuery) {
		this.baseQuery = baseQuery;
	}
	
	// 从查询jsp页面点击按钮打开一个新的页面
	// <!-- 方法名和视图名称一致 -->
		// <result name="{2}">/WEB-INF/views/{1}/{1}_{2}.jsp</result>
		// chart1-3都是转发
		public String chart1() throws Exception {
			return "chart1";
		}

		public String chart2() throws Exception {
			return "chart2";
		}

		public String chart3() throws Exception {
			return "chart3";
		}
	
	// 接受从purchaseBillItem_chart1.jsp发出ajax请求,返回json字符串
		public String json() throws Exception {
			// [["东莞供应商",5.00],["成都供应商",57.25]]
			List<Object[]> list = purchaseBillItemService.findByGroup2(baseQuery);
			putContext("map", list);
			return "jsonResult";
		}
}


