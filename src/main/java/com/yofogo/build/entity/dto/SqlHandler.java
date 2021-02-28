package com.yofogo.build.entity.dto;

import java.util.ArrayList;
import java.util.List;

public class SqlHandler {

	public SqlHandler(){}
	public SqlHandler(int type){
		this.type=type;
	}
	public SqlHandler(SqlHandler sh){
		this.type=sh.getType();
		this.sql=sh.getSql();
		this.sqlFields=sh.getSqlFields();
		this.sqlTable=sh.getSqlTable();
		this.sqlWhere=sh.getSqlWhere();
		this.params.addAll(sh.getParams());
		this.uniqueGroups=sh.getUniqueGroups();
		this.status=sh.getStatus();
	}
	private int type;//1更新；2查询。
	private String sql;
	private String sqlFields;
	private String sqlTable;
	private String sqlWhere="";
	private List<Object> params=new ArrayList<Object>();
	private List<UniqueGroup> uniqueGroups;

	private int status=0;//0表示成功
	private String msg="";

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getSqlFields() {
		return sqlFields;
	}
	public void setSqlFields(String sqlFields) {
		this.sqlFields = sqlFields;
	}
	public String getSqlTable() {
		return sqlTable;
	}
	public void setSqlTable(String sqlTable) {
		this.sqlTable = sqlTable;
	}
	public String getSqlWhere() {
		return sqlWhere;
	}
	public void setSqlWhere(String sqlWhere) {
		this.sqlWhere = sqlWhere;
	}
	public List<Object> getParams() {
		return params;
	}
	public void setParams(List<Object> params) {
		this.params = params;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<UniqueGroup> getUniqueGroups() {
		return uniqueGroups;
	}
	public void setUniqueGroups(List<UniqueGroup> uniqueGroups) {
		this.uniqueGroups = uniqueGroups;
	}


	public class UniqueGroup{
		public UniqueGroup(String tbName){
			this.tableName=tbName;
		}
		private String tableName;
		private String sql;
		private List<Object> params;
		public String getSql() {
			return this.sql;
		}
		public List<Object> getParams() {
			return this.params;
		}

		public void AddKeyAndValue(String key,Object val){
			if(this.params==null) {
				this.params=new ArrayList<Object>();
				this.sql="SELECT COUNT(0) FROM "+this.tableName+" WHERE "+key+"=?";
			} else this.sql+=" AND "+key+"=?";
			this.params.add(val);
		}

	}

}
