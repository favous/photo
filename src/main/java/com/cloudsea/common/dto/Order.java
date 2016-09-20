package com.cloudsea.common.dto;

import java.util.ArrayList;
import java.util.List;

public class Order {
	private List<String> dir = new ArrayList<String>();
	private List<Integer> column = new ArrayList<Integer>();
	public List<String> getDir() {
		return dir;
	}
	public void setDir(List<String> dir) {
		this.dir = dir;
	}
	public List<Integer> getColumn() {
		return column;
	}
	public void setColumn(List<Integer> column) {
		this.column = column;
	}
}
