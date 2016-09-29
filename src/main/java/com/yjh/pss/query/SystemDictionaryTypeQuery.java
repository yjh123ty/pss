package com.yjh.pss.query;

import org.apache.commons.lang.StringUtils;

import com.yjh.pss.domain.SystemDictionaryType;

public class SystemDictionaryTypeQuery extends BaseQuery {
	private String name;
	
	public SystemDictionaryTypeQuery() {
		super(SystemDictionaryType.class.getName());
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
