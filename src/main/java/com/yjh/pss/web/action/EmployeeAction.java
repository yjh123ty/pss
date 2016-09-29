package com.yjh.pss.web.action;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.yjh.pss.domain.Department;
import com.yjh.pss.domain.Employee;
import com.yjh.pss.domain.Role;
import com.yjh.pss.query.EmployeeQuery;
import com.yjh.pss.query.PageList;
import com.yjh.pss.service.IDepartmentService;
import com.yjh.pss.service.IEmployeeService;
import com.yjh.pss.service.IRoleService;

public class EmployeeAction extends CRUDAction<Employee> {
	private static final long serialVersionUID = 1L;

	// 依赖注入
	private IEmployeeService employeeService;
	private IDepartmentService departmentService;
	private IRoleService roleService;

	// 前台对象模型 ,ModelDriver切换成栈顶对象，因此不需要getter,setter
	private Employee employee;

	// 分页对象,getter方法
	private PageList pageList;

	// 必须实例化,getter,setter方法
	private EmployeeQuery baseQuery = new EmployeeQuery();

	// 验证用户名是否存在，接收传入的username, getter,setter方法
	private String username;

	// 获取(角色)ids的checkbox的值
	private Long[] ids;

	// 列表
	@Override
	protected void list() throws Exception {
		this.pageList = employeeService.findByQuery(baseQuery);
		putContext("allDepts", departmentService.getAll());
		putContext("allRoles", roleService.getAll());
	}

	// 只验证save方法:简单验证：非空，长度，格式
	public void validateSave() {
		if (StringUtils.isBlank(employee.getUsername())) {
			addFieldError("nameError", "用户名必须填写");
		}
		if (StringUtils.isBlank(employee.getPassword())) {
			addFieldError("pwdError", "密码必须填写");
		}
	}

	// 中转
	// 出现转换异常或者验证异常，跳转到input方法
	// @InputConfig(methodName = INPUT)
	@Override
	public String input() throws Exception {
		putContext("allDepts", departmentService.getAll());
		putContext("allRoles", roleService.getAll());
		return INPUT;
	}

	// 保存
	// 添加了这个标签，那么如果我们输入了错误的参数类型，它会让我们跳转回本页面，并且会对错误信息进行回显@InputConfig(methodName
	// = INPUT)
	@InputConfig(methodName = "input")
	@Override
	public String save() throws Exception {
		// 处理部门为空的情况
		Department department = employee.getDepartment();
		if (department != null && department.getId() == -1L) {
			employee.setDepartment(null);
		}

		// 把ids变成中间表employee_role
		for (Long id : ids) {
			employee.getRoles().add(new Role(id));
		}

		// 若传入id为空，则是保存，否则为修改
		if (id != null) {
			employeeService.update(employee);
			addActionMessage("修改成功");
		} else {
			employeeService.save(employee);
			/**
			 * 保存成功后，将 当前页面改成最后一页，
			 * 这里我们取最大值，currentPage由于做了判断，一切大于totalPage的值，都会改为totalPage
			 */
			baseQuery.setCurrentPage(Integer.MAX_VALUE);
			addActionMessage("保存成功");
		}
		return RELOAD;
	}

	// //后台删除
	// @Override
	// public String delete() throws Exception {
	// if (id != null) {
	// employeeService.delete(id);
	// }
	// return RELOAD;
	// }

	// 使用ajax删除
	@Override
	public String delete() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			if (id != null) {
				employeeService.delete(id);
				out.print("{\"success\":true,\"msg\":\"删除成功\"}");
			} else {
				out.print("{\"success\":false,\"msg\":\"删除必须传递id\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();// 便于排错
			out.print("{\"success\":false,\"msg\":\"删除出现异常:" + e.getMessage() + "\"}");
		}
		return null;// 不返回页面,只返回文本

	}

	@Override
	// 执行action的方法之前做额外的事情
	public void prepare() throws Exception {
	}

	// 只在调用input方法前调用
	@Override
	public void prepareInput() throws Exception {
		if (id != null) { // 修改
			employee = employeeService.get(id);
			// 把中间表employee_role变成ids
			Set<Role> roles = employee.getRoles();
			ids = new Long[roles.size()];
			int index = 0;
			for (Role role : roles) {
				ids[index++] = role.getId();
			}
		} else {
			employee = new Employee();
		}
	}

	// 只在调用save方法前调用
	@Override
	public void prepareSave() throws Exception {
		if (id != null) {// 先从数据库查询出来，password就存在
			employee = employeeService.get(id);
			// 修改后保存：employee持久状态：employee.getDepartment持久状态.setId(xx)
			// 把employee.getDepartment,getRoles变成临时状态
			employee.setDepartment(null);
			employee.getRoles().clear();

		} else {
			// 变成栈顶对象，保证没有修改的对象属性没有被丢失
			employee = new Employee();
		}
	}

	// ajax验证用户名是否重复,响应用out.print
	public String checkUsername() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();

