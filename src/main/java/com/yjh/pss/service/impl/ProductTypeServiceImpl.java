package com.yjh.pss.service.impl;

import java.util.List;

import com.yjh.pss.domain.ProductType;
import com.yjh.pss.service.IProductTypeService;

public class ProductTypeServiceImpl extends BaseServiceImpl<ProductType> implements IProductTypeService {

	@Override
	public List<ProductType> getParents() {
		return baseDao.findByHql("select o from ProductType o where o.parent is null");
	}

	@Override
	public List<ProductType> findChildren(Long parentId) {
		return baseDao.findByHql("select o from ProductType o where o.parent.id = ?",parentId);
	}

}
