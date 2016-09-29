package com.yjh.pss.web.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.core.metadata.TableMetaDataProvider;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.yjh.pss.domain.PurchaseBill;
import com.yjh.pss.domain.PurchaseBillItem;
import com.yjh.pss.query.PageList;
import com.yjh.pss.query.PurchaseBillQuery;
import com.yjh.pss.service.IEmployeeService;
import com.yjh.pss.service.IPurchaseBillService;
import com.yjh.pss.service.ISupplierService;

public class PurchaseBillAction extends CRUDAction<PurchaseBill> {
	private static final long serialVersionUID = 1L;

	// 依赖注入
	private IPurchaseBillService purchaseBillService;
	private IEmployeeService employeeService;
	private ISupplierService supplierService;

	// 前台对象模型 ,ModelDriver切换成栈顶对象，因此不需要getter,setter
	private PurchaseBill purchaseBill;

	// 分页对象,getter方法
	private PageList pageList;

	// 必须实例化,getter,setter方法
	private PurchaseBillQuery baseQuery = new PurchaseBillQuery();

	// 列表
	@Override
	protected void list() throws Exception {
		this.pageList = purchaseBillService.findByQuery(baseQuery);
	}

	// 中转
	@Override
	public String input() throws Exception {
		putContext("allSuppliers", supplierService.getAll());
		putContext("allBuyers", employeeService.getBuyers());
		return INPUT;
	}

	// 保存
	@InputConfig(methodName = "input")
	@Override
	public String save() throws Exception {
		BigDecimal totalAmount = new BigDecimal(0);// 总金额
		BigDecimal totalNum = new BigDecimal(0);// 总数量
		// 获取当前采购明细集合(从一方获取多方,已经建立关系)
		List<PurchaseBillItem> billItems = purchaseBill.getBillItems();
		// 没有明细
		if (billItems.size() == 0) {
			return RELOAD;
		}
		for (PurchaseBillItem purchaseBillItem : billItems) {
			purchaseBillItem.setBill(purchaseBill);
			// 计算小计(每一件商品的 ：价格 * 数量)
			purchaseBillItem.setAmount(purchaseBillItem.getPrice().multiply(purchaseBillItem.getNum()));

			totalAmount = totalAmount.add(purchaseBillItem.getAmount());
			totalNum = totalNum.add(purchaseBillItem.getNum());
		}

		// 设置总金额,总数量
		purchaseBill.setTotalAmount(totalAmount);
		purchaseBill.setTotalNum(totalNum);

		// 设置录入员
		purchaseBill.setInputUser(getLoginUser());

		// 若传入id为空，则是保存，否则为修改
		if (id != null) {
			Integer status = purchaseBill.getStatus();
			System.out.println("save方法中，bill的id为：" + id);
			if (status != 0) {
				System.out.println("检测到你尝试恶意修改订单！！！！！！！");
				return "permission";
			}
			purchaseBillService.update(purchaseBill);
			addActionMessage("修改成功");
		} else {
			// 新增订单状态为待审核
			purchaseBill.setStatus(0);
			purchaseBillService.save(purchaseBill);
			// 更新后跳转至最后一页
			baseQuery.setCurrentPage(Integer.MAX_VALUE);
			addActionMessage("保存成功");
		}
		return RELOAD;
	}

	// 使用ajax删除
	@Override
	public String delete() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			if (id != null) {
				// 根据传入id得到要删除的账单
				PurchaseBill purchaseBill = purchaseBillService.get(id);
				// 拿到账单的状态
				Integer status = purchaseBill.getStatus();
				// 当订单状态不为1（已审核）也不为-1（作废）的时候，才能删除这一单
				if (status != -1 && status != 1) {
					purchaseBillService.delete(id);
					out.print("{\"success\":true,\"msg\":\"删除成功\"}");
				} else {
					out.print("{\"success\":false,\"msg\":\"该单不能被删除！\"}");
				}
			} else {
				out.print("{\"success\":false,\"msg\":\"删除必须传递id！\"}");
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

	// 希望vdate有默认值
	// 只在调用input方法前调用
	@Override
	public void prepareInput() throws Exception {
		if (id != null) { // 修改
			this.purchaseBill = purchaseBillService.get(id);
		} else {
			// 设置交易时间，也可以在domain里面new
			purchaseBill = new PurchaseBill();
			purchaseBill.setVdate(new Date());
		}
	}

	// 只在调用save方法前调用
	@Override
	public void prepareSave() throws Exception {
		if (id != null) {
			purchaseBill = purchaseBillService.get(id);
			purchaseBill.setBuyer(null);
			purchaseBill.setSupplier(null);
			purchaseBill.getBillItems().clear();
		} else {
			// 变成栈顶对象，保证没有修改的对象属性没有被丢失
			purchaseBill = new PurchaseBill();
		}
	}

	// 审核
	public String check() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			if (id != null) {
				// 根据传入id得到审核的账单
				PurchaseBill purchaseBill = purchaseBillService.get(id);
				// 拿到账单的状态
				Integer status = purchaseBill.getStatus();
				// 当订单状态为0（待审核）时候，才能审核这一单
				if (status == 0) {
					purchaseBill.setStatus(1);
					purchaseBillService.save(purchaseBill);
					out.print("{\"success\":true,\"msg\":\"审核成功\"}");
				} else {
					out.print("{\"success\":false,\"msg\":\"该单不能审核！\"}");
				}
			} else {
				out.print("{\"success\":false,\"msg\":\"审核必须传递id！\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();// 便于排错
			out.print("{\"success\":false,\"msg\":\"审核异常:" + e.getMessage() + "\"}");
		}
		return null;// 不返回页面,只返回文本

	}

	// 依赖注入
	public void setPurchaseBillService(IPurchaseBillService purchaseBillService) {
		this.purchaseBillService = purchaseBillService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setSupplierService(ISupplierService supplierService) {
		this.supplierService = supplierService;
	}

	// 查询对象的封装
	public PurchaseBillQuery getBaseQuery() {
		return baseQuery;
	}

	public void setBaseQuery(PurchaseBillQuery baseQuery) {
		this.baseQuery = baseQuery;
	}

	// 查询后结果的封装
	public PageList getPageList() {
		return pageList;
	}

	// 模型驱动
	@Override
	public PurchaseBill getModel() {
		return purchaseBill;
	}

}
