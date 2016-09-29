package com.yjh.pss.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.yjh.pss.domain.Employee;

public abstract class BaseAction extends ActionSupport {
	  // 重定向视图
	  public static final String RELOAD = "reload";
	  
	  public static final String LOGIN_USER = "loginUser";
	  
	  protected void putContext(String key, Object value) {
		    ActionContext.getContext().put(key, value);
	  }
	  
	  // session里面登录用户的key

	  protected Employee getLoginUser() {
		  return (Employee) ActionContext.getContext().getSession().get(LOGIN_USER);
	}

}
