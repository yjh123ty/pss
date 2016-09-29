package com.yjh.pss.query;

import org.apache.commons.lang.StringUtils;

import com.yjh.pss.domain.Menu;

public class MenuQuery extends BaseQuery {
	private String name;
	
	public MenuQuery() {
		super(Menu.class.getName());
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
