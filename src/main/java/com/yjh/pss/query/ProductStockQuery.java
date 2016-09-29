package com.yjh.pss.query;

import org.apache.commons.lang.StringUtils;

import com.yjh.pss.domain.ProductStock;

public class ProductStockQuery extends BaseQuery {
	private String name;
	
	public ProductStockQuery() {
		super(ProductStock.class.getName());
	}

	@Override
	protected void addWhere() {
		if(StringUtils.isNotBlank(name)){
			addWhere("o.name like ?", "%" + name + "%");
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
