package com.cloudsea.photo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Gallery implements Serializable{

	private static final long serialVersionUID = 713679747610537252L;
	
	private Long id;
	private Long menuId;
	private String sort;
	private Integer colNum;
	private Double querNum;
	private Integer loadNum = 50;
	private String enable;
	private Date createTime;
	private Date updateTime;//
	private List<Icon> iconList;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public Integer getColNum() {
		return colNum;
	}
	public void setColNum(Integer colNum) {
		this.colNum = colNum;
	}
	public Double getQuerNum() {
		return querNum;
	}
	public void setQuerNum(Double querNum) {
		this.querNum = querNum;
	}
	public Integer getLoadNum() {
		return loadNum;
	}
	public void setLoadNum(Integer loadNum) {
		this.loadNum = loadNum;
	}
	public List<Icon> getIconList() {
		return iconList;
	}
	public void setIconList(List<Icon> iconList) {
		this.iconList = iconList;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
