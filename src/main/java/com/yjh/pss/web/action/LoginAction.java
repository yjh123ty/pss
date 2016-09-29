package com.yjh.pss.web.action;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.yjh.pss.domain.Employee;
import com.yjh.pss.service.IEmployeeService;

public class LoginAction extends BaseAction {
	// 前台接受的用户名和密码
	private String username;
	private String password;

	// 依赖注入
	private IEmployeeService employeeService;

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	// 显示登录页面
	@Override
	public String execute() throws Exception {
		return LOGIN;
	}

	public void validateCheck() {
		if (StringUtils.isBlank(username)) {
			addFieldError("username", "用户名必须填写");
		}
		if (StringUtils.isBlank(password)) {
			addFieldError("password", "密码必须填写");
		}
	}

	// 处理登录请求
	@InputConfig(methodName = "execute")
	public String login() throws Exception {
		Employee employee = employeeService.findByLogin(username, password);
		if (employee != null) {// 登录成功
			ActionContext.getContext().getSession().put(LOGIN_USER, employee);
			return "main";// 重定向到main
		}
		addActionError("登录失败");
		return LOGIN;
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

}
