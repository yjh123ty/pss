package com.yjh.pss.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 入库单
 * @author Administrator
 *
 */
public class StockIncomeBill {
	private Long id;
	private Date vdate;// 交易时间
	private BigDecimal totalAmount;// 总金额
	private BigDecimal totalNum;// 总数量
	private Date inputTime = new Date();// 录入时间
	private Date auditorTime;// 审核时间
	/**
	 * 状态:0待审,1已审，-1作废
	 */
	private Integer status = 0;

	private Supplier supplier;// 多对一，非空,供应商
	private Employee auditor;// 多对一，可以为空,审核人(入库经理)
	private Employee inputUser;// 多对一，非空(录入人,当前登录用户)
	
	private Employee keeper;// 多对一，非空(入库员)
	private Depot depot;// 多对一,非空
	// 一般组合关系使用List
	private List<StockIncomeBillItem> items = new ArrayList<StockIncomeBillItem>();
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Date getVdate() {
		return vdate;
	}



	public void setVdate(Date vdate) {
		this.vdate = vdate;
	}



	public BigDecimal getTotalAmount() {
		return totalAmount;
	}



	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}



	public BigDecimal getTotalNum() {
		return totalNum;
	}



	public void setTotalNum(BigDecimal totalNum) {
		this.totalNum = totalNum;
	}



	public Date getInputTime() {
		return inputTime;
	}



	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}



	public Date getAuditorTime() {
		return auditorTime;
	}



	public void setAuditorTime(Date auditorTime) {
		this.auditorTime = auditorTime;
	}



	public Integer getStatus() {
		return status;
	}



	public void setStatus(Integer status) {
		this.status = status;
	}



	public Supplier getSupplier() {
		return supplier;
	}



	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}



	public Employee getAuditor() {
		return auditor;
	}



	public void setAuditor(Employee auditor) {
		this.auditor = auditor;
	}



	public Employee getInputUser() {
		return inputUser;
	}



	public void setInputUser(Employee inputUser) {
		this.inputUser = inputUser;
	}



	public Employee getKeeper() {
		return keeper;
	}



	public void setKeeper(Employee keeper) {
		this.keeper = keeper;
	}



	public Depot getDepot() {
		return depot;
	}



	public void setDepot(Depot depot) {
		this.depot = depot;
	}



	public List<StockIncomeBillItem> getItems() {
		return items;
	}



	public void setItems(List<StockIncomeBillItem> items) {
		this.items = items;
	}



	@Override
	public String toString() {
		return "StockIncomeBill [id=" + id + ", vdate=" + vdate + ", totalAmount=" + totalAmount + ", totalNum="
				+ totalNum + ", inputTime=" + inputTime + ", auditorTime=" + auditorTime + ", status=" + status + "]";
	}

	
}
