package com.jzwl.common.page;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据类，用来处理分页查询
 * 
 * @author zhang guo yu
 * @version 1.0
 * @since 2014-12-24
 * */
public class PageObject implements Serializable {
	private List datasource; // 查询数据集
	private int currentPage; // 当前页
	private int pageSize; // 每页显示数据条数
	private int absolutePage; // 共计多少页数据
	private int totalCount; // 共计多少条记录

	/**
	 * 获取数据集
	 * @return
	 */
	public List getDatasource() {
		return datasource;
	}

	/**
	 * 设置数据集
	 * @param datasource
	 */
	public void setDatasource(List datasource) {
		this.datasource = datasource;
	}

	/**
	 * 获取当前是第几页
	 * @return
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 设置当前是第几页
	 * @param currentPage
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * 获取每页显示条数
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页显示条数
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获取共计多少页
	 * @return
	 */
	public int getAbsolutePage() {
		return absolutePage;
	}
	
	/**
	 * 设置共计多少页
	 * @return
	 */
	public void setAbsolutePage(int absolutePage) {
		this.absolutePage = absolutePage;
	}

	/**
	 * 获取共计多少条记录
	 * @return
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置共计多少条记录
	 * @return
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}
