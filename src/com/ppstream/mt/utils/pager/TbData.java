package com.ppstream.mt.utils.pager;

import java.io.Serializable;
import java.util.List;

public class TbData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List list;
	private PgInfo pageInfo;
	
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public PgInfo getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PgInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	
}
