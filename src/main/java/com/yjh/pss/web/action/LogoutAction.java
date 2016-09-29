package com.yjh.pss.web.action;

import org.apache.struts2.ServletActionContext;

/**
 * 注销
 * @author Administrator
 *
 */
public class LogoutAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		ServletActionContext.getRequest().getSession().invalidate();
		return LOGIN;
	}
}
