package com.yjh.pss.domain;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

public class ProductType {
	private Long id;
	private String name;
	private String descs;
	//自关联
	private ProductType parent;
	private Set<ProductType> children = new HashSet<>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@JSON(serialize = false)//当前属性不输出
	public String getDescs() {
		return descs;
	}
	public void setDescs(String descs) {
		this.descs = descs;
	}
	
	
	@JSON(serialize = false)
	public ProductType getParent() {
		return parent;
	}
	public void setParent(ProductType parent) {
		this.parent = parent;
	}
	public Set<ProductType> getChildren() {
		return children;
	}
	public void setChildren(Set<ProductType> children) {
		this.children = children;
	}
	
	
}
