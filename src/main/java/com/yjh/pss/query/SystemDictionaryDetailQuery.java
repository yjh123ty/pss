package com.yjh.pss.query;

import org.apache.commons.lang.StringUtils;

import com.yjh.pss.domain.SystemDictionaryDetail;

public class SystemDictionaryDetailQuery extends BaseQuery {
	private String name;
	
	public SystemDictionaryDetailQuery() {
		super(SystemDictionaryDetail.class.getName());
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
