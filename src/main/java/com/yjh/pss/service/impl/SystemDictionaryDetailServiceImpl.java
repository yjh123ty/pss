package com.yjh.pss.service.impl;

import java.util.List;

import com.yjh.pss.domain.SystemDictionaryDetail;
import com.yjh.pss.domain.SystemDictionaryType;
import com.yjh.pss.service.ISystemDictionaryDetailService;

public class SystemDictionaryDetailServiceImpl extends BaseServiceImpl<SystemDictionaryDetail> implements ISystemDictionaryDetailService {
	//sn是不会被修改且唯一的
	String hql = "select o from SystemDictionaryDetail o where o.systemDictionaryType.sn=?";

	@Override
	public List<SystemDictionaryDetail> getBrands() {
		return baseDao.findByHql(hql, SystemDictionaryType.PRODUCT_BRAND);
	}

	@Override
	public List<SystemDictionaryDetail> getUnits() {
		return baseDao.findByHql(hql, SystemDictionaryType.PRODUCT_UNIT);
	}
	
}
