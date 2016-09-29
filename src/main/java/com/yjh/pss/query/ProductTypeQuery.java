package com.yjh.pss.query;

import org.apache.commons.lang.StringUtils;

import com.yjh.pss.domain.ProductType;

public class ProductTypeQuery extends BaseQuery {
	private String name;
	
	public ProductTypeQuery() {
		super(ProductType.class.getName());
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
