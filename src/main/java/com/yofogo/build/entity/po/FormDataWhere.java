package com.yofogo.build.entity.po;

import java.util.Date;

import cn.com.yofogo.frame.dao.annotation.ColumnAnnotation;
import cn.com.yofogo.frame.dao.annotation.TableAnnotation;

@TableAnnotation(name = "form_data_where",isMustAnnotation=true)
public class FormDataWhere {

	@ColumnAnnotation(name="fdwid",primaryKey=true,isAutoIncrease=true)
	private Integer fdwid;
	@ColumnAnnotation(name="fiid")
	private Integer fiid;
	@ColumnAnnotation(name="fdfid")
	private Integer fdfid;
	@ColumnAnnotation(name="names")
	private String names;//类型
	@ColumnAnnotation(name="types")
	private String types;//类型
	@ColumnAnnotation(name="is_vague")
	private Integer isVague=0;//是否模糊搜索（0否；1是。默认为0）
	@ColumnAnnotation(name="is_interval")
	private Integer isInterval=0;//是否有区间（0无；1有；默认为0）
	@ColumnAnnotation(name="is_must")
	private Integer isMust=0;//是否为必须条件（0否；1是。默认为0）
	@ColumnAnnotation(name="sorts")
	private int sorts=5;
	@ColumnAnnotation(name="created",insert=true)
	private Date created;
	@ColumnAnnotation(name="up_date",insert=true ,update=true)
	private Date upDate;
	public Integer getFdwid() {
		return fdwid;
	}
	public void setFdwid(Integer fdwid) {
		this.fdwid = fdwid;
	}
	public Integer getFiid() {
		return fiid;
	}
	public void setFiid(Integer fiid) {
		this.fiid = fiid;
	}
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
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
	}
	public Integer getIsVague() {
		return isVague;
	}
	public void setIsVague(Integer isVague) {
		this.isVague = isVague;
	}
	public Integer getIsInterval() {
		return isInterval;
	}
	public void setIsInterval(Integer isInterval) {
		this.isInterval = isInterval;
	}
	public Integer getIsMust() {
		return isMust;
	}
	public void setIsMust(Integer isMust) {
		this.isMust = isMust;
	}
	public Integer getSorts() {
		return sorts;
	}
	public void setSorts(Integer sorts) {
		this.sorts = sorts;
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
	public void setSorts(int sorts) {
		this.sorts = sorts;
	}


}
