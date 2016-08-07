package com.taotao.search.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {

	//总记录数
	private Long totalRecord;
	//查询分页列表数据
	private List<SearchItem> itemList;
	//当前页码
	private Integer curPage;
	//总页数
	private Integer pages;
	public Long getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(Long totalRecord) {
		this.totalRecord = totalRecord;
	}
	public List<SearchItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}
	public Integer getCurPage() {
		return curPage;
	}
	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}
	public Integer getPages() {
		return pages;
	}
	public void setPages(Integer pages) {
		this.pages = pages;
	}
}
