package com.yjh.pss.domain;

import java.util.HashSet;
import java.util.Set;

public class Department  {
	private Long id;
	private String name;
	private Set<Employee> employees = new HashSet<>();
	
	public Department(){}
	
	public Department(long id) {
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
	public Set<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}
	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + "]";
	}
	
}
