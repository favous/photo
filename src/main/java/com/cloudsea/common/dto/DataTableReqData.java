package com.cloudsea.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataTableReqData<T> implements Serializable{
	
	private static final long serialVersionUID = -8796187012885896664L;
	
	private int start;
	private int draw;
	private int length = 10;
	private Search search = new Search();
	private List<Column> columns = new ArrayList<Column>();
	private List<Order> order = new ArrayList<Order>();
	private T object;
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getDraw() {
		return draw;
	}
	public void setDraw(int draw) {
		this.draw = draw;
	}
	public Search getSearch() {
		return search;
	}
	public void setSearch(Search search) {
		this.search = search;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	public List<Order> getOrder() {
		return order;
	}
	public void setOrder(List<Order> order) {
		this.order = order;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public T getObject() {
		return object;
	}
	public void setObject(T object) {
		this.object = object;
	}
	

	/*public class Column{
		private Search search = new Search();
		private boolean orderable;
		private boolean searchable;
		private String data;
		private String name;
		public Search getSearch() {
			return search;
		}
		public void setSearch(Search search) {
			this.search = search;
		}
		public boolean isOrderable() {
			return orderable;
		}
		public void setOrderable(boolean orderable) {
			this.orderable = orderable;
		}
		public boolean isSearchable() {
			return searchable;
		}
		public void setSearchable(boolean searchable) {
			this.searchable = searchable;
		}
		public String getData() {
			return data;
		}
		public void setData(String data) {
			this.data = data;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	public class Search{
		private String value;
		private String regex;
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getRegex() {
			return regex;
		}
		public void setRegex(String regex) {
			this.regex = regex;
		}
	}
	
	public class Order{
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
	}*/

/*	var aaaa = [ columns[0][search][value] = [],
			search[value] = [ hh ],
			columns[3][search][value] = [], 
			columns[3][orderable] = [ true ],
			columns[0][search][regex] = [ false ], 
			columns[3][search][regex] = [ false ],
			columns[4][orderable] = [ true ], 
			columns[0][data] = [ menuId ], 
			columns[2][search][value] = [],
			columns[2][search][regex] = [ false ], 
			columns[0][name] = [], 
			order[0][dir] = [ asc ], 
			columns[3][name] = [],
			order[1][column] = [ 2 ],
			columns[3][searchable] = [ true ],
			columns[2][searchable] = [ true ], 
			start = [ 0 ],
			draw = [ 3 ], 
			columns[4][searchable] = [ true ],
			columns[1][search][value] = [], 
			search[regex] = [ false ],
			columns[0][searchable] = [ true ],
			columns[2][orderable] = [ true ], 
			columns[1][searchable] = [ true ], 
			columns[0][orderable] = [ true ],
			order[0][column] = [ 1 ], null ];*/
}
