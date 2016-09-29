package com.yjh.pss.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PurchaseBill {
	private Long id;
	private Date vdate;
	private BigDecimal totalAmount;
	private BigDecimal totalNum;
	private Date inputTime  = new Date();;
	private Date auditorTime;
	private Integer status;
	
	//多对一,非空：供应商
	private Supplier supplier;
	
	//多对一：审核人
	private Employee auditor;
	
	//多对一,非空：录入人
	private Employee inputUser;
	
	//多对一,非空：采购员
	private Employee buyer;
	
	//单据明细：一对多
	private List<PurchaseBillItem> billItems = new ArrayList<>();

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

	public Employee getBuyer() {
		return buyer;
	}

	public void setBuyer(Employee buyer) {
		this.buyer = buyer;
	}

	public List<PurchaseBillItem> getBillItems() {
		return billItems;
	}

	public void setBillItems(List<PurchaseBillItem> billItems) {
		this.billItems = billItems;
	}
	
	
}
