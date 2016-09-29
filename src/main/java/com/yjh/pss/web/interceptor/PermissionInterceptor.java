package com.yjh.pss.web.interceptor;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.yjh.pss.domain.Employee;
import com.yjh.pss.service.IEmployeeService;
import com.yjh.pss.web.action.BaseAction;

public class PermissionInterceptor extends AbstractInterceptor {
	//注入员工service
	private IEmployeeService employeeService;

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	// 放行的action 、 注入
	private String excludeActions;

	public void setExcludeActions(String excludeActions) {
		this.excludeActions = excludeActions;
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// 当前访问的action
		Object action = invocation.getAction();
		System.out.println("PermissionInterceptor:" + action);
		// action的name
		String actionName = action.getClass().getSimpleName();
		
		// 1.放行:不需要拦截的Action都放行
		if (excludeActions.contains(actionName)) {
			return invocation.invoke();
		}
		// 2.没有登录用户拦截(判断用户是否登录，是否存在于session中)
		Employee employee = (Employee) ActionContext.getContext().getSession().get(BaseAction.LOGIN_USER);
		if (employee == null) {
			// 返回登录界面
			return BaseAction.LOGIN;
		}
		

		// 已经登录成功
		// 3.权限拦截
	    // 获取当前action访问的方法
		String methodName = invocation.getProxy().getMethod();
		String classMethodName = action.getClass().getName() + "." + methodName;
		String actionMethodName = actionName + "." + methodName;
		String allActionMethodName = actionName + ".ALL";

		// 当前访问的资源
		System.out.println("actionMethodName:" + actionMethodName);
		System.out.println("allActionMethodName:" + allActionMethodName);

		// 拦截已经登录成功的用户是否具有访问当前资源的权限
		// 拦截的规则: a.只要在permission权限表method字段出现的字符串匹配当前访问的资源,拦截
		List<String> allMethodList = employeeService.getAllPermissionsMethods();
		// 若再这个方法List中有 方法名classMethodName 或者 所有的方法名allActionMethodName 
		if(allMethodList.contains(actionMethodName) || allMethodList.contains(allActionMethodName)){
			// b.拦截之后,判断当前登录的用户是否具有访问该资源的权限(5张表的查询)
			List<String> list = employeeService.findMethodsByUserInSession(employee.getId());
			if (list.contains(actionMethodName) || list.contains(allActionMethodName)) {
			}else {
				// <!-- 全局返回视图 -->
				// <global-results>
				// <!-- 视图名称permission，显示没有权限的页面 -->
				// <result name="permission">/WEB-INF/views/permission.jsp</result>
				// </global-results>
				return "permission";
			}
		}

		// 放行，进入下一个拦截器
		return invocation.invoke();
	}
}
