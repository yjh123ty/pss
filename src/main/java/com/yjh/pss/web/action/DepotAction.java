package com.yjh.pss.web.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.yjh.pss.domain.Depot;
import com.yjh.pss.query.DepotQuery;
import com.yjh.pss.query.PageList;
import com.yjh.pss.service.IDepotService;


public class DepotAction extends CRUDAction<Depot>{
	private static final long serialVersionUID = 1L;
	
	//依赖注入
	private IDepotService depotService;
	
	//前台对象模型 ,ModelDriver切换成栈顶对象，因此不需要getter,setter
	private Depot depot;
	
	// 分页对象,getter方法
	private PageList pageList;
	
	// 必须实例化,getter,setter方法
	private DepotQuery baseQuery = new DepotQuery();
	
	//列表
	@Override
	protected void list() throws Exception {
		this.pageList = depotService.findByQuery(baseQuery);
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
			depotService.update(depot);
			addActionMessage("修改成功");
		}else {
			depotService.save(depot);
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
				depotService.delete(id);
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
			depot = depotService.get(id);
		}
	}
	
	//只在调用save方法前调用
	@Override
	public void prepareSave() throws Exception {
		if (id != null) {
			depot = depotService.get(id);
		} else {
			//变成栈顶对象，保证没有修改的对象属性没有被丢失
			depot = new Depot();
		}
	}
	


	
	 //依赖注入
	public void setDepotService(IDepotService depotService) {
		this.depotService = depotService;
	}

	// 查询对象的封装
	public DepotQuery getBaseQuery() {
		return baseQuery;
	}
	public void setBaseQuery(DepotQuery baseQuery) {
		this.baseQuery = baseQuery;
	}

	 // 查询后结果的封装
	public PageList getPageList() {
		return pageList;
	}

    // 模型驱动
	@Override
	public Depot getModel() {
		return depot;
	}
	

}
