package com.yjh.pss.domain;

import java.math.BigDecimal;

/**
 * 入库单明细
 * @author Administrator
 *
 */
public class StockIncomeBillItem {
	private Long id;
	private BigDecimal price;// 入库价格
	private BigDecimal num;// 入库数量
	private BigDecimal amount;// 入库小计=入库价格*入库数量
	private String descs;// 备注
	private Product product;// 多对一,非空,入库产品
	private StockIncomeBill bill;// 组合关系,非空,属于那个入库订单
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
	public StockIncomeBill getBill() {
		return bill;
	}
	public void setBill(StockIncomeBill bill) {
		this.bill = bill;
	}
	@Override
	public String toString() {
		return "StockIncomeBillItem [id=" + id + ", price=" + price + ", num=" + num + ", amount=" + amount + ", descs="
				+ descs + "]";
	}
	

}
