package com.yjh.pss.service;

import java.util.List;

import com.yjh.pss.domain.SystemDictionaryDetail;

public interface ISystemDictionaryDetailService extends IBaseService<SystemDictionaryDetail> {
	//获得品牌
	List<SystemDictionaryDetail> getBrands();
	//获得单位
	List<SystemDictionaryDetail> getUnits();
}
