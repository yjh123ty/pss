package com.yjh.pss.query;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.yjh.pss.domain.PurchaseBillItem;

public class PurchaseBillItemQuery extends BaseQuery {
	// 查询条件不要放初始化的值
	private Integer status;
	// 交易时间段
	private Date beginDate;
	private Date endDate;
	// 获取分组条件
	private String groupBy;
	
	public PurchaseBillItemQuery() {
		super(PurchaseBillItem.class.getName());
	}

	@Override
	protected void addWhere() {
		//状态
		if(status != null && status != -2L){
			addWhere("o.bill.status=?", status);
		}
		//时间段
		if(beginDate != null){
			addWhere("o.bill.vdate >=?", beginDate);
		}
		if(endDate != null){
			// 对结束时间+1天的处理
			Date date = DateUtils.addDays(endDate, 1);
			addWhere("o.bill.vdate<?", date);

		}
	}
	
	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	
}
