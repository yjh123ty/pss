package com.yjh.pss.web.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.inject.util.Strings;
import com.yjh.pss.domain.Department;
import com.yjh.pss.domain.Employee;
import com.yjh.pss.service.IDepartmentService;
import com.yjh.pss.service.IEmployeeService;

public class ImportAction extends BaseAction {
	private File upload;
	
	//前台定义一个flag用于标识需要导入的信息类型：1-员工信息；2-部门信息
	private Integer flag;
	
	//  依赖注入
	private IEmployeeService employeeService;
	private IDepartmentService departmentService;

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	// 1.先显示一个页面
	// <s:file/>上传xls文件
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	// 上传文件
	public String upload() throws Exception {
		// 判断上传文件是否为空
		if (upload != null) {
			List<String[]> list = new ArrayList<>();
			if (flag == 1) {
				// 通过service里面的方法读取到要上传的数据
				list = employeeService.importFile(upload);
				// 将list中的各个字段封装成domain对象，并进行持久化(save)操作
				for (String[] strings : list) {
					Employee employee = new Employee();
					employee.setUsername(strings[1] + UUID.randomUUID().toString().substring(0, 6));
					employee.setPassword(strings[2]);
					employee.setEmail(strings[3]);
					String age = strings[4];
					if (StringUtils.isNotBlank(age)) {
						employee.setAge(Integer.parseInt(age));
					}
					String deptName = strings[5];
					if (StringUtils.isNotBlank(deptName)) {
						Department department = departmentService.findByName(deptName);
						employee.setDepartment(department);
					}
					// 数据持久化
					employeeService.save(employee);
				}
			}else if(flag == 2){
				// 通过service里面的方法读取到要上传的数据
				list = departmentService.importFile(upload);
				// 将list中的各个字段封装成domain对象，并进行持久化(save)操作
				for (String[] strings : list) {
					Department department = new Department();
					department.setName(strings[1]);
					// 数据持久化
					departmentService.save(department);
				}
			}
			addActionError("导入数据:" + list.size() + "条");
		} else {
			addActionError("导入失败！");
		}
		return SUCCESS;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	
	
	
	// ====================单个domain类型的上传文件代码========================
//	public String upload() throws Exception {
//		// 判断上传文件是否为空
//		if (upload != null) {
//				// 通过service里面的方法读取到要上传的数据
//				List<String[]> list = employeeService.importFile(upload);
//				// 将list中的各个字段封装成domain对象，并进行持久化(save)操作
//				for (String[] strings : list) {
//					Employee employee = new Employee();
//					employee.setUsername(strings[1] + UUID.randomUUID().toString());
//					employee.setPassword(strings[2]);
//					employee.setEmail(strings[3]);
//					String age = strings[4];
//					if (StringUtils.isNotBlank(age)) {
//						employee.setAge(Integer.parseInt(age));
//					}
//					String deptName = strings[5];
//					if (StringUtils.isNotBlank(deptName)) {
//						Department department = departmentService.findByName(deptName);
//						employee.setDepartment(department);
//					}
//					// 数据持久化
//					employeeService.save(employee);
//				}
//			addActionError("导入数据:" + list.size() + "条");
//		} else {
//			addActionError("导入失败！");
//		}
//		return SUCCESS;
//	}

}
