package com.yjh.pss.domain;

public class Permission {
	private Long id;
	private String name;
	private String method;
	private String descs;
	
	public Permission() {
		
	}
	
	public Permission(Long id) {
		this.id = id;
	}
	
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
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getDescs() {
		return descs;
	}
	public void setDescs(String descs) {
		this.descs = descs;
	}
	@Override
	public String toString() {
		return "Permission [id=" + id + ", name=" + name + ", method=" + method + ", descs=" + descs + "]";
	}
	
}
