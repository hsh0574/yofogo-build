package com.yofogo.build.entity.po;
import cn.com.yofogo.frame.dao.annotation.ColumnAnnotation;
import cn.com.yofogo.frame.dao.annotation.TableAnnotation;
import cn.com.yofogo.tools.util.DateTimeUtil;

/**
 * 项目信息实体类
 * @author zhengzhou.yang
 * 2016-09-15
 */
@TableAnnotation(name = "pro_info",isMustAnnotation=true,isShowSql=true)
public class ProInfo implements java.io.Serializable{

	//private fields
	@ColumnAnnotation(name="piid",primaryKey=true)
	private String piid;//项目编号，主键
	@ColumnAnnotation(name="names")
	private String names;//项目名称（中文名称）
	@ColumnAnnotation(name="out_name")
	private String outName;//输出项目名称
	@ColumnAnnotation(name="out_path")
	private String outPath;//输出路径（本地）
	@ColumnAnnotation(name="base_package")
	private String basePackage;//项目包路径
	@ColumnAnnotation(name="controller_type")
	private Integer controllerType=1;//项目控制器类型（1:spring MVC Controller;2:struts2 action。默认为1）
	@ColumnAnnotation(name="project_type")
	private Integer projectType=1;//项目类型（1:maven；2普通。默认为1）
	@ColumnAnnotation(name="is_popedom")
	private Integer isPopedom=1;//是否有权限管理（0无；1有。默认为1）
	@ColumnAnnotation(name="status")
	private Integer status=1;//状态（1正常；2禁用）
	@ColumnAnnotation(name="created",notNull=true,insert=true)
	private java.util.Date created;//创建时间
	@ColumnAnnotation(name="up_date",notNull=true,update=true)
	private java.util.Date upDate;//最后修改时间
	//private where fields
	//public methods

	private String childPackage="";//项目子包路径
	private String databaseName="";//数据源名称
	private boolean isLombok=false;//是否使用Lombok


	/**
	 * 项目编号，主键
	 * @return
	 */
	public String getPiid() {
		return this.piid;
	}
	/**
	 * 项目编号，主键
	 * @param piid
	 */
	public void setPiid(String piid) {
		this.piid = piid;
	}
	/**
	 * 项目名称（中文名称）
	 * @return
	 */
	public String getNames() {
		return this.names;
	}
	/**
	 * 项目名称（中文名称）
	 * @param names
	 */
	public void setNames(String names) {
		this.names = names;
	}
	/**
	 * 输出项目名称
	 * @return
	 */
	public String getOutName() {
		return this.outName;
	}
	/**
	 * 输出项目名称
	 * @param outName
	 */
	public void setOutName(String outName) {
		this.outName = outName;
	}
	/**
	 * 输出路径（本地）
	 * @return
	 */
	public String getOutPath() {
		return this.outPath;
	}
	/**
	 * 输出路径（本地）
	 * @param outPath
	 */
	public void setOutPath(String outPath) {
		this.outPath = outPath;
	}
	/**
	 * 项目包路径
	 * @return
	 */
	public String getBasePackage() {
		return this.basePackage;
	}
	/**
	 * 项目包路径
	 * @param basePackage
	 */
	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}
	/**
	 * 项目控制器类型（1:spring MVC Controller;2:struts2 action。默认为1）
	 * @return
	 */
	public Integer getControllerType() {
		return this.controllerType;
	}
	/**
	 * 项目控制器类型（1:spring MVC Controller;2:struts2 action。默认为1）
	 * @param controllerType
	 */
	public void setControllerType(Integer controllerType) {
		this.controllerType = controllerType;
	}
	/**
	 * 项目类型（1:maven；2普通。默认为1）
	 * @return
	 */
	public Integer getProjectType() {
		return this.projectType;
	}
	/**
	 * 项目类型（1:maven；2普通。默认为1）
	 * @param projectType
	 */
	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}
	/**
	 * 是否有权限管理
	 * @return 0无；1有
	 */
	public Integer getIsPopedom() {
		return isPopedom;
	}
	/**
	 * 是否有权限管理
	 * @param isPopedom 0无；1有
	 */
	public void setIsPopedom(Integer isPopedom) {
		this.isPopedom = isPopedom;
	}
	/**
	 * 状态
	 * @return 状态（1正常；2禁用）
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 状态
	 * @param status 1正常；2禁用
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

	/**
	 * 项目子包路径
	 * @return
	 */
	public String getChildPackage() {
		return childPackage;
	}
	/**
	 * 项目子包路径
	 * @param childPackage 项目子包路径
	 */
	public void setChildPackage(String childPackage) {
		this.childPackage = childPackage;
	}
	//////////////////////////////////////////////////////////
	/**
	 * 获取java包基础路径（ 后面有"/"）
	 * @return
	 */
	public String getJavaBasePath() {
		if(this.projectType==1) return this.outPath+"/"+this.outName+"/src/main/java/"+this.basePackage.replace(".", "/")+"/";
		else return this.outPath+"/"+this.outName+"/src/"+this.basePackage.replace(".", "/")+"/";
	}

	/**
	 * 获取WebRoot根路径（ 后面有"/"）
	 * @return
	 */
	public String getWebRoot(){
		if(this.projectType==1) return this.outPath+"/"+this.outName+"/src/main/webapp/";
		else return this.outPath+"/"+this.outName+"/WebRoot/";
	}

	/**
	 * 获取配置文件根路径（ 后面有"/"）
	 * @return
	 */
	public String getResourcesPath(){
		if(this.projectType==1) return this.outPath+"/"+this.outName+"/src/main/resources/";
		else return this.outPath+"/"+this.outName+"/src/conf/";
	}


	/**
	 * 获取项目根路径（ 后面有"/"）
	 * @return
	 */
	public String getBasePath(){
		return this.outPath+"/"+this.outName+"/";
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public boolean isLombok() {
		return isLombok;
	}

	public void setLombok(boolean lombok) {
		isLombok = lombok;
	}
}