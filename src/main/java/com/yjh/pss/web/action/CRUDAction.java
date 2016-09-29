package com.yjh.pss.web.action;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * 规范所有又CRUD+高级查询+分页的Action
 */
public abstract class CRUDAction<T> extends BaseAction implements ModelDriven<T>,Preparable{
	private static final long serialVersionUID = 1L;
	
	protected Long id;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	// 列表方法
	@Override
	public String execute() throws Exception {
		list();
		return SUCCESS;
	}
	
	// 必须由子类重写
	protected abstract void list() throws Exception;
	//保存
	public abstract String save() throws Exception ;
	//删除
	public abstract String delete() throws Exception;
//	//中转
//	public String input() throws Exception {
//		return INPUT;
//	}
	
	//只在调用input方法前调用
	public abstract void prepareInput() throws Exception;
	
	//只在调用save方法前调用
	public abstract void prepareSave() throws Exception;
	
	//空方法，覆写
	@Override
	public void prepare() throws Exception {
	}
	

}
