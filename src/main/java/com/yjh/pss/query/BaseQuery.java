package com.yjh.pss.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseQuery {
	// 初始值
	private int currentPage = 1;
	// 当前页码
	private int pageSize = 10;
	// 定义装hql的对象
	private StringBuilder limitHql;
	private StringBuilder countHql;
	//特别添加属性给查询报表,只装查询条件的hql
	private StringBuilder whereHql;
	// 定义hql的参数
	private List paramList;

	public BaseQuery(String className) {
		// 自己拼接:StringBuffer(同步,线程安全),StringBuilder(不同步,线程不安全)
		limitHql = new StringBuilder("select o from " + className + " o ");
		countHql = new StringBuilder("select count(o) from " + className + " o ");
		whereHql = new StringBuilder();
		paramList = new ArrayList();
	}

	// select count(o) from Employee o where o.username like ? and o.email like ?
	// select o from Employee o where o.username like ? and o.email like ?

	// 提供一个方法给子类直接调用
	// 提供方法：让子类传入条件hql：o.name like ?和查询条件？
	// select o from Employee o where o.date between ? and ?
	// 可变参数：Object... objects必须写到最后
	// 提供一个方法给子类直接调用
	protected void addWhere(String where, Object... objects) {
		if (paramList.size() == 0) {// 添加where
			limitHql.append(" where ").append(where);
			countHql.append(" where ").append(where);
			whereHql.append(" where ").append(where);
		} else {// 添加and
			limitHql.append(" and ").append(where);
			countHql.append(" and ").append(where);
			whereHql.append(" and ").append(where);
		}
		paramList.addAll(Arrays.asList(objects));
	}


	// 在提供一个方法,让子类重写的
	protected abstract void addWhere();

	// 保证addWhere()只能调用一次
	private boolean flag;// 标记
	// 定义标记
	private void builderWhere() {
		if(!flag){
			addWhere();
			// 修改flag为true
			flag = true;
		}
	}

	// 控制addWhere方法只能调用一次

	// 需要回显数据，getter，setter
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	// 提供3个get方法获取2条hql，查询条件
	public String getLimitHql() {
		builderWhere();
		return limitHql.toString();
	}

	public String getCountHql() {
		builderWhere();
		return countHql.toString();
	}

	public List getParamList() {
		builderWhere();
		return paramList;
	}
	
	//专门用于报表查询的hql
	public String getWhereHql() {
		builderWhere();
		return whereHql.toString();
	}


}
