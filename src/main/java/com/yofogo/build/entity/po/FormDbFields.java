package com.yofogo.build.entity.po;

import cn.com.yofogo.frame.dao.annotation.ColumnAnnotation;
import cn.com.yofogo.frame.dao.annotation.TableAnnotation;
import cn.com.yofogo.tools.util.DateTimeUtil;

/**
 * 项目信息实体类
 * @author zhengzhou.yang
 * 2016-09-19
 */
@TableAnnotation(name = "form_db_fields",isMustAnnotation=true)
public class FormDbFields implements java.io.Serializable{

	//private fields
	@ColumnAnnotation(name="fdfid",primaryKey=true,isAutoIncrease=true)
	private Integer fdfid;//数据库字段编号，主键，自增。
	@ColumnAnnotation(name="fiid")
	private Integer fiid;//所属表单信息
	@ColumnAnnotation(name="names")
	private String names;//名称
	@ColumnAnnotation(name="data_tag")
	private String dataTag;//数据列标识
	@ColumnAnnotation(name="db_type")
	private String dbType;//数据类型
	@ColumnAnnotation(name="max_len")
	private Integer maxLen;//最大长度
	@ColumnAnnotation(name="precision_len")
	private Integer precisionLen;//精度小数位数
	@ColumnAnnotation(name="default_val")
	private String defaultVal;//默认值
	@ColumnAnnotation(name="is_must")
	private Integer isMust;//是否必须填写（0否；1是）
	@ColumnAnnotation(name="select_items")
	private String selectItems;//选择项信息
	@ColumnAnnotation(name="sorts")
	private Integer sorts;//显示顺序
	@ColumnAnnotation(name="status")
	private Integer status;//状态（0编辑；1启用；2禁用。默认为0）
	@ColumnAnnotation(name="created",insert=true)
	//@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date created;//创建时间
	@ColumnAnnotation(name="up_date",insert=true ,update=true)
	private java.util.Date upDate;//最后修改时间
	//private where fields
	//public methods
	/**
	 * 数据库字段编号，主键，自增。
	 * @return
	 */
	public Integer getFdfid() {
		return this.fdfid;
	}
	/**
	 * 数据库字段编号，主键，自增。
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
	 * 数据列标识（不可修改）
	 * @return
	 */
	public String getDataTag() {
		return this.dataTag;
	}
	/**
	 * 数据列标识（不可修改）
	 * @param dataTag
	 */
	public void setDataTag(String dataTag) {
		this.dataTag = dataTag;
	}
	/**
	 * 数据类型
	 * @return
	 */
	public String getDbType() {
		return this.dbType;
	}
	/**
	 * 数据类型
	 * @param dbType
	 */
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	/**
	 * 最大长度
	 * @return
	 */
	public Integer getMaxLen() {
		return this.maxLen;
	}
	/**
	 * 最大长度
	 * @param maxLen
	 */
	public void setMaxLen(Integer maxLen) {
		this.maxLen = maxLen;
	}
	/**
	 * 精度小数位数
	 * @return
	 */
	public Integer getPrecisionLen() {
		return this.precisionLen;
	}
	/**
	 * 精度小数位数
	 * @param precisionLen
	 */
	public void setPrecisionLen(Integer precisionLen) {
		this.precisionLen = precisionLen;
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
	public String getSelectItems() {
		return selectItems;
	}
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
		return DateTimeUtil.dateTimeToString(this.created, "yyyy-MM-dd HH:mm:ss");
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
		return DateTimeUtil.dateTimeToString(this.upDate, "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 最后修改时间
	 * @param upDate
	 */
	public void setUpDate(java.util.Date upDate) {
		this.upDate = upDate;
	}
}