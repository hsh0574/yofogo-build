package com.yofogo.build.entity.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SelectItems {
	private int type;
	private Object items;
	private String sql;
	private Map<String,String> params=new HashMap<String,String>();
	private List<String> paramNames=new ArrayList<String>();
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Object getItems() {
		return items;
	}
	public void setItems(Object items) {
		this.items = items;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	public List<String> getParamNames() {
		return paramNames;
	}
	public void setParamNames(List<String> paramNames) {
		this.paramNames = paramNames;
	}
}
