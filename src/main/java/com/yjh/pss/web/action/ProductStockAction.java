package com.yjh.pss.web.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.yjh.pss.domain.ProductStock;
import com.yjh.pss.query.ProductStockQuery;
import com.yjh.pss.query.PageList;
import com.yjh.pss.service.IProductStockService;


public class ProductStockAction extends CRUDAction<ProductStock>{
	private static final long serialVersionUID = 1L;
	
	//依赖注入
	private IProductStockService productStockService;
	
	//前台对象模型 ,ModelDriver切换成栈顶对象，因此不需要getter,setter
	private ProductStock productStock;
	
	// 分页对象,getter方法
	private PageList pageList;
	
	// 必须实例化,getter,setter方法
	private ProductStockQuery baseQuery = new ProductStockQuery();
	
	//列表
	@Override
	protected void list() throws Exception {
		this.pageList = productStockService.findByQuery(baseQuery);
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
			productStockService.update(productStock);
			addActionMessage("修改成功");
		}else {
			productStockService.save(productStock);
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
				productStockService.delete(id);
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
			productStock = productStockService.get(id);
		}
	}
	
	//只在调用save方法前调用
	@Override
	public void prepareSave() throws Exception {
		if (id != null) {
			productStock = productStockService.get(id);
		} else {
			//变成栈顶对象，保证没有修改的对象属性没有被丢失
			productStock = new ProductStock();
		}
	}
	


	
	 //依赖注入
	public void setProductStockService(IProductStockService productStockService) {
		this.productStockService = productStockService;
	}

	// 查询对象的封装
	public ProductStockQuery getBaseQuery() {
		return baseQuery;
	}
	public void setBaseQuery(ProductStockQuery baseQuery) {
		this.baseQuery = baseQuery;
	}

	 // 查询后结果的封装
	public PageList getPageList() {
		return pageList;
	}

    // 模型驱动
	@Override
	public ProductStock getModel() {
		return productStock;
	}
	

}
