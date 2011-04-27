package com.ppstream.mt.utils.pager;

import java.io.Serializable;

public class PgInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer totalCount; // 总共记录数
	private Integer pageSize; // 每页条目数
	private Integer sumPage;  // 总页数
	private Integer pageNo;  // 当前页
	private boolean first;
	private boolean last;
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getSumPage() {
		return sumPage;
	}
	public void setSumPage(Integer sumPage) {
		this.sumPage = sumPage;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
	public boolean isLast() {
		return last;
	}
	public void setLast(boolean last) {
		this.last = last;
	}
	
}