		// ??为什么此处要以这样的格式打印？out.print,打印的东西是什么(这是ajax响应)

		if (id != null) { // id不为空，则是修改
			/**
			 * 在编辑中，要做几步事情： 1、拿到以前的用户名 2、判断是否修改了原来的用户名 a.若没有修改，则直接返回验证为true
			 * b.若修改，则验证传入的名字，和数据库中的数据进行对照
			 */
			// 1、拿到以前的用户名
			String oldUsername = employeeService.get(id).getUsername();

			// 2、判断是否修改了原来的用户名
			if (username.equals(oldUsername)) {
				// a.若没有修改，则直接返回验证为true
				out.print("{\"valid\": true}");
			} else {
				// b.若修改，则验证传入的名字，和数据库中的数据进行对照
				out.print("{\"valid\":" + employeeService.findByUsername(username) + "}");
			}
		} else {
			// id为空，则表示前台处理的功能是“保存”，就需要去验证数据库是否有该名称的用户
			out.print("{\"valid\":" + employeeService.findByUsername(username) + "}");
		}

		return null;// 不返回页面,只返回json文本
	}

	// 下载
	// 1、产生一个xlsx文件
	// 2、创建一个输出字节流，将产生的xlsx文件放入这个字节流中去
	// 3、将这个字节流对象转换成输入的字节流对象
	private InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}

	// 表头的内容是固定
	String[] heads = { "编号", "用户名", "密码", "邮箱", "年龄", "部门名称" };
	
	public String download() throws Exception {
		// 下载保证查询后的所有数据
		baseQuery.setPageSize(Integer.MAX_VALUE);
		this.pageList = employeeService.findByQuery(baseQuery);
		// 行(由于这里设置的页面显示数据是最大尺寸，因此，查询到的结果也是所有的数据，导出的文件也应该是全部的数据)
		List<Employee> rows = pageList.getRows();
		// 把List<Employee>变成List<String[]>
		List<String[]> list = new ArrayList<String[]>();
		for (Employee employee : rows) {
			String[] strings = new String[heads.length];
			strings[0] = employee.getId().toString();
			strings[1] = employee.getUsername();
			strings[2] = employee.getPassword();
			strings[3] = employee.getEmail();
			strings[4] = employee.getAge() == null ? "" : employee.getAge().toString();
			strings[5] = employee.getDepartment() == null ? "" : employee.getDepartment().getName();
			// 最关键的方法
			list.add(strings);
		}
		inputStream = employeeService.download(heads, list);
		return "download";

		/**
		 * 这种方式并不通用，值针对Employee方式，而且这种方式没考虑表头内容
		 */
		// //1、产生一个xlsx文件
		// // 创建一个对象内存
		// SXSSFWorkbook wb = new SXSSFWorkbook();
		// // 创建一个表
		// Sheet sh = wb.createSheet();
		// List<Employee> list = employeeService.getAll();
		// for (int rownum = 0; rownum < list.size(); rownum++) {
		// // 创建表里面的行
		// Row row = sh.createRow(rownum);
		// for (int cellnum = 0; cellnum < 5; cellnum++) {
		// // 处理单元格
		// Cell cell = row.createCell(cellnum);
		// //思路：每一行将数据依次读出（编号，名称，年龄，邮箱等等属性）
		// switch (cellnum) {
		// case 0:
		// cell.setCellValue(list.get(rownum).getId());
		// break;
		// case 1:
		// cell.setCellValue(list.get(rownum).getUsername());
		// break;
		// case 2:
		// cell.setCellValue(list.get(rownum).getEmail());
		// break;
		// case 3:
		// cell.setCellValue(list.get(rownum).getAge());
		// break;
		// case 4:
		// cell.setCellValue(list.get(rownum).getDepartment().getName());
		// break;
		// default:
		// break;
		// }
		// }
		// }
		// //2、创建一个输出字节流，将产生的xlsx文件放入这个字节流中去
		// ByteArrayOutputStream out = new ByteArrayOutputStream();
		// wb.write(out);
		// out.close();
		// wb.dispose();
		// //3、将这个字节流对象转换成输入的字节流对象(为什么需要一个InputStream，因为这是struts2的下载对象是这样设计的：
		// // StreamResult对象需要一个InputStream，并且我们还要提供一个getInputStream的方法)
		// this.inputStream = new ByteArrayInputStream(out.toByteArray());
		// return "download";

	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	// 依赖注入
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	// 查询对象的封装
	public EmployeeQuery getBaseQuery() {
		return baseQuery;
	}

	public void setBaseQuery(EmployeeQuery baseQuery) {
		this.baseQuery = baseQuery;
	}

	// 查询后结果的封装
	public PageList getPageList() {
		return pageList;
	}

	// 模型驱动
	@Override
	public Employee getModel() {
		return employee;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	
	// 处理下载的中文名称
	  public String getFileName() throws UnsupportedEncodingException {
	    return new String("员工列表.xlsx".getBytes("GBK"), "ISO8859-1");
	  }
	  
	  
}
