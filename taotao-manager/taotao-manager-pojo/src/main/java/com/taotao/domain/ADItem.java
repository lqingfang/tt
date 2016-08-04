package com.taotao.domain;

import java.io.Serializable;

public class ADItem implements Serializable{

//	"srcB": 
//    "height": 240,
//    "alt": "",
//    "width": 670,
//    "src": 
//    "widthB": 550,
//    "href": 
//    "heightB": 240
	 
	
	 private String alt; //提示语
	 
	 private String src; // 
	 private Integer width;
	 private Integer height; //图片高度
	 
	 private String srcB; //图片地址
	 private Integer widthB;
	 private Integer heightB;
	 
	 private String href;  //购买地址
	 
	public String getSrcB() {
		return srcB;
	}
	public void setSrcB(String srcB) {
		this.srcB = srcB;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public Integer getWidthB() {
		return widthB;
	}
	public void setWidthB(Integer widthB) {
		this.widthB = widthB;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public Integer getHeightB() {
		return heightB;
	}
	public void setHeightB(Integer heightB) {
		this.heightB = heightB;
	}
}
