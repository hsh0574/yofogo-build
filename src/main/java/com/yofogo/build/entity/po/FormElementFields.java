package com.yofogo.build.entity.po;

import cn.com.yofogo.frame.dao.annotation.ColumnAnnotation;
import cn.com.yofogo.frame.dao.annotation.TableAnnotation;
import cn.com.yofogo.tools.util.DateTimeUtil;

/**
 * 表单数据类型信息实体类
 * @author zhengzhou.yang
 * 2016-09-19
 */
@TableAnnotation(name = "form_element_fields",isMustAnnotation=true)
public class FormElementFields implements java.io.Serializable{

	//private fields
	@ColumnAnnotation(name="fefid",primaryKey=true,isAutoIncrease=true)
	private Integer fefid;//表单数据类型主键编号，自动增长
	@ColumnAnnotation(name="fdfid")
	private Integer fdfid;//数据库字段编号
	@ColumnAnnotation(name="fiid")
	private Integer fiid;//所属表单信息
	@ColumnAnnotation(name="names")
	private String names;//名称
	@ColumnAnnotation(name="tag")
	private String tag;//标识
	@ColumnAnnotation(name="types")
	private String types;//类型
	@ColumnAnnotation(name="len")
	private Integer len;//长度
	@ColumnAnnotation(name="default_val")
	private String defaultVal;//默认值
	@ColumnAnnotation(name="is_must")
	private Integer isMust;//是否必须填写（0否；1是）
	@ColumnAnnotation(name="upload_type")
	private String uploadType;//上传类型
	@ColumnAnnotation(name="sorts")
	private Integer sorts;//显示顺序
	@ColumnAnnotation(name="mg_status")
	private Integer mgStatus;//后台处理状态（-1不显示；0列表显示；1列表显示及条件；。默认为0）
	@ColumnAnnotation(name="status")
	private Integer status;//状态（0编辑；1启用；2禁用。默认为0）
	@ColumnAnnotation(name="created",insert=true)
	private java.util.Date created;//创建时间
	@ColumnAnnotation(name="up_date",insert=true,update=true)
	private java.util.Date upDate;//最后修改时间
	//private where fields
	private String selectItems;//选择类型的选项值。json格式：类型(type:1值；2sql)；默认值(default)；数据(datas:[val,name]、sql）
	//public methods

	/**
	 * 表单数据类型主键编号，自动增长
	 * @return
	 */
	public Integer getFefid() {
		return this.fefid;
	}
	/**
	 * 表单数据类型主键编号，自动增长
	 * @param fefid
	 */
	public void setFefid(Integer fefid) {
		this.fefid = fefid;
	}
	/**
	 * 数据库字段编号
	 * @return
	 */
	public Integer getFdfid() {
		return this.fdfid;
	}
	/**
	 * 数据库字段编号
	 * @param fdfid
	 */
	public void setFdfid(Integer fdfid) {
		this.fdfid = fdfid;
	}
	/**
	 * 所属表单信息
	 * @return
	 */
	public Integer getFiid() {
		return this.fiid;
	}
	/**
	 * 所属表单信息
	 * @param fiid
	 */
	public void setFiid(Integer fiid) {
		this.fiid = fiid;
	}
	/**
	 * 名称
	 * @return
	 */
	public String getNames() {
		return this.names;
	}
	/**
	 * 名称
	 * @param names
	 */
	public void setNames(String names) {
		this.names = names;
	}
	/**
	 * 标识
	 * @return
	 */
	public String getTag() {
		return this.tag;
	}
	/**
	 * 标识
	 * @param tag
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}
	/**
	 * 类型
	 * @return
	 */
	public String getTypes() {
		return this.types;
	}
	/**
	 * 类型
	 * @param types
	 */
	public void setTypes(String types) {
		this.types = types;
	}
	/**
	 * 长度
	 * @return
	 */
	public Integer getLen() {
		return this.len;
	}
	/**
	 * 长度
	 * @param len
	 */
	public void setLen(Integer len) {
		this.len = len;
	}
	/**
	 * 默认值
	 * @return
	 */
	public String getDefaultVal() {
		return this.defaultVal;
	}
	/**
	 * 默认值
	 * @param defaultVal
	 */
	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}
	/**
	 * 是否必须填写（0否；1是）
	 * @return
	 */
	public Integer getIsMust() {
		return this.isMust;
	}
	/**
	 * 是否必须填写（0否；1是）
	 * @param isMust
	 */
	public void setIsMust(Integer isMust) {
		this.isMust = isMust;
	}
	/**
	 * 上传类型
	 * @return
	 */
	public String getUploadType() {
		return this.uploadType;
	}
	/**
	 * 上传类型
	 * @param uploadType
	 */
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}
	/**
	 * 选择类型的选项值。json格式：类型(type:1值；2sql)；默认值(default)；数据(datas:[val,name]、sql）
	 * @return
	 */
	public String getSelectItems() {
		return this.selectItems;
	}
	/**
	 * 选择类型的选项值。json格式：类型(type:1值；2sql)；默认值(default)；数据(datas:[val,name]、sql）
	 * @param selectItems
	 */
	public void setSelectItems(String selectItems) {
		this.selectItems = selectItems;
	}
	/**
	 * 显示顺序
	 * @return
	 */
	public Integer getSorts() {
		return this.sorts;
	}
	/**
	 * 显示顺序
	 * @param sorts
	 */
	public void setSorts(Integer sorts) {
		this.sorts = sorts;
	}
	/**
	 * 后台处理状态（-1不显示；0列表显示；1列表显示及条件；。默认为0）
	 * @return
	 */
	public Integer getMgStatus() {
		return this.mgStatus;
	}
	/**
	 * 后台处理状态（-1不显示；0列表显示；1列表显示及条件；。默认为0）
	 * @param mgStatus
	 */
	public void setMgStatus(Integer mgStatus) {
		this.mgStatus = mgStatus;
	}
	/**
	 * 状态（0编辑；1启用；2禁用。默认为0）
	 * @return
	 */
	public Integer getStatus() {
		return this.status;
	}
	/**
	 * 状态（0编辑；1启用；2禁用。默认为0）
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 创建时间（原始时间对象）
	 * @return 原始java.util.Date对象
	 */
	public java.util.Date getCreated_original() {
		return this.created;
	}
	/**
	 * 创建时间(字符串)
	 * @return 格式化后的字符串
	 */
	public String getCreated() {
		if(this.created==null) return null;
		else return DateTimeUtil.dateTimeToString(this.created, "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 创建时间
	 * @param created
	 */
	public void setCreated(java.util.Date created) {
		this.created = created;
	}
	/**
	 * 最后修改时间（原始时间对象）
	 * @return 原始java.util.Date对象
	 */
	public java.util.Date getUpDate_original() {
		return this.upDate;
	}
	/**
	 * 最后修改时间(字符串)
	 * @return 格式化后的字符串
	 */
	public String getUpDate() {
		if(this.upDate==null) return null;
		else return DateTimeUtil.dateTimeToString(this.upDate, "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 最后修改时间
	 * @param upDate
	 */
	public void setUpDate(java.util.Date upDate) {
		this.upDate = upDate;
	}
}