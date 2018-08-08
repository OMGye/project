package com.util;

import java.util.List;

/**
 * 分页类的封装
 * @author upupgogogo
 *
 */
public class PageBean<T> {
	private int pageNum;	// 当前页数
	private int totalCount; // 总记录数
	private int totalPage; // 总页数
	private int pageSize;	// 每页显示的记录数
	private List<T> list; // 每页显示数据的集合

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getPageNum() {

		return pageNum;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public List<T> getList() {
		return list;
	}
}
