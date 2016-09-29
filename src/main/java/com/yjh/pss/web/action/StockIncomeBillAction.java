package com.yjh.pss.web.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.yjh.pss.domain.PurchaseBill;
import com.yjh.pss.domain.StockIncomeBill;
import com.yjh.pss.domain.StockIncomeBillItem;
import com.yjh.pss.query.StockIncomeBillQuery;
import com.yjh.pss.query.PageList;
import com.yjh.pss.service.IEmployeeService;
import com.yjh.pss.service.IStockIncomeBillItemService;
import com.yjh.pss.service.IStockIncomeBillService;
import com.yjh.pss.service.ISupplierService;


public class StockIncomeBillAction extends CRUDAction<StockIncomeBill>{
	private static final long serialVersionUID = 1L;
	
	//依赖注入
	private IStockIncomeBillService stockIncomeBillService;
	private IEmployeeService employeeService;
	private ISupplierService supplierService;
	
	//前台对象模型 ,ModelDriver切换成栈顶对象，因此不需要getter,setter
	private StockIncomeBill stockIncomeBill;
	
	// 分页对象,getter方法
	private PageList pageList;
	
	// 必须实例化,getter,setter方法
	private StockIncomeBillQuery baseQuery = new StockIncomeBillQuery();
	
	//列表
	@Override
	protected void list() throws Exception {
		this.pageList = stockIncomeBillService.findByQuery(baseQuery);
	}
	
	
	//中转
	@Override
	public String input() throws Exception {
		putContext("allSuppliers", supplierService.getAll());
		putContext("allKeepers", employeeService.getAll());
		return INPUT;
	}
	
	//保存
	@InputConfig(methodName="input")	
	@Override
	public String save() throws Exception {
		BigDecimal totalAmount = new BigDecimal(0);// 总金额
		BigDecimal totalNum = new BigDecimal(0);// 总数量
		// 获取当前采购明细集合(从一方获取多方,已经建立关系)
		List<StockIncomeBillItem> items = stockIncomeBill.getItems();
		// 没有明细
		if (items.size() == 0) {
			return RELOAD;
		}
		for (StockIncomeBillItem stockIncomeBillItem : items) {
			//建立多方与一方的关联
			stockIncomeBillItem.setBill(stockIncomeBill);
			
			stockIncomeBillItem.setAmount(stockIncomeBillItem.getNum().multiply(stockIncomeBillItem.getPrice()));
			totalAmount = totalAmount.add(stockIncomeBillItem.getAmount());
			totalNum = totalNum.add(stockIncomeBillItem.getNum());
		}
		stockIncomeBill.setTotalAmount(totalAmount);
		stockIncomeBill.setTotalNum(totalNum);
		stockIncomeBill.setStatus(0);
		//设置一方到多方的关系
		stockIncomeBill.setItems(items);
		// 设置录入员
		stockIncomeBill.setInputUser(getLoginUser());
		
		//若传入id为空，则是保存，否则为修改
		if(id != null){
			stockIncomeBillService.update(stockIncomeBill);
			addActionMessage("修改成功");
		}else {
			stockIncomeBillService.save(stockIncomeBill);
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
				stockIncomeBillService.delete(id);
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
			stockIncomeBill = stockIncomeBillService.get(id);
		}else{
			// 设置交易时间，也可以在domain里面new
			stockIncomeBill = new StockIncomeBill();
			stockIncomeBill.setVdate(new Date());
		}
	}
	
	//只在调用save方法前调用
	@Override
	public void prepareSave() throws Exception {
		if (id != null) {
			stockIncomeBill = stockIncomeBillService.get(id);
			stockIncomeBill.setKeeper(null);
			stockIncomeBill.setSupplier(null);
			stockIncomeBill.getItems().clear();
		} else {
			//变成栈顶对象，保证没有修改的对象属性没有被丢失
			stockIncomeBill = new StockIncomeBill();
		}
	}
	


	
	 //依赖注入
	public void setStockIncomeBillService(IStockIncomeBillService stockIncomeBillService) {
		this.stockIncomeBillService = stockIncomeBillService;
	}
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	public void setSupplierService(ISupplierService supplierService) {
		this.supplierService = supplierService;
	}

	// 查询对象的封装
	public StockIncomeBillQuery getBaseQuery() {
		return baseQuery;
	}
	public void setBaseQuery(StockIncomeBillQuery baseQuery) {
		this.baseQuery = baseQuery;
	}

	 // 查询后结果的封装
	public PageList getPageList() {
		return pageList;
	}

    // 模型驱动
	@Override
	public StockIncomeBill getModel() {
		return stockIncomeBill;
	}
	

}
