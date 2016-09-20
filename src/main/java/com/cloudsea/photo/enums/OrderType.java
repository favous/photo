package com.cloudsea.photo.enums;

public enum OrderType {

	ASC(1),DESC(2);
	
	private int index;
	
	private OrderType(int index) {
		this.index =index;
	}

	public int getIndex() {
		return index;
	}
	
	
}
