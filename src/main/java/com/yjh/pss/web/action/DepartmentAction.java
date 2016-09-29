package com.yjh.pss.web.action;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.yjh.pss.domain.Department;
import com.yjh.pss.query.DepartmentQuery;
import com.yjh.pss.query.PageList;
import com.yjh.pss.service.IDepartmentService;


public class DepartmentAction extends CRUDAction<Department>{
	private static final long serialVersionUID = 1L;
	
	//依赖注入
	private IDepartmentService departmentService;
	
	//前台对象模型 ,ModelDriver切换成栈顶对象，因此不需要getter,setter
	private Department department;
	
	// 分页对象,getter方法
	private PageList pageList;
	
	// 必须实例化,getter,setter方法
	private DepartmentQuery baseQuery = new DepartmentQuery();
	
	//列表
	@Override
	protected void list() throws Exception {
		this.pageList = departmentService.findByQuery(baseQuery);
	}
	
	
	//中转
	// 出现转换异常或者验证异常，跳转到input方法
//	@InputConfig(methodName = INPUT)
	@Override
	public String input() throws Exception {
		return INPUT;
	}
	
	//保存
	//添加了这个标签，那么如果我们输入了错误的参数类型，它会让我们跳转回本页面，并且会对错误信息进行回显@InputConfig(methodName = INPUT)
	@InputConfig(methodName="input")	
	@Override
	public String save() throws Exception {
		//若传入id为空，则是保存，否则为修改
		if(id != null){
			departmentService.update(department);
			addActionMessage("修改成功");
		}else {
			departmentService.save(department);
			/**
			 * 保存成功后，将 当前页面改成最后一页，
			 * 这里我们取最大值，currentPage由于做了判断，一切大于totalPage的值，都会改为totalPage
			 */
			baseQuery.setCurrentPage(Integer.MAX_VALUE);
			addActionMessage("保存成功");
		}
		return RELOAD;
	}
	
//	//后台删除
//	@Override
//	public String delete() throws Exception {
//		if (id != null) {
//			departmentService.delete(id);
//		}
//		return RELOAD;
//	}
	
	//使用ajax删除
	@Override
	public String delete() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			if (id != null) {
				departmentService.delete(id);
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
		 System.out.println("..............");
		if(id!=null){	//修改
			department = departmentService.get(id);
		}
	}
	
	//只在调用save方法前调用
	@Override
	public void prepareSave() throws Exception {
		if (id != null) {// 先从数据库查询出来，password就存在
			department = departmentService.get(id);
		} else {
			//变成栈顶对象，保证没有修改的对象属性没有被丢失
			department = new Department();
		}
	}
	
	// 下载
	// 1、产生一个xlsx文件
	// 2、创建一个输出字节流，将产生的xlsx文件放入这个字节流中去
	// 3、将这个字节流对象转换成输入的字节流对象
	private InputStream inputStream;
	
	public InputStream getInputStream() {
		return inputStream;
	}
	
	String[] heads = { "部门编号", "部门名称" };
	
	public String download() throws Exception {
		// 下载保证查询后的所有数据
		baseQuery.setPageSize(Integer.MAX_VALUE);
		this.pageList = departmentService.findByQuery(baseQuery);
		//获取所有数据
		List<Department> rows = pageList.getRows();
		// 把List<Employee>变成List<String[]>
		List<String[]> list = new ArrayList<String[]>();
		for (Department department : rows) {
			String[] strings = new String[heads.length];
			strings[0] = department.getId().toString();
			strings[1] = department.getName();
			list.add(strings);
		}
		inputStream = departmentService.download(heads, list);
		return "download";
	}
	
	 //依赖注入
	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	// 查询对象的封装
	public DepartmentQuery getBaseQuery() {
		return baseQuery;
	}
	public void setBaseQuery(DepartmentQuery baseQuery) {
		this.baseQuery = baseQuery;
	}

	 // 查询后结果的封装
	public PageList getPageList() {
		return pageList;
	}

    // 模型驱动
	@Override
	public Department getModel() {
		return department;
	}
	
	// 处理下载的中文名称
	  public String getFileName() throws UnsupportedEncodingException {
	    return new String("部门信息.xlsx".getBytes("GBK"), "ISO8859-1");
	  }

}
