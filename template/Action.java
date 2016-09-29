package com.yjh.pss.web.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.yjh.pss.domain.${domain};
import com.yjh.pss.query.${domain}Query;
import com.yjh.pss.query.PageList;
import com.yjh.pss.service.I${domain}Service;


public class ${domain}Action extends CRUDAction<${domain}>{
	private static final long serialVersionUID = 1L;
	
	//依赖注入
	private I${domain}Service ${lowerDomain}Service;
	
	//前台对象模型 ,ModelDriver切换成栈顶对象，因此不需要getter,setter
	private ${domain} ${lowerDomain};
	
	// 分页对象,getter方法
	private PageList pageList;
	
	// 必须实例化,getter,setter方法
	private ${domain}Query baseQuery = new ${domain}Query();
	
	//列表
	@Override
	protected void list() throws Exception {
		this.pageList = ${lowerDomain}Service.findByQuery(baseQuery);
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
			${lowerDomain}Service.update(${lowerDomain});
			addActionMessage("修改成功");
		}else {
			${lowerDomain}Service.save(${lowerDomain});
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
				${lowerDomain}Service.delete(id);
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
			${lowerDomain} = ${lowerDomain}Service.get(id);
		}
	}
	
	//只在调用save方法前调用
	@Override
	public void prepareSave() throws Exception {
		if (id != null) {
			${lowerDomain} = ${lowerDomain}Service.get(id);
		} else {
			//变成栈顶对象，保证没有修改的对象属性没有被丢失
			${lowerDomain} = new ${domain}();
		}
	}
	


	
	 //依赖注入
	public void set${domain}Service(I${domain}Service ${lowerDomain}Service) {
		this.${lowerDomain}Service = ${lowerDomain}Service;
	}

	// 查询对象的封装
	public ${domain}Query getBaseQuery() {
		return baseQuery;
	}
	public void setBaseQuery(${domain}Query baseQuery) {
		this.baseQuery = baseQuery;
	}

	 // 查询后结果的封装
	public PageList getPageList() {
		return pageList;
	}

    // 模型驱动
	@Override
	public ${domain} getModel() {
		return ${lowerDomain};
	}
	

}
