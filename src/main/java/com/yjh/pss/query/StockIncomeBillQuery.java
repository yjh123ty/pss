package com.yjh.pss.query;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.yjh.pss.domain.StockIncomeBill;

public class StockIncomeBillQuery extends BaseQuery {
	// 交易时间段
		private Date beginDate;
		private Date endDate;
		// 查询条件不要放初始化的值
		private Integer status;
		
		public StockIncomeBillQuery() {
			super(StockIncomeBill.class.getName());
		}

		@Override
		protected void addWhere() {
//			//用户必须输入两个参数，一般不这样做
//			if(StringUtils.isNotBlank(name)){
//				addWhere("o.vdate between ? and ?", beginDate,endDate);
//			}
//		}
			if(status != null && status != -2){
				addWhere(" o.status = ? ", status);
			}
			if(beginDate != null){
				addWhere(" o.vdate >= ? ", beginDate);
			}
			if(endDate != null){
				Date date = DateUtils.addDays(endDate, 1);
				addWhere(" o.vdate < ? ", date);
			}
		
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

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}
}
