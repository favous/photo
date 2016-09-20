package com.cloudsea.photo.entity;

public class Icon extends BaseEntity {

	private static final long serialVersionUID = 206775429956587214L;
	
	private Long menuId;
	private Long galleryId;
	private int orderNo;
	private String src;
	private String pictureSrc;
	
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public Long getGalleryId() {
		return galleryId;
	}
	public void setGalleryId(Long galleryId) {
		this.galleryId = galleryId;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getPictureSrc() {
		return pictureSrc;
	}
	public void setPictureSrc(String pictureSrc) {
		this.pictureSrc = pictureSrc;
	}
}
