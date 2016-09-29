package com.yjh.pss.query;

import org.apache.commons.lang.StringUtils;

import com.yjh.pss.domain.Employee;

public class EmployeeQuery extends BaseQuery {
	private String username;
	private String email;
	private Long deptId;
	// 年龄滑块核心初始化值
	private String age = "18,130";

	// 年龄滑块回显核心代码,提供一个ages属性
	public String[] getAges() {
		if (StringUtils.isNotBlank(age)) {
			String[] strings = age.split(",");
			if (strings != null && strings.length == 2) {
				return strings;
			}
		}
		return new String[2];
	}

	public EmployeeQuery() {
		super(Employee.class.getName());
	}

	@Override
	protected void addWhere() {
		if (StringUtils.isNotBlank(username)) {
			addWhere("o.username like ?", "%" + username + "%");
		}

		if (StringUtils.isNotBlank(email)) {
			addWhere("o.email like ?", "%" + email + "%");
		}

		if (deptId != null && deptId != -1) {
			addWhere("o.department.id = ?", deptId);
		}

		if (StringUtils.isNotBlank(age)) {
			String[] strings = age.split(",");
			if (strings != null && strings.length == 2) {
				addWhere("o.age between ? and ?", Integer.parseInt(strings[0]), Integer.parseInt(strings[1]));
			}
		}

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

}
