package com.yofogo.build.entity.po;

import java.util.Date;

import cn.com.yofogo.frame.dao.annotation.ColumnAnnotation;
import cn.com.yofogo.frame.dao.annotation.TableAnnotation;

@TableAnnotation(name = "Form_data_table",isMustAnnotation=true)
public class FormDataTable {

	@ColumnAnnotation(name="fdtid",primaryKey=true,isAutoIncrease=true)
	private Integer fdtid;
	@ColumnAnnotation(name="fiid")
	private Integer fiid;
	@ColumnAnnotation(name="fdfid")
	private Integer fdfid;
	@ColumnAnnotation(name="names")
	private String names;
	@ColumnAnnotation(name="width")
	private Integer width=20;
	@ColumnAnnotation(name="is_checkbox")
	private int isCheckbox=0;
	@ColumnAnnotation(name="fmt_fun_name")
	private String fmtFunName;
	@ColumnAnnotation(name="css_param")
	private String cssParam;
	@ColumnAnnotation(name="sorts")
	private int sorts=5;
	@ColumnAnnotation(name="created",insert=true)
	private Date created;
	@ColumnAnnotation(name="up_date",insert=true ,update=true)
	private Date upDate;
	public Integer getFdfid() {
		return fdfid;
	}
	public void setFdfid(Integer fdfid) {
		this.fdfid = fdfid;
	}
	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public int getIsCheckbox() {
		return isCheckbox;
	}
	public void setIsCheckbox(int isCheckbox) {
		this.isCheckbox = isCheckbox;
	}
	public String getFmtFunName() {
		return fmtFunName;
	}
	public void setFmtFunName(String fmtFunName) {
		this.fmtFunName = fmtFunName;
	}
	public String getCssParam() {
		return cssParam;
	}
	public void setCssParam(String cssParam) {
		this.cssParam = cssParam;
	}
	public int getSorts() {
		return sorts;
	}
	public void setSorts(int sorts) {
		this.sorts = sorts;
	}
	public Integer getFdtid() {
		return fdtid;
	}
	public void setFdtid(Integer fdtid) {
		this.fdtid = fdtid;
	}
	public Integer getFiid() {
		return fiid;
	}
	public void setFiid(Integer fiid) {
		this.fiid = fiid;
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

}
