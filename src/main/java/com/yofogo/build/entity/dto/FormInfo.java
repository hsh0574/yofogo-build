package com.yofogo.build.entity.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.yofogo.build.generator.DataBaseProduct;


public class FormInfo implements java.io.Serializable {
	private Integer fiid;
	private Integer fcid;
	private String names;
	private String moduleName="module";//所属模块名称
	private String tag;
	private Integer types;
	private Integer cellCount=2;
	private String uniqueKeys;
	private String primaryKey;
	private Integer primaryType;//1自增；2程序生成
	private String template;
	private String path;
	private String tableName;
	private Date created;
	private Date upDate;
	private List<FormElementField> eleFields;
	private Map<Integer,FormElementField> fdfidToFields;
	private List<FormDataTable> formDataTables=new ArrayList<FormDataTable>();
	private List<FormDataWhere> formDataWheres=new ArrayList<FormDataWhere>();
	//使用时生成值的字段。
	private SqlHandler selectAll;
	private String primaryKeyToEntityName;

	public Integer getFiid() {
		return fiid;
	}
	public void setFiid(Integer fiid) {
		this.fiid = fiid;
	}
	public Integer getFcid() {
		return fcid;
	}
	public void setFcid(Integer fcid) {
		this.fcid = fcid;
	}
	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Integer getTypes() {
		return types;
	}
	public void setTypes(Integer types) {
		this.types = types;
	}
	public Integer getCellCount() {
		return cellCount;
	}
	public void setCellCount(Integer cellCount) {
		this.cellCount = cellCount;
	}
	public String getUniqueKeys() {
		return uniqueKeys;
	}
	public void setUniqueKeys(String uniqueKeys) {
		this.uniqueKeys = uniqueKeys;
	}
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	public Integer getPrimaryType() {
		return primaryType;
	}
	public void setPrimaryType(Integer primaryType) {
		this.primaryType = primaryType;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTableName() {
		if(this.types==1) return "form_data_commons";
		else return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpDate() {
		return upDate;
	}
	public void setUpDate(Date upDate) {
		this.upDate = upDate;
	}
	public List<FormElementField> getEleFields() {
		return eleFields;
	}
	public String getPrimaryKeyToEntityName() {
		return primaryKeyToEntityName;
	}
	public void setPrimaryKeyToEntityName(String primaryKeyToEntityName) {
		this.primaryKeyToEntityName = primaryKeyToEntityName;
	}
	public void setEleFields(List<FormElementField> eleFields) {
		this.eleFields = eleFields;
		fdfidToFields=new HashMap<Integer, FormElementField>();
		if(this.eleFields==null) return;
		for(FormElementField field : this.eleFields){
			fdfidToFields.put(field.getFdfid(), field);
		}
	}
	public List<FormDataTable> getFormDataTables() {
		return formDataTables;
	}
	public void setFormDataTables(List<FormDataTable> formDataTables) {
		this.formDataTables = formDataTables;
	}
	public List<FormDataWhere> getFormDataWheres() {
		return formDataWheres;
	}
	public void setFormDataWheres(List<FormDataWhere> formDataWheres) {
		this.formDataWheres = formDataWheres;
	}


	/*************************************************************/

	/**
	 * 根据表单字段编号获取表单字段对象
	 * @param fdfid
	 * @return
	 */
	public FormElementField getFormElementField(int fdfid){
		if(fdfidToFields!=null) return fdfidToFields.get(fdfid);
		return null;
	}

	public SqlHandler getInsertSqlHandler(Map<String,Object> datas){
		SqlHandler sh=new SqlHandler(1);
		StringBuilder sql=new StringBuilder("INSERT INTO "+getTableName()+"(id");
		List<Object> params=new ArrayList<Object>();
		params.add(UUID.randomUUID().toString());
		if(this.types==1){
			sql.append(",system_fiid");
			params.add(this.fiid);
		}
		for(FormElementField field : this.eleFields){
			Object valObj=datas.get(field.getTag());
			if(valObj==null){
				if(field.getIsMust()==1) {
					sh.setStatus(1);
					sh.setMsg(field.getNames()+"为必填项！");
					return sh;
				}
				continue;
			}
			sql.append(","+ DataBaseProduct.getDBFieldSymbols(field.getDataTag()));
			if(valObj instanceof String[]) valObj=buildArrayVal((String[])valObj);
			params.add(valObj);
		}
		sql.append(",system_created) VALUES(?");
		for(int i=1;i<params.size();i++) sql.append(",?");
		sql.append(","+DataBaseProduct.getDBDateTime()+")");
		sh.setSql(sql.toString());
		sh.setParams(params);
		return sh;
	}

	public SqlHandler getUpdateSqlHandler(Map<String,Object> datas){
		SqlHandler sh=new SqlHandler(1);
		Object idVal=datas.get("id");
		if(idVal==null || "".equals(idVal.toString())){
			sh.setStatus(1);
			sh.setMsg("找不到唯一标识数据！");
			return sh;
		}
		StringBuilder sql=new StringBuilder("UPDATE "+getTableName()+" SET system_update="+DataBaseProduct.getDBDateTime());
		if(this.uniqueKeys!=null && !"".equals(this.uniqueKeys)){
			for(String group : this.uniqueKeys.split(";")){
				if("".equals(group.trim())) continue;
				SqlHandler.UniqueGroup ub=sh.new UniqueGroup(getTableName());
				for(String key : group.split(",")){
					Object val=datas.get(key);
					if(val==null) {
						for(String k :  group.split(",")) datas.remove(k);
						ub=null;
						break;
					}
					if(val instanceof String[]) val=buildArrayVal((String[])val);
					ub.AddKeyAndValue(key, val);
				}
				if(ub!=null) sh.getUniqueGroups().add(ub);
			}
		}
		List<Object> params=new ArrayList<Object>();
		for(FormElementField field : this.eleFields){
			Object valObj=datas.get(field.getTag());
			if(valObj==null) continue;
			sql.append(","+DataBaseProduct.getDBFieldSymbols(field.getDataTag())+"=?");
			if(valObj instanceof String[]) valObj=buildArrayVal((String[])valObj);
			params.add(valObj);
		}
		if(this.types==1){
			sql.append(" WHERE system_fiid=? and id=?");
			params.add(this.fiid);
			params.add(idVal);
		}else {
			sql.append(" WHERE id=?");
			params.add(idVal);
		}
		sh.setSql(sql.toString());
		sh.setParams(params);
		return sh;
	}

	public SqlHandler getSelectSqlHandler(String id){
		SqlHandler sh=new SqlHandler(getSqlSelectAll());
		String sql=sh.getSql();
		if(this.types==1) sql+=" AND id=?";
		else sql+=" WHERE "+this.getPrimaryKey()+"=?";
		sh.setSql(sql);
		sh.getParams().add(id);
		return sh;
	}

	public SqlHandler getSelectSqlHandler_(Map<String,Object> datas){
		SqlHandler sh=new SqlHandler(getSqlSelectAll());


		return sh;
	}


	public SqlHandler getSqlSelectAll() {
		if(this.selectAll==null){
			this.selectAll=new SqlHandler(2);
			StringBuilder sql=new StringBuilder("SELECT ");
			StringBuilder sqlFields=new StringBuilder();
			String spStr="";
			if(this.types==1){
				sqlFields.append("id");
				spStr=",";
			}
			//else if(this.primaryType!=3)sql.append(this.primaryKey);
			for(FormElementField field : this.getEleFields()){
				if(this.types==1) sqlFields.append(spStr+DataBaseProduct.getDBFieldSymbols(field.getDataTag())+" AS "+(field.getFefid()==null?field.getDataTag():field.getTag()));
				else sqlFields.append(spStr+DataBaseProduct.getDBFieldSymbols(field.getDataTag()));
				if("".equals(spStr)) spStr=",";
			}
			if(this.types==1) sqlFields.append(",system_created,system_update");
			sql.append(sqlFields);
			this.selectAll.setSqlFields(sqlFields.toString());
			this.selectAll.setSqlTable(getTableName());
			sql.append(" FROM "+this.selectAll.getSqlTable());
			if(this.types==1){
				this.selectAll.setSqlWhere("system_fiid=?");
				sql.append(" WHERE "+this.selectAll.getSqlWhere());
				this.selectAll.getParams().add(this.fiid);
			}
			this.selectAll.setSql(sql.toString());
		}
		return this.selectAll;
	}



	private String buildArrayVal(String[] vals){
		if(vals==null) return "";
		String jsonStr="[";
		for(int i=0;i<vals.length;i++) jsonStr+=(i>0?",":"")+"{\"val\":\""+vals[i]+"\"}";
		return jsonStr+"]";
	}

}
