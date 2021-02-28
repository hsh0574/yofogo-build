package com.yofogo.build.entity.po;

import java.util.Date;
import java.util.List;

import cn.com.yofogo.frame.dao.annotation.ColumnAnnotation;
import cn.com.yofogo.frame.dao.annotation.TableAnnotation;

@TableAnnotation(name = "form_info",isMustAnnotation=true)
public class FormInfo implements java.io.Serializable {
	@ColumnAnnotation(name="fiid",primaryKey=true,isAutoIncrease=true)
	private Integer fiid;
	@ColumnAnnotation(name="piid")
	private String piid;
	@ColumnAnnotation(name="fcid")
	private Integer fcid;
	@ColumnAnnotation(name="names")
	private String names;
	@ColumnAnnotation(name="tag")
	private String tag;
	@ColumnAnnotation(name="types")
	private Integer types;
	@ColumnAnnotation(name="template")
	private String template;
	@ColumnAnnotation(name="table_name")
	private String tableName;
	@ColumnAnnotation(name="primary_key")
	private String primaryKey;
	@ColumnAnnotation(name="primary_type")
	private String primaryType;
	@ColumnAnnotation(name="unique_keys")
	private String uniqueKeys;
	@ColumnAnnotation(name="cell_count")
	private String cellCount;
	@ColumnAnnotation(name="path")
	private String path;
	@ColumnAnnotation(name="created",insert=true)
	private Date created;
	@ColumnAnnotation(name="up_date",notNull=true,insert=true ,update=true)
	private Date upDate;

	private List<FormDbFields> dataFields;
	private List<FormDataTable> dataTables;
	private List<FormDataWhere> dataWhere;
	private List<FormElementFields> elementFields;
	public Integer getFiid() {
		return fiid;
	}
	public void setFiid(Integer fiid) {
		this.fiid = fiid;
	}
	public String getPiid() {
		return piid;
	}
	public void setPiid(String piid) {
		this.piid = piid;
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
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<FormDbFields> getDataFields() {
		return dataFields;
	}
	public void setDataFields(List<FormDbFields> dataFields) {
		this.dataFields = dataFields;
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
	public List<FormDataTable> getDataTables() {
		return dataTables;
	}
	public void setDataTables(List<FormDataTable> dataTables) {
		this.dataTables = dataTables;
	}
	public List<FormDataWhere> getDataWhere() {
		return dataWhere;
	}
	public void setDataWhere(List<FormDataWhere> dataWhere) {
		this.dataWhere = dataWhere;
	}
	public List<FormElementFields> getElementFields() {
		return elementFields;
	}
	public void setElementFields(List<FormElementFields> elementFields) {
		this.elementFields = elementFields;
	}
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getPrimaryType() {
		return primaryType;
	}
	public void setPrimaryType(String primaryType) {
		this.primaryType = primaryType;
	}
	public String getUniqueKeys() {
		return uniqueKeys;
	}
	public void setUniqueKeys(String uniqueKeys) {
		this.uniqueKeys = uniqueKeys;
	}
	public String getCellCount() {
		return cellCount;
	}
	public void setCellCount(String cellCount) {
		this.cellCount = cellCount;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}


}
