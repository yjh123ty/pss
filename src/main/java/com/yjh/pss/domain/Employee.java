package com.yjh.pss.domain;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

public class Employee {
	private Long id;
	private String username;
	private String password;
	private String email;
	private String headImage;
	private Integer age;
	private Department department;
	// 当前员工拥有的角色列表
	private Set<Role> roles = new HashSet<Role>();

	public Employee() {

	}

	public Employee(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	@JSON(serialize=false)
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public String getRoleString(){
		StringBuilder builder = new StringBuilder();
		for(Role role : roles){
			builder.append(role.getName() + "," );
		}
		if(builder.length()>0){
			builder.deleteCharAt(builder.length() - 1);
		}
		return builder.toString();
	}
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", age=" + age + "]";
	}

}
