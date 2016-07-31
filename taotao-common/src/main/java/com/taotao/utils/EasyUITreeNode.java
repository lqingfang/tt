package com.taotao.utils;

import java.io.Serializable;

public class EasyUITreeNode implements Serializable{

	//节点Id
	private Integer id;
	//节点名称
	private String text;
	//节点状态：如果此节点有子节点:closed,否则:open
	private String state;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}
