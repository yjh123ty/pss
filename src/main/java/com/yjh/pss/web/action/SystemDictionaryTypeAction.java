package com.yjh.pss.web.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.yjh.pss.domain.SystemDictionaryType;
import com.yjh.pss.query.SystemDictionaryTypeQuery;
import com.yjh.pss.query.PageList;
import com.yjh.pss.service.ISystemDictionaryTypeService;


public class SystemDictionaryTypeAction extends CRUDAction<SystemDictionaryType>{
	private static final long serialVersionUID = 1L;
	
	//依赖注入
	private ISystemDictionaryTypeService systemDictionaryTypeService;
	
	//前台对象模型 ,ModelDriver切换成栈顶对象，因此不需要getter,setter
	private SystemDictionaryType systemDictionaryType;
	
	// 分页对象,getter方法
	private PageList pageList;
	
	// 必须实例化,getter,setter方法
	private SystemDictionaryTypeQuery baseQuery = new SystemDictionaryTypeQuery();
	
	//列表
	@Override
	protected void list() throws Exception {
		this.pageList = systemDictionaryTypeService.findByQuery(baseQuery);
	}
	
	
	//中转
	@Override
	public String input() throws Exception {
		return INPUT;
	}
	
	//保存
	@InputConfig(methodName="input")	
	@Override
	public String save() throws Exception {
		//若传入id为空，则是保存，否则为修改
		if(id != null){
			systemDictionaryTypeService.update(systemDictionaryType);
			addActionMessage("修改成功");
		}else {
			systemDictionaryTypeService.save(systemDictionaryType);
			//更新后跳转至最后一页
			baseQuery.setCurrentPage(Integer.MAX_VALUE);
			addActionMessage("保存成功");
		}
		return RELOAD;
	}
	
	
	//使用ajax删除
	@Override
	public String delete() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			if (id != null) {
				systemDictionaryTypeService.delete(id);
				out.print("{\"success\":true,\"msg\":\"删除成功\"}");
			}else{
				out.print("{\"success\":false,\"msg\":\"删除必须传递id\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();// 便于排错
			out.print("{\"success\":false,\"msg\":\"删除出现异常:"+e.getMessage()+"\"}");
		}
		return null;// 不返回页面,只返回文本

	}
	
	@Override
//	执行action的方法之前做额外的事情
	public void prepare() throws Exception {
	}
	
	//只在调用input方法前调用
	@Override
	public void prepareInput() throws Exception {
		if(id!=null){	//修改
			systemDictionaryType = systemDictionaryTypeService.get(id);
		}
	}
	
	//只在调用save方法前调用
	@Override
	public void prepareSave() throws Exception {
		if (id != null) {
			systemDictionaryType = systemDictionaryTypeService.get(id);
		} else {
			//变成栈顶对象，保证没有修改的对象属性没有被丢失
			systemDictionaryType = new SystemDictionaryType();
		}
	}
	


	
	 //依赖注入
	public void setSystemDictionaryTypeService(ISystemDictionaryTypeService systemDictionaryTypeService) {
		this.systemDictionaryTypeService = systemDictionaryTypeService;
	}

	// 查询对象的封装
	public SystemDictionaryTypeQuery getBaseQuery() {
		return baseQuery;
	}
	public void setBaseQuery(SystemDictionaryTypeQuery baseQuery) {
		this.baseQuery = baseQuery;
	}

	 // 查询后结果的封装
	public PageList getPageList() {
		return pageList;
	}

    // 模型驱动
	@Override
	public SystemDictionaryType getModel() {
		return systemDictionaryType;
	}
	

}
