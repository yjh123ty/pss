package com.yjh.pss.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yjh.pss.domain.PurchaseBillItem;
import com.yjh.pss.query.PurchaseBillItemQuery;
import com.yjh.pss.service.IPurchaseBillItemService;

public class PurchaseBillItemServiceImpl extends BaseServiceImpl<PurchaseBillItem> implements IPurchaseBillItemService {

	@Override
	public List<Object[]> findByGroup(PurchaseBillItemQuery baseQuery) {
		// 如果分组条件为空，设置默认值为根据供应商来分组
		String groupBy = baseQuery.getGroupBy();
		if (StringUtils.isBlank(groupBy)) {
			groupBy="o.bill.supplier.name";
	        baseQuery.setGroupBy(groupBy);
		}
		// 根据是否有查询条件,拼接不同的hql
		List paramList = baseQuery.getParamList();
		if (paramList.size() == 0) {// 没有查询条件
			String hql = "select "+ groupBy +",count(o) from PurchaseBillItem o group by " + groupBy;
			return baseDao.findByHql(hql);
		}else{
			String where = baseQuery.getWhereHql();
			String hql = "select "+ groupBy +",count(o) from PurchaseBillItem o " + where +" group by " + groupBy;
			//装条件的对象是List,查询需要Object[],转换
			return baseDao.findByHql(hql,paramList.toArray());
		}

	}

	@Override
	public List<PurchaseBillItem> findItems(PurchaseBillItemQuery baseQuery, Object groupByValue) {
		//拿到groupBy条件
		String groupBy = baseQuery.getGroupBy();
		
		//根据是否有条件，拼接不同的hql
		List paramList = baseQuery.getParamList();
		if(paramList.size()==0){ //没有查询条件，但注意，一定有分组条件！！！！！
			String hql = "select o from PurchaseBillItem o where "+ groupBy + " =?";
			return baseDao.findByHql(hql,groupByValue);
		}else{
			String where = baseQuery.getWhereHql();
			// where是大写还是小写的
			String hql = "select o from PurchaseBillItem o where " + groupBy + "=?" + where.replaceAll("where", "and");
			//由于这里在循环体内部，所以为了保证每次查询的参数是不变的，这里需要重新new一个list，保证paramList不会改变
			List list = new ArrayList();
			list.add(groupByValue);
			list.addAll(paramList);
			// 装条件的对象是List,查询需要Object[],转换
			return baseDao.findByHql(hql, list.toArray());

		}
	}

	@Override
	public List<Object[]> findByGroup2(PurchaseBillItemQuery baseQuery) {
		String groupBy = baseQuery.getGroupBy();
	    if (StringUtils.isBlank(groupBy)) {
	      throw new RuntimeException("必须要有分组条件...");
	    }
	    List paramList = baseQuery.getParamList();
	    if (paramList.size() == 0) {// 没有查询条件
	      String hql = "select " + groupBy + ",sum(o.amount) FROM PurchaseBillItem o GROUP BY " + groupBy;
	      return baseDao.findByHql(hql);
	    } else {// 肯定有查询条件
	      String where = baseQuery.getWhereHql();
	      String hql = "select " + groupBy + ",sum(o.amount) FROM PurchaseBillItem o" + where + " GROUP BY " + groupBy;
	      return baseDao.findByHql(hql, paramList.toArray());
	    }
	}

}
