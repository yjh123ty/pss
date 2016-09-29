package com.yjh.pss.domain;

public class SystemDictionaryType {
	// 定义2个常量：系统初始化的时候
	public static final String PRODUCT_BRAND = "productBrand";// 产品品牌
	public static final String PRODUCT_UNIT = "productUnit";// 产品单位

	private Long id;
	private String sn;// 唯一,不能修改
	private String name;// domain的名称

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
