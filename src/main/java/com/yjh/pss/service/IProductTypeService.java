package com.yjh.pss.service;

import java.util.List;

import com.yjh.pss.domain.ProductType;

public interface IProductTypeService extends IBaseService<ProductType> {
	//获取一级菜单
	public List<ProductType> getParents();
	//获取二级菜单
	public List<ProductType> findChildren(Long parentId);
}
