package com.yjh.pss.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 及时库存
 * @author Administrator
 *
 */
public class ProductStock {
	private Long id;
	// incomeDate depot_id product_id num price amount
	// 3          1        100        100 200   20000
	// 5          1        100        200 250   50000
	//上面2条记录必须合并,加权平均法
	// 5          1        100        300 70000/300  70000
	private BigDecimal num;// 当前库存数量
	private BigDecimal amount;// 小计
	private BigDecimal price;// 库存价格
	private Date incomeDate;// 入库时间
	private Boolean warning = true;// 是否发出预警
	private BigDecimal topNum = new BigDecimal(100);// 最大库存量
	private BigDecimal bottomNum = new BigDecimal(50);// 最小库存量
	// 业务要求,同时唯一：同一个仓库里面的产品是唯一的
	// product_id,depot_id
	// 100 1
	// 100 1错误
	// 100 2
	// `product_id` bigint(20) NOT NULL,
	private Product product;// 多对一,非空
	// `depot_id` bigint(20) NOT NULL,
	private Depot depot;// 多对一,非空
	
	
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
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



	public BigDecimal getPrice() {
		return price;
	}



	public void setPrice(BigDecimal price) {
		this.price = price;
	}



	public Date getIncomeDate() {
		return incomeDate;
	}



	public void setIncomeDate(Date incomeDate) {
		this.incomeDate = incomeDate;
	}



	public Boolean getWarning() {
		return warning;
	}



	public void setWarning(Boolean warning) {
		this.warning = warning;
	}



	public BigDecimal getTopNum() {
		return topNum;
	}



	public void setTopNum(BigDecimal topNum) {
		this.topNum = topNum;
	}



	public BigDecimal getBottomNum() {
		return bottomNum;
	}



	public void setBottomNum(BigDecimal bottomNum) {
		this.bottomNum = bottomNum;
	}



	public Product getProduct() {
		return product;
	}



	public void setProduct(Product product) {
		this.product = product;
	}



	public Depot getDepot() {
		return depot;
	}



	public void setDepot(Depot depot) {
		this.depot = depot;
	}



	@Override
	public String toString() {
		return "ProductStock [id=" + id + ", num=" + num + ", amount=" + amount + ", price=" + price + ", incomeDate="
				+ incomeDate + ", warning=" + warning + ", topNum=" + topNum + ", bottomNum=" + bottomNum + "]";
	}
	

}
