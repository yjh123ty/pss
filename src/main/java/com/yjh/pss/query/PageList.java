package com.yjh.pss.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

public class PageList {
	// 当前页码
	private int currentPage;
	// 一页条数
	private int pageSize;
	// 总的条数
	private int totalCount;
	// 总的页数:计算
	private int totalPage;
	// 当前页的数据
	private List rows = new ArrayList();
	
	public PageList() {
	}
	
	public PageList(int currentPage, int pageSize, int totalCount) {
		// 1.做负数错误处理
		this.currentPage = currentPage< 1 ? 1 : currentPage;
		this.pageSize = pageSize< 1 ? 10 : pageSize;
		this.totalCount = totalCount;
		
		// 2.计算totalPage	
		// ceil(double a)有小数点，取整+1
		this.totalPage = (int) Math.ceil(this.totalCount * 1.0D / this.pageSize);
		
		// 3.判断当前页码大于总的页数
		this.currentPage = this.currentPage > this.totalPage ? 	this.totalPage : this.currentPage;
	}

	//定义首页，尾页，页数
	@JSON(serialize = false)
	public int getBegin(){
		if(currentPage == 1){
			return 1;
		}else{
			return (currentPage - 1) * pageSize + 1;
		}
	}
	
	@JSON(serialize = false)
	public int getEnd(){
		if(currentPage == totalPage){
			return totalCount;
		}else{
			return currentPage * pageSize;
		}
	}
	
	//处理首页，尾页
	@JSON(serialize = false)
	public String getPage(){
		StringBuilder builder = new StringBuilder();
		//处理首页
		if(this.currentPage == 1){
			builder.append("<li class='prev disabled'><a href='#'>首页</a></li>");
			builder.append("<li class='prev disabled'><a href='#'>上一页</a></li>");
		}else{
			builder.append("<li class='prev'><a href='#' title='跳转到首页' onclick='go(1);'>首页</a></li>");
			builder.append("<li class='prev'><a href='#' title='跳转到 " + (this.currentPage - 1) + "页' onclick='go("+(this.currentPage - 1)+");'>上一页</a></li>");
		}
		for (int i = 1; i <= this.totalPage; i++) {
			if (i == this.currentPage) {
				builder.append("<li class='active'><a href='#'>" + i + "</a></li>");
			} else {
				builder
						.append("<li class='next'><a href='#' title='跳转到" + i + "页' onclick='go(" + i + ");'>" + i + "</a></li>");
			}
		}
		
		//尾页
		if(this.currentPage == this.totalPage){
			builder.append("<li class='next disabled'><a href='#'>下一页</a></li>");
			builder.append("<li class='next disabled'><a href='#'>尾页</a></li>");
		}else{
			builder.append("<li class='next'><a href='#' title='跳转到" + (this.currentPage + 1) + "页' onclick='go("
					+ (this.currentPage + 1) + ");'>下一页</a></li>");
			builder.append("<li class='next'><a href='#' title='跳转到尾页' onclick='go(" + this.totalPage + ");'>尾页</a></li>");
		}
		return builder.toString();
	}

	@JSON(serialize = false)
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	@JSON(name = "total")
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	@JSON(serialize = false)
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	@Override
	public String toString() {
		return "PageList [currentPage=" + currentPage + ", pageSize=" + pageSize + ", totalCount=" + totalCount
				+ ", totalPage=" + totalPage + ", rows=" + rows + "]";
	}
	
	
}
