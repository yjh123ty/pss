package com.yjh.pss.web.action;

import java.util.HashMap;
import java.util.Map;

import com.yjh.pss.domain.Employee;
import com.yjh.pss.query.EmployeeQuery;
import com.yjh.pss.query.PageList;
import com.yjh.pss.service.IEmployeeService;

public class DatagridAction extends BaseAction {
	private IEmployeeService employeeService;
	private EmployeeQuery baseQuery = new EmployeeQuery();
	
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	//分页展示
	private Integer page;
	private Integer rows;
	
	//前台接受的Employee
	private Employee employee;
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	//显示列表页面
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	//获取json数据(展示列表数据)
	public String json() throws Exception {
		baseQuery.setCurrentPage(page);
		baseQuery.setPageSize(rows);
		PageList pageList = employeeService.findByQuery(baseQuery);
		putContext("map", pageList);
		return "jsonResult";
	}
	
	//保存员工
	public String save() throws Exception {
		Map<String, Object> map =new HashMap<String, Object>();
		System.out.println(employee.toString());
		try {
			if(employee.getId()==null){
				employeeService.save(employee);
				map.put("msg", "保存成功");
			}else{
				employeeService.update(employee);
				map.put("msg", "修改成功");
			}
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "异常："+e.getMessage());
		}
		putContext("map", map);
		return "jsonResult";
	}

	//删除员工
	public String delete() throws Exception {
		Map<String, Object> map =new HashMap<String, Object>();
		try {
			employeeService.delete(employee.getId());
			map.put("success", true);
			map.put("msg", "删除成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "异常："+e.getMessage());
		}
		putContext("map", map);
		return "jsonResult";
	}
	
	public EmployeeQuery getBaseQuery() {
		return baseQuery;
	}
	public void setBaseQuery(EmployeeQuery baseQuery) {
		this.baseQuery = baseQuery;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}
	
	
}
