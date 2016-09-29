package com.yjh.pss.web.action;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.yjh.pss.domain.Employee;
import com.yjh.pss.domain.Menu;
import com.yjh.pss.domain.Permission;
import com.yjh.pss.domain.Role;
import com.yjh.pss.query.RoleQuery;
import com.yjh.pss.query.PageList;
import com.yjh.pss.service.IMenuService;
import com.yjh.pss.service.IPermissionService;
import com.yjh.pss.service.IRoleService;

public class RoleAction extends CRUDAction<Role> {
	private static final long serialVersionUID = 1L;

	// 依赖注入
	private IRoleService roleService;
	private IPermissionService permissionService;
	private IMenuService menuService;
	
	// 前台对象模型 ,ModelDriver切换成栈顶对象，因此不需要getter,setter
	private Role role;

	// 分页对象,getter方法
	private PageList pageList;

	// 必须实例化,getter,setter方法
	private RoleQuery baseQuery = new RoleQuery();

	// 权限数组：多对多关系
	private Long[] ids;
	// 菜单数组：多对多关系
	private Long[] mids;


	// 列表
	@Override
	protected void list() throws Exception {
		this.pageList = roleService.findByQuery(baseQuery);
		putContext("allPermissions", permissionService.getAll());
		putContext("menus", menuService.getAll());
	}

	// 中转
	// 出现转换异常或者验证异常，跳转到input方法
	// @InputConfig(methodName = INPUT)
	@Override
	public String input() throws Exception {
		putContext("allPermissions", permissionService.getAll());
		putContext("menus", menuService.getAll());
		return INPUT;
	}

	// 保存
	// 添加了这个标签，那么如果我们输入了错误的参数类型，它会让我们跳转回本页面，并且会对错误信息进行回显@InputConfig(methodName
	// = INPUT)
	@InputConfig(methodName = "input")
	@Override
	public String save() throws Exception {
		// 将获取的权限进行保存
		for (Long pid : ids) {
			role.getPermissions().add(new Permission(pid));
		}
		// 将获取的菜单进行保存
		for (Long mid : mids) {
			role.getMenus().add(new Menu(mid));
		}

		// 若传入id为空，则是保存，否则为修改
		if (id != null) {
			roleService.update(role);
		} else {
			roleService.save(role);
			baseQuery.setCurrentPage(Integer.MAX_VALUE);
		}
		return RELOAD;
	}

	// //后台删除
	// @Override
	// public String delete() throws Exception {
	// if (id != null) {
	// roleService.delete(id);
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
				roleService.delete(id);
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

	// 只在调用input方法前调用,回显
	@Override
	public void prepareInput() throws Exception {
		if (id != null) { // 修改
			role = roleService.get(id);
			//回显权限
			Set<Permission> permissions = role.getPermissions();
			//回显可访问菜单
			Set<Menu> menus = role.getMenus();
			//获取前台传递的权限的id个数
			ids = new Long[permissions.size()];
			int index = 0;
			for (Permission permission : permissions) {
				ids[index++] = permission.getId();
			}
			
			//获取前台传递的可访问菜单的id个数
			mids = new Long[menus.size()];
			int indexMenu = 0;
			for (Menu menu : menus) {
				mids[indexMenu++] = menu.getId();
			}
			
		} else {
			role = new Role();
		}
		
	}

	// 只在调用save方法前调用
	@Override
	public void prepareSave() throws Exception {
		if (id != null) {// 从数据库获得该角色对象
			role = roleService.get(id);
			//将角色中的权限和可访问菜单都改为临时状态
			role.getPermissions().clear();
			role.getMenus().clear();
		} else {
			// 变成栈顶对象，保证没有修改的对象属性没有被丢失
			role = new Role();
		}
	}

	// 权限数组
	public Long[] getIds() {
		return ids;
	}
	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	//可访问的菜单数组
	public Long[] getMids() {
		return mids;
	}
	public void setMids(Long[] mids) {
		this.mids = mids;
	}
	
	// 依赖注入
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
	public void setPermissionService(IPermissionService permissionService) {
		this.permissionService = permissionService;
	}
	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}

	// 查询对象的封装
	public RoleQuery getBaseQuery() {
		return baseQuery;
	}

	public void setBaseQuery(RoleQuery baseQuery) {
		this.baseQuery = baseQuery;
	}

	// 查询后结果的封装
	public PageList getPageList() {
		return pageList;
	}

	// 模型驱动:我们不需要再为模型提供getter,setter，而是直接通过模型，再前置保存方法的时候，就为其创建一个domain对象，
	// 使这个domain对象变成栈顶对象，保证没有修改的对象属性没有被丢失
	@Override
	public Role getModel() {
		return role;
	}

}
