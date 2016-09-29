package com.yjh.pss.web.action;

import com.yjh.pss.service.IMenuService;

public class MainAction extends BaseAction {
	//依赖注入
	private IMenuService menuService;
	
	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}
	
	
	@Override
	public String execute() throws Exception {
		putContext("menus", menuService.findByLoginUser(getLoginUser()));
		return SUCCESS;
	}
	
	public String json() throws Exception {
		putContext("map", menuService.findByLoginUser(getLoginUser()));
		return "jsonResult";
	}
	
}
