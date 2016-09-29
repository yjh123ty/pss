package com.yjh.pss.domain;

import java.math.BigDecimal;

public class PurchaseBillItem {
	private Long id;
	private BigDecimal price;
	private BigDecimal num;
	private BigDecimal amount;
	private String descs;
	//双向多对一
	private Product product;
	//组合关系
	private PurchaseBill bill;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getNum() {
		return num;
	}
	public void setNum(BigDecimal num) {
		this.num = num;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getDescs() {
		return descs;
	}
	public void setDescs(String descs) {
		this.descs = descs;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public PurchaseBill getBill() {
		return bill;
	}
	public void setBill(PurchaseBill bill) {
		this.bill = bill;
	}
	@Override
	public String toString() {
		return "PurchaseBillItem [id=" + id + ", price=" + price + ", num=" + num + ", amount=" + amount + ", descs="
				+ descs + "]";
	}
	
}
