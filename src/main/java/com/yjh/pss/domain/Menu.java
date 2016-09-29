package com.yjh.pss.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

public class Menu {
	private Long id;
	private String name;
	private String url;
	private String icon;
	// 单向多对一
	private Menu parent;
	// 添加一个属性,装二级菜单,但是此属性不持久化,不交给hibernate管理
	private List<Menu> children = new ArrayList<Menu>();
	
	public Menu(){}
	
	public Menu(Long mid) {
		this.id = mid;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@JSON(name = "text")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@JSON(name = "iconCls")
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	@JSON(serialize=false)
	public Menu getParent() {
		return parent;
	}
	public void setParent(Menu parent) {
		this.parent = parent;
	}
	public List<Menu> getChildren() {
		return children;
	}
	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	
}
