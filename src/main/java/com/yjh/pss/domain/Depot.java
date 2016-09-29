package com.yjh.pss.domain;

import java.math.BigDecimal;

/**
 * 仓库模型
 */
public class Depot {
	private Long id;
	private String name;
	private BigDecimal maxCapacity;// 最大容量,参考值
	private BigDecimal currentCapacity;// 当前容量,参考值
	private BigDecimal totalAmount;// 总金额,参考值
	
	public Depot(){}
	
	public Depot(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getMaxCapacity() {
		return maxCapacity;
	}
	public void setMaxCapacity(BigDecimal maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	public BigDecimal getCurrentCapacity() {
		return currentCapacity;
	}
	public void setCurrentCapacity(BigDecimal currentCapacity) {
		this.currentCapacity = currentCapacity;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	
}
