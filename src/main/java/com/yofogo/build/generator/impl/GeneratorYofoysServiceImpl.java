package com.yofogo.build.generator.impl;

import com.yofogo.build.entity.dto.*;
import com.yofogo.build.entity.enums.DtoType;
import com.yofogo.build.entity.enums.FormFieldType;
import com.yofogo.build.entity.po.ProInfo;
import com.yofogo.build.generator.BuildUtils;
import com.yofogo.build.generator.IGenerator;
import com.yofogo.build.generator.ModuleFloorInfo;
import com.yofogo.build.generator.impl.entity.EntityDtoImpl;
import com.yofogo.build.generator.impl.entity.IEntityDto;

import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneratorYofoysServiceImpl implements IGenerator {

    //private final static ModuleFloorInfo serviceModule = new ModuleFloorInfo("Service","service");
    private final static ModuleFloorInfo serviceModule = new ModuleFloorInfo("Dao","dao.das");
    private final static ModuleFloorInfo apiModule=new ModuleFloorInfo("Api","api");
    private final static ModuleFloorInfo bizModule=new ModuleFloorInfo("ApiImpl","biz.apiimpl");
    private final static ModuleFloorInfo appMgModule=new ModuleFloorInfo("Controller","mg.biz%s.ctrl");
    private final static ModuleFloorInfo appMainModule=new ModuleFloorInfo("Controller","main.biz%s.ctrl");
    //private final static ModuleFloorInfo serviceModule = new ModuleFloorInfo("Das","das");
    //private final static ModuleFloorInfo bizModule=new ModuleFloorInfo("BizImpl","biz");

    @Override
    public boolean buildEntity(ProInfo project, FormInfo formInfo) {
        IEntityDto entity = new EntityDtoImpl();
        boolean bl = entity.buildEntityPo(project,formInfo);
        if(!bl) return bl;
        return entity.buildEntityDto(project,formInfo);
    }

    @Override
    public boolean buildDas(ProInfo project, FormInfo formInfo, List<String> methods) {

        String entityClassName=BuildUtils.buildTuoFengName(formInfo.getTag(),true,project.getDelTablePrefix()),
                entityClassNamePo=entityClassName+"Po",
                entityClassNameReqDto=entityClassName+DtoType.DTO_REQUEST.getPrex(),
                entityName=BuildUtils.buildTuoFengName(formInfo.getTag(),false,project.getDelTablePrefix());
        String className=entityClassName+ serviceModule.getSuffix();
        File file=new File(project.getJavaBasePath()+project.getChildPackage().replace(".", "/")+"/"+serviceModule.getPageName().replace(".","/")+"/impl");
        try {
            if(!file.exists()) file.mkdirs();
            //接口
            StringBuilder outInter=new StringBuilder();
            //实现类
            StringBuilder outImpl=new StringBuilder();
            {
                outInter.append("package "+project.getBasePackage()+project.getChildPackage()+"."+serviceModule.getPageName()+";\n\n");
                outInter.append("import java.util.List;\n");
                outInter.append("import cn.com.yofogo.tools.Feedback;\n");
                outInter.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_RESPONSE.getPackagName()+"."+entityClassName+DtoType.DTO_RESPONSE.getPrex()+";\n");
                outInter.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_RESPONSE_LIST.getPackagName()+"."+entityClassName+DtoType.DTO_RESPONSE_LIST.getPrex()+";\n");
                outInter.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_RESPONSE_PAGE.getPackagName()+"."+entityClassName+DtoType.DTO_RESPONSE_PAGE.getPrex()+";\n");
                outInter.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_REQUEST.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST.getPrex()+";\n");
                outInter.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_REQUEST_QUERY.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST_QUERY.getPrex()+";\n");
                outInter.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_REQUEST_QUERY_LIST.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST_QUERY_LIST.getPrex()+";\n");
                outInter.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_REQUEST_QUERY_PAGE.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST_QUERY_PAGE.getPrex()+";\n");
                outInter.append("import "+project.getBasePackage()+project.getChildPackage()+BuildUtils.DAO_PACKAGE_NAME +".po."+entityClassNamePo+";\n");
                outInter.append("import com.yofoys.services.commons.base.PageRespDto;\n\n");
                outInter.append("public interface I"+className+" {\n\n");
            }
            {
                outImpl.append("package "+project.getBasePackage()+project.getChildPackage()+"."+serviceModule.getPageName()+".impl;\n\n");
                outImpl.append("import java.util.ArrayList;\n");
                outImpl.append("import java.util.List;\n");

                outImpl.append("import com.yofoys.services.commons.base.PageRespDto;\n");
                outImpl.append("import org.springframework.beans.factory.annotation.Autowired;\n");
                outImpl.append("import org.springframework.stereotype.Service;\n");
                outImpl.append("import javax.annotation.Resource;\n");
                outImpl.append("import cn.com.yofogo.frame.tools.NumberCreator;\n");
                outImpl.append("import cn.com.yofogo.frame.assistant.PageUtil;\n");
                outImpl.append("import cn.com.yofogo.frame.dao.perdure.BaseDao;\n");
                outImpl.append("import cn.com.yofogo.tools.Feedback;\n");

                outImpl.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_RESPONSE.getPackagName()+"."+entityClassName+DtoType.DTO_RESPONSE.getPrex()+";\n");
                outImpl.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_RESPONSE_LIST.getPackagName()+"."+entityClassName+DtoType.DTO_RESPONSE_LIST.getPrex()+";\n");
                outImpl.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_RESPONSE_PAGE.getPackagName()+"."+entityClassName+DtoType.DTO_RESPONSE_PAGE.getPrex()+";\n");
                outImpl.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_REQUEST.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST.getPrex()+";\n");
                outImpl.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_REQUEST_QUERY.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST_QUERY.getPrex()+";\n");
                outImpl.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_REQUEST_QUERY_LIST.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST_QUERY_LIST.getPrex()+";\n");
                outImpl.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_REQUEST_QUERY_PAGE.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST_QUERY_PAGE.getPrex()+";\n");
                outImpl.append("import "+project.getBasePackage()+project.getChildPackage()+BuildUtils.DAO_PACKAGE_NAME +".po."+entityClassNamePo+";\n");
                //import com.yofoys.services.goods.dao.po.PlateBrandPo;
                outImpl.append("import "+project.getBasePackage()+project.getChildPackage()+"."+serviceModule.getPageName()+".I"+className+";\n");
                //outImpl.append("import cn.com.yofogo.frame.dao.DBModes.ResultMode;\n");
                //if(project.getControllerType()==1)

                outImpl.append("\n");

                outImpl.append("@Service\n");
                outImpl.append("public class "+className+"Impl implements I"+className+" {\n\n");
            }
            outImpl.append("	@Resource(name = \""+project.getDatabaseName()+"\")\n");//
            outImpl.append("	private BaseDao dbHelp;\n");
            String methodHead="";
            for(String methodName : methods){
                if("add".equalsIgnoreCase(methodName)){
                    methodHead="	/**\n"
                            +"	 * 新增"+formInfo.getNames()+"\n"
                            +"	 * @param "+entityName+"Po \n"
                            +"	 * @return 0:新增成功；其他值为新增失败\n"
                            +"	 */\n";
                    //接口
                    outInter.append(methodHead);
                    outInter.append("	public Feedback add"+entityClassName+"("+entityClassNamePo+" "+entityName+"Po);\n");

                    //接口实现
                    outImpl.append(methodHead);
                    outImpl.append("	@Override\n");
                    outImpl.append("	public Feedback add"+entityClassName+"("+entityClassNamePo+" "+entityName+"Po) {\n");

                    if(formInfo.getPrimaryType()==2){

                        outImpl.append("		"+entityName+"Po.set"+BuildUtils.buildTuoFengName(BuildUtils.getEntityIdNanme(formInfo),true)+"(NumberCreator.getNumber(dbHelp, \""+formInfo.getTableName()+"\"));\n");
                    }
                    outImpl.append("		boolean reslut=false;\n");
                    outImpl.append("		try {\n");
                    outImpl.append("			reslut = dbHelp.save("+entityName+"Po);\n");
                    outImpl.append("		} catch (Exception e) {\n");
                    outImpl.append("			e.printStackTrace();\n");
                    outImpl.append("		}\n");
                    outImpl.append("		return new Feedback(reslut?0:1,reslut?\"保存成功\":\"保存失败\");\n");
                    outImpl.append("	}\n");

                }else if("update".equalsIgnoreCase(methodName)){
                    methodHead="	/**\n"
                            +"	 * 修改"+formInfo.getNames()+"\n"
                            +"	 * @param "+entityName+"Po \n"
                            +"	 * @return 0:修改成功；其他值为修改失败\n"
                            +"	 */\n";
                    {
                        outInter.append(methodHead);
                        outInter.append("	public Feedback update"+entityClassName+"("+entityClassNamePo+" "+entityName+"Po);\n");
                    }
                    {
                        outImpl.append(methodHead);
                        outImpl.append("	@Override\n");
                        outImpl.append("	public Feedback update"+entityClassName+"("+entityClassNamePo+" "+entityName+"Po) {\n");
                        outImpl.append("		boolean reslut=false;\n");
                        outImpl.append("		try {\n");
                        outImpl.append("			reslut = dbHelp.update("+entityName+"Po);\n");
                        outImpl.append("		} catch (Exception e) {\n");
                        outImpl.append("			e.printStackTrace();\n");
                        outImpl.append("		}\n");
                        outImpl.append("		return new Feedback(reslut?0:1,reslut?\"保存成功\":\"保存失败\");\n");
                        outImpl.append("	}\n");
                    }
                }

            }
            outInter.append("\n");
            outImpl.append("\n");
            {//根据编号获取对象
                methodHead="	/**\n"
                        +"	 * 根据主键编号获取 "+formInfo.getNames()+"\n"
                        +"	 * @param id \n"
                        +"	 * @return \n"
                        +"	 */\n";
                outInter.append(methodHead);
                outInter.append("	public "+entityClassName+DtoType.DTO_RESPONSE.getPrex()+" get"+entityClassName+"ById(Long id);\n");

                SqlHandler sh=formInfo.getSelectSqlHandler("");
                outImpl.append(methodHead);
                outImpl.append("	@Override\n");
                outImpl.append("	public "+entityClassName+DtoType.DTO_RESPONSE.getPrex()+" get"+entityClassName+"ById(Long id) {\n");
                outImpl.append("		String sql=\""+sh.getSql()+"\";\n");
                /*outImpl.append("		"+entityClassName+DtoType.DTO_RESPONSE.getPrex()+" "+entityName+"Dto = new "+entityClassName+DtoType.DTO_RESPONSE.getPrex()+"();\n");
                outImpl.append("		"+entityClassNamePo+" "+entityName+" = dbHelp.queryObject("+entityClassNamePo+".class, sql,"+(formInfo.getTypes()==1?formInfo.getFiid()+",":"")+"id);\n");
                outImpl.append("		"+entityName+"Dto.set"+entityClassNamePo+"("+entityName+"); \n");*/

                outImpl.append("		"+entityClassName+DtoType.DTO_RESPONSE.getPrex()+" "+entityName+"Dto = dbHelp.queryObject("+entityClassName+DtoType.DTO_RESPONSE.getPrex()+".class, sql,"+(formInfo.getTypes()==1?formInfo.getFiid()+",":"")+"id);\n");
                outImpl.append("		return "+entityName+"Dto;\n");
                outImpl.append("	}\n");

            }
            outInter.append("\n");
            outImpl.append("\n");
            {//List查询


                String querHandleName=entityName+DtoType.DTO_REQUEST_QUERY.getPrex();
                String ListData="List<"+entityClassName+DtoType.DTO_RESPONSE_LIST.getPrex()+">";
                methodHead="	/**\n"
                        +"	 * 根据条件获取 "+formInfo.getNames()+" 数据集合\n"
                        +"	 * @param "+querHandleName+"\n"
                        +"	 * @return \n"
                        +"	 */\n";
                outInter.append(methodHead);
                outInter.append("	public Feedback<"+ListData+"> queryList("+entityClassName+DtoType.DTO_REQUEST_QUERY_LIST.getPrex()+" "+querHandleName+");\n");


                SqlHandler sh=formInfo.getSqlSelectAll();
                outImpl.append(methodHead);
                outImpl.append("	@Override\n");
                outImpl.append("	public Feedback<"+ListData+"> queryList("+entityClassName+DtoType.DTO_REQUEST_QUERY_LIST.getPrex()+" "+querHandleName+") {\n");
                FormElementField field;

                //查询条件处理
                StringBuilder sqlWheres=new StringBuilder();
                String methodName;
                String getPrex="get";
                if(project.isLombok()) getPrex="";
                boolean isBetweenTime=false;
                for(FormDataWhere where : formInfo.getFormDataWheres()){
                    field = formInfo.getFormElementField(where.getFdfid());
                    if(field==null) continue;
                    if(field.getFefid()==null) methodName=BuildUtils.buildTuoFengName(field.getDataTag(),true);
                    else methodName=BuildUtils.buildTuoFengName(field.getTag(),true);
                    if(where.getIsInterval()==1){
                        isBetweenTime=true;
                        sqlWheres.append("		if("+querHandleName+"."+getPrex+methodName+"Begin()!=null && "+querHandleName+"."+getPrex+methodName+"End()!=null){\n");
                        sqlWheres.append("			sql.append(\" AND "+field.getDataTag()+" BETWEEN ? AND ?\");\n");
                        sqlWheres.append("			params.add("+querHandleName+"."+getPrex+methodName+"Begin());\n");
                        sqlWheres.append("			params.add("+querHandleName+"."+getPrex+methodName+"End());\n");
                        sqlWheres.append("		} else if("+querHandleName+"."+getPrex+methodName+"Begin()!=null) {\n");
                        sqlWheres.append("			sql.append(\" AND "+field.getDataTag()+" >= ?\");\n");
                        sqlWheres.append("			params.add("+querHandleName+"."+getPrex+methodName+"Begin());\n");
                        sqlWheres.append("		} else  if("+querHandleName+"."+getPrex+methodName+"End()!=null) {\n");
                        sqlWheres.append("			sql.append(\" AND "+field.getDataTag()+" <= ?\");\n");
                        sqlWheres.append("			params.add("+querHandleName+"."+getPrex+methodName+"End());\n");
                        sqlWheres.append("		} \n");
                    }else {
                        sqlWheres.append("		if("+querHandleName+"."+getPrex+methodName+"()!=null){\n");
                        if(where.getIsVague()==1){
                            sqlWheres.append("			sql.append(\" AND "+field.getDataTag()+" LIKE ?\");\n");
                            sqlWheres.append("			params.add(\"%\"+"+querHandleName+"."+getPrex+methodName+"()+\"%\");\n");
                        }else {
                            sqlWheres.append("			sql.append(\" AND "+field.getDataTag()+"=?\");\n");
                            sqlWheres.append("			params.add("+querHandleName+"."+getPrex+methodName+"());\n");
                        }
                        sqlWheres.append("		}\n");
                    }
                }

                outImpl.append("		List<Object> params = new ArrayList<Object>();\n");
                outImpl.append("		StringBuffer sql= new StringBuffer();\n");
                outImpl.append("		sql.append(\""+sh.getSql()+"\");\n");
                if(formInfo.getTypes()==1) {
                    outImpl.append("		params.add("+formInfo.getFiid()+");\n");
                } else {
                    // if(formInfo.getFormDataWheres().size()>0)
                    //outImpl.append("		sql.append(\" WHERE 1=1\");\n");
                    outImpl.append("		sql.append(\" WHERE is_flat=?\");\n");
                    outImpl.append("		params.add("+querHandleName+"."+getPrex+"IsFlat());\n");
                }
                {//SASS
                    outImpl.append("		if(" + querHandleName + "." + getPrex + "TenantId()!=null) {\n");
                    outImpl.append("			sql.append(\" AND tenant_id=?\");\n");
                    outImpl.append("			params.add(" + querHandleName + "." + getPrex + "TenantId());\n");
                    outImpl.append("		}\n");
                    outImpl.append("		if(" + querHandleName + "." + getPrex + "InstanceId()!=null) {\n");
                    outImpl.append("			sql.append(\" AND instance_id=?\");\n");
                    outImpl.append("			params.add(" + querHandleName + "." + getPrex + "InstanceId());\n");
                    outImpl.append("		}\n");
                }

                if(!isBetweenTime){
                    methodName="CreateTime";
                    String fieldName="create_time";
                    sqlWheres.append("		if("+querHandleName+"."+getPrex+methodName+"Begin()!=null && "+querHandleName+"."+getPrex+methodName+"End()!=null){\n");
                    sqlWheres.append("			sql.append(\" AND "+fieldName+" BETWEEN ? AND ?\");\n");
                    sqlWheres.append("			params.add("+querHandleName+"."+getPrex+methodName+"Begin());\n");
                    sqlWheres.append("			params.add("+querHandleName+"."+getPrex+methodName+"End());\n");
                    sqlWheres.append("		} else if("+querHandleName+"."+getPrex+methodName+"Begin()!=null) {\n");
                    sqlWheres.append("			sql.append(\" AND "+fieldName+" >= ?\");\n");
                    sqlWheres.append("			params.add("+querHandleName+"."+getPrex+methodName+"Begin());\n");
                    sqlWheres.append("		} else  if("+querHandleName+"."+getPrex+methodName+"End()!=null) {\n");
                    sqlWheres.append("			sql.append(\" AND "+fieldName+" <= ?\");\n");
                    sqlWheres.append("			params.add("+querHandleName+"."+getPrex+methodName+"End());\n");
                    sqlWheres.append("		} \n");
                }
                outImpl.append(sqlWheres);
                outImpl.append("		"+ListData+" list = dbHelp.queryEntity("+entityClassName+DtoType.DTO_RESPONSE_LIST.getPrex()+".class, sql.toString(), params.toArray());\n");
                outImpl.append("		if(list!=null) {\n");
                outImpl.append("			return new Feedback(list);\n");
                outImpl.append("		} else {\n");
                outImpl.append("			return new Feedback(4,\"获取"+formInfo.getNames()+"数据列表失败\");\n");
                outImpl.append("		}\n");
                outImpl.append("	}\n");

            }


            outInter.append("\n");
            outImpl.append("\n");
            {//分页条件查询

                String querHandleName=entityName+DtoType.DTO_REQUEST_QUERY.getPrex();
                String pageUtil="PageUtil<"+entityClassName+DtoType.DTO_RESPONSE_PAGE.getPrex()+","+entityClassName+DtoType.DTO_REQUEST_QUERY_PAGE.getPrex()+">";
                methodHead="	/**\n"
                        +"	 * 根据条件分页获取 "+formInfo.getNames()+"\n"
                        +"	 * @param "+querHandleName+"\n"
                        +"	 * @return \n"
                        +"	 */\n";
                outInter.append(methodHead);
                outInter.append("	public Feedback<PageRespDto<"+entityClassName+DtoType.DTO_RESPONSE_PAGE.getPrex()+">> queryPage("+entityClassName+DtoType.DTO_REQUEST_QUERY_PAGE.getPrex()+" "+querHandleName+");\n");

                SqlHandler sh=formInfo.getSqlSelectAll();
                outImpl.append(methodHead);
                outImpl.append("	@Override\n");
                outImpl.append("	public Feedback<PageRespDto<"+entityClassName+DtoType.DTO_RESPONSE_PAGE.getPrex()+">> queryPage("+entityClassName+DtoType.DTO_REQUEST_QUERY_PAGE.getPrex()+" "+querHandleName+") {\n");

                FormElementField field;
                //选择数据从数据库获取时，列表查询需要进行子查询的处理
                StringBuilder sqlSelectItems=new StringBuilder();
                for(FormDataTable dt : formInfo.getFormDataTables()){
                    field = formInfo.getFormElementField(dt.getFdfid());
                    if(field==null || field.getFefid()==null) continue;
                    SelectItems selectItem=field.getSelectItem();
                    if(selectItem!=null && selectItem.getType()==2){
                        String sqlField="			+\",(SELECT ";
                        boolean isSuccess=true;;
                        //select fcid AS val ,names AS name from form_category where
                        String tempSql=selectItem.getSql();
                        //name的解析
                        Matcher m= Pattern.compile("(\\ |,)(((\\w+\\ +(AS|as|As|aS)\\ +)?(name|NAME))(\\ +|,))").matcher(tempSql);
                        if(isSuccess=m.find()){
                            sqlField+=m.group(3);
                        }
                        //表名的解析
                        m=Pattern.compile("\\ +((from|FORM)\\ +\\w+)\\ *").matcher(tempSql);
                        if(isSuccess=m.find()){
                            sqlField+=" "+m.group(1);
                        }
                        //val，即编号的解析，作为条件
                        m=Pattern.compile("(\\ |,)((((\\w+)\\ +(AS|as|As|aS)\\ +)?(val|VAL))(\\ +|,))").matcher(tempSql);
                        if(isSuccess=m.find()){
                            String tempS=m.group(3).toUpperCase();
                            if(tempS.indexOf(" AS ")>0) tempS=m.group(5);
                            sqlField+=" WHERE "+tempS+"="+sh.getSqlTable()+"."+field.getDataTag();
                        }
                        if(isSuccess){
                            sqlSelectItems.append(sqlField+") AS "+BuildUtils.buildTuoFengName(field.getTag(),false)+"ToOfItemName\"\n");
                        }
                    }
                }
                //查询条件处理
                StringBuilder sqlWheres=new StringBuilder();
                String methodName;
                boolean isBetweenTime=false;
                String getPrex="get";
                if(project.isLombok()) getPrex="";
                for(FormDataWhere where : formInfo.getFormDataWheres()){
                    field = formInfo.getFormElementField(where.getFdfid());
                    if(field==null) continue;
                    if(field.getFefid()==null) methodName=BuildUtils.buildTuoFengName(field.getDataTag(),true);
                    else methodName=BuildUtils.buildTuoFengName(field.getTag(),true);
                    if(where.getIsInterval()==1){
                        isBetweenTime=true;
                        sqlWheres.append("		if("+querHandleName+"."+getPrex+methodName+"Begin()!=null && "+querHandleName+"."+getPrex+methodName+"End()!=null){\n");
                        sqlWheres.append("			sql.append(\" AND "+field.getDataTag()+" BETWEEN ? AND ?\");\n");
                        sqlWheres.append("			params.add("+querHandleName+"."+getPrex+methodName+"Begin());\n");
                        sqlWheres.append("			params.add("+querHandleName+"."+getPrex+methodName+"End());\n");
                        sqlWheres.append("		} else if("+querHandleName+"."+getPrex+methodName+"Begin()!=null) {\n");
                        sqlWheres.append("			sql.append(\" AND "+field.getDataTag()+" >= ?\");\n");
                        sqlWheres.append("			params.add("+querHandleName+"."+getPrex+methodName+"Begin());\n");
                        sqlWheres.append("		} else  if("+querHandleName+"."+getPrex+methodName+"End()!=null) {\n");
                        sqlWheres.append("			sql.append(\" AND "+field.getDataTag()+" <= ?\");\n");
                        sqlWheres.append("			params.add("+querHandleName+"."+getPrex+methodName+"End());\n");
                        sqlWheres.append("		} \n");
                    }else {
                        sqlWheres.append("		if("+querHandleName+"."+getPrex+methodName+"()!=null){\n");
                        if(where.getIsVague()==1){
                            sqlWheres.append("			sql.append(\" AND "+field.getDataTag()+" LIKE ?\");\n");
                            sqlWheres.append("			params.add(\"%\"+"+querHandleName+"."+getPrex+methodName+"()+\"%\");\n");
                        }else {
                            sqlWheres.append("			sql.append(\" AND "+field.getDataTag()+"=?\");\n");
                            sqlWheres.append("			params.add("+entityName+"."+getPrex+methodName+"());\n");
                        }
                        sqlWheres.append("		}\n");
                    }
                }
                outImpl.append("		StringBuffer sql= new StringBuffer();\n");
                if(sqlSelectItems.length()>0) {
                    outImpl.append("		sql.append(\"SELECT "+sh.getSqlFields()+"\")\n");
                    outImpl.append(sqlSelectItems);
                    outImpl.append("			.append(\" FROM "+sh.getSqlTable()+("".equals(sh.getSqlWhere())?"":" WHERE "+sh.getSqlWhere())+"\");\n");
                } else {
                    outImpl.append("		sql.append(\""+sh.getSql()+"\");\n");
                }
                outImpl.append("		List<Object> params = new ArrayList<Object>();\n");
                if(formInfo.getTypes()==1) {
                    outImpl.append("		params.add("+formInfo.getFiid()+");\n");
                } else {//if(formInfo.getFormDataWheres().size()>0)
                    //outImpl.append("		sql.append(\" WHERE 1=1\");\n");
                    outImpl.append("		sql.append(\" WHERE is_flat=?\");\n");
                    outImpl.append("		params.add("+querHandleName+"."+getPrex+"IsFlat"+"());\n");
                }
                {//SASS
                    outImpl.append("		if(" + querHandleName + "." + getPrex + "TenantId()!=null) {\n");
                    outImpl.append("			sql.append(\" AND tenant_id=?\");\n");
                    outImpl.append("			params.add(" + querHandleName + "." + getPrex + "TenantId());\n");
                    outImpl.append("		}\n");
                    outImpl.append("		if(" + querHandleName + "." + getPrex + "InstanceId()!=null) {\n");
                    outImpl.append("			sql.append(\" AND instance_id=?\");\n");
                    outImpl.append("			params.add(" + querHandleName + "." + getPrex + "InstanceId());\n");
                    outImpl.append("		}\n");
                }
                if(!isBetweenTime){
                    methodName="CreateTime";
                    String fieldName="create_time";
                    sqlWheres.append("		if("+querHandleName+"."+getPrex+methodName+"Begin()!=null && "+querHandleName+"."+getPrex+methodName+"End()!=null){\n");
                    sqlWheres.append("			sql.append(\" AND "+fieldName+" BETWEEN ? AND ?\");\n");
                    sqlWheres.append("			params.add("+querHandleName+"."+getPrex+methodName+"Begin());\n");
                    sqlWheres.append("			params.add("+querHandleName+"."+getPrex+methodName+"End());\n");
                    sqlWheres.append("		} else if("+querHandleName+"."+getPrex+methodName+"Begin()!=null) {\n");
                    sqlWheres.append("			sql.append(\" AND "+fieldName+" >= ?\");\n");
                    sqlWheres.append("			params.add("+querHandleName+"."+getPrex+methodName+"Begin());\n");
                    sqlWheres.append("		} else  if("+querHandleName+"."+getPrex+methodName+"End()!=null) {\n");
                    sqlWheres.append("			sql.append(\" AND "+fieldName+" <= ?\");\n");
                    sqlWheres.append("			params.add("+querHandleName+"."+getPrex+methodName+"End());\n");
                    sqlWheres.append("		} \n");
                }
                outImpl.append(sqlWheres);

                outImpl.append("\n");
                outImpl.append("		"+pageUtil+" page = new PageUtil<>();\n");
                outImpl.append("		page.setPageNo("+querHandleName+".getPageQueryNo());\n");
                outImpl.append("		page.setPageSize("+querHandleName+".getPageQuerySize());\n");
                outImpl.append("\n");
                outImpl.append("		dbHelp.queryForPage("+entityClassName+DtoType.DTO_RESPONSE_PAGE.getPrex()+".class, page, sql.toString(), params.toArray());\n");
                outImpl.append("\n");
                outImpl.append("		PageRespDto<"+entityClassName+DtoType.DTO_RESPONSE_PAGE.getPrex()+"> result= new PageRespDto<>();\n");
                outImpl.append("		result.setPageNo(page.getPageNo());\n");
                outImpl.append("		result.setPageSize(page.getPageSize());\n");
                outImpl.append("		result.setRows(page.getList());\n");
                outImpl.append("		result.setTotal(page.getRecTotal());\n");
                outImpl.append("		page = null;\n");
                outImpl.append("		return new Feedback<>(result);\n");
                outImpl.append("	}\n");

            }
            outInter.append("\n");
            outImpl.append("\n");
            if(1==2){//有需要从数据库获取数据的下拉框对应方法
                for(FormElementField field : formInfo.getEleFields()){
                    FormFieldType fieldType=null;
                    try {
                        fieldType=FormFieldType.valueOf(field.getTypes());
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    if(FormFieldType.SELECT==fieldType || FormFieldType.CHECKBOX==fieldType || FormFieldType.RADIO==fieldType){
                        SelectItems selectItem=field.getSelectItem();
                        if(selectItem==null || selectItem.getType()!=2) continue;
                        String methodName=BuildUtils.buildTuoFengName(field.getTag(),true),paramNames="",params="",paramdescs="";
                        if(selectItem.getParams().size()>0){
                            for(String key : selectItem.getParamNames()){
                                if(params.length()>0){
                                    params+=",String "+key;
                                }else {
                                    params="String "+key;
                                }
                                paramNames+=","+key;
                                paramdescs+="	 * @param "+key+" \n";
                            }
                        }
                        methodHead="	/**\n"
                                +"	 * 获取 "+field.getNames()+" 的选项信息\n"
                                + paramdescs
                                +"	 * @return \n"
                                +"	 */\n";
                        outInter.append(methodHead);
                        outInter.append("	public List get"+methodName+"SelectItems("+params+");\n");

                        SqlHandler sh=formInfo.getSqlSelectAll();
                        outImpl.append(methodHead);
                        outImpl.append("	public List get"+methodName+"SelectItems("+params+") {\n");
                        outImpl.append("		String sql=\""+selectItem.getSql()+"\";\n");
                        outImpl.append("		return dbHelp.query(sql, ResultMode.isMap"+paramNames+");\n");
                        outImpl.append("	}\n");
                    }
                }
            }

            outInter.append("}");
            outImpl.append("}");
            //接口
            BufferedWriter InterWrite= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(project.getJavaBasePath()+project.getChildPackage().replace(".", "/")+"/"+serviceModule.getPageName().replace(".","/")+"/I"+className+".java"),BuildUtils.FILE_CHARSETNAME));
            InterWrite.write(outInter.toString());
            InterWrite.flush();
            InterWrite.close();
            //实现类
            BufferedWriter implWrite= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(project.getJavaBasePath()+project.getChildPackage().replace(".", "/")+"/"+serviceModule.getPageName().replace(".","/")+"/impl/"+className+"Impl.java"),BuildUtils.FILE_CHARSETNAME));
            implWrite.write(outImpl.toString());
            implWrite.flush();
            implWrite.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean buildBizAndImpl(ProInfo project,FormInfo formInfo) {
        boolean interfaceRet=buildInterface(project, formInfo);
        if(!interfaceRet) return false;

        String entityClassName=BuildUtils.buildTuoFengName(formInfo.getTag(),true,project.getDelTablePrefix()),
                entityName=BuildUtils.buildTuoFengName(formInfo.getTag(),false,project.getDelTablePrefix()),
                entityClassNameReqDto=entityClassName+DtoType.DTO_REQUEST.getPrex(),
                entityClassNamePo = entityClassName+"Po",
                entityClassNameRespDto=entityClassName+DtoType.DTO_RESPONSE.getPrex();
        String dasInterface="I"+entityClassName+ serviceModule.getSuffix(),
                dasName=entityName+ serviceModule.getSuffix();
        String className=entityClassName+bizModule.getSuffix();
        String dtoNameSuffix="Dto";

        File file=new File(project.getJavaBasePath()+project.getChildPackage().replace(".", "/")+"/"+bizModule.getPageName().replace(".","/"));
        try {
            if(!file.exists()) file.mkdirs();
            StringBuilder outStr=new StringBuilder();
            {
                outStr.append("package "+project.getBasePackage()+project.getChildPackage()+"."+bizModule.getPageName()+";\n");
                outStr.append("\n");
                //outStr.append("import javax.annotation.Resource;\n");
                outStr.append("import com.yofoys.services.commons.base.PageRespDto;\n");
                outStr.append("import org.springframework.beans.factory.annotation.Autowired;\n");
                outStr.append("import org.springframework.stereotype.Service;\n");
                outStr.append("import org.springframework.web.bind.annotation.*;\n");
                outStr.append("import org.springframework.beans.BeanUtils;\n");
                outStr.append("\n");
                outStr.append("import "+project.getBasePackage()+project.getChildPackage()+"."+apiModule.getPageName()+".I"+entityClassName+apiModule.getSuffix()+";\n");
                //if(isDto){
                    outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_RESPONSE.getPackagName()+"."+entityClassName+DtoType.DTO_RESPONSE.getPrex()+";\n");
                    outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_RESPONSE_LIST.getPackagName()+"."+entityClassName+DtoType.DTO_RESPONSE_LIST.getPrex()+";\n");
                    outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_RESPONSE_PAGE.getPackagName()+"."+entityClassName+DtoType.DTO_RESPONSE_PAGE.getPrex()+";\n");
                    outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_REQUEST.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST.getPrex()+";\n");
                    outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_REQUEST_QUERY.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST_QUERY.getPrex()+";\n");
                    outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_REQUEST_QUERY_LIST.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST_QUERY_LIST.getPrex()+";\n");
                    outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_REQUEST_QUERY_PAGE.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST_QUERY_PAGE.getPrex()+";\n");
                    outStr.append("import "+project.getBasePackage()+project.getChildPackage()+BuildUtils.DAO_PACKAGE_NAME +".po."+entityClassNamePo+";\n");
               // }
                outStr.append("import "+project.getBasePackage()+project.getChildPackage()+"."+serviceModule.getPageName()+".I"+entityClassName+ serviceModule.getSuffix() +";\n");
                outStr.append("import cn.com.yofogo.frame.assistant.PageUtil;\n");
                outStr.append("import cn.com.yofogo.tools.Feedback;\n");
                outStr.append("\n");
                outStr.append("import java.util.List;\n");
                outStr.append("\n");
                //outStr.append("@Service(\""+entityName+apiModule.getSuffix()+"\")\n");
                outStr.append("@RestController\n");
                outStr.append("@RequestMapping()\n");

                outStr.append("public class "+className+" implements "+getInterfaceName(entityClassName)+"{\n\n");// extends BaseController
            }

            String querHandleName=entityName+DtoType.DTO_REQUEST_QUERY.getPrex();

            outStr.append("\n");
            outStr.append("	@Autowired\n");
            outStr.append("	private "+dasInterface+" "+dasName+";\n");
            outStr.append("\n");
            {//数据列表页面
                String listData="List<"+entityClassName+DtoType.DTO_RESPONSE_LIST.getPrex()+">";
                outStr.append("	@Override\n");
                outStr.append("	public Feedback<"+listData+"> queryList("+entityClassName+DtoType.DTO_REQUEST_QUERY_LIST.getPrex()+" "+querHandleName+"){\n");//,"+entityClassName+dtoNameSuffix+" "+entityName+"
                outStr.append("		return "+dasName+".queryList("+querHandleName+");\n");//,"+entityName+"
                outStr.append("	}\n\n");
            }
            {//数据page列表页面

                String pageUtil="PageUtil<"+entityClassName+DtoType.DTO_RESPONSE_PAGE.getPrex()+","+entityClassName+DtoType.DTO_REQUEST_QUERY_PAGE.getPrex()+">";
                outStr.append("	@Override\n");
                outStr.append("	public Feedback<PageRespDto<"+entityClassName+DtoType.DTO_RESPONSE_PAGE.getPrex()+">> queryPage("+entityClassName+DtoType.DTO_REQUEST_QUERY_PAGE.getPrex()+" "+querHandleName+"){\n");//,"+entityClassName+dtoNameSuffix+" "+entityName+"
                //outStr.append("		page.setPage(true);\n");
                outStr.append("		return "+dasName+".queryPage("+querHandleName+");\n");//,"+entityName+"
                //outStr.append("		return new Feedback<"+pageUtil+">(page);\n");//,"+entityName+"
				/*outStr.append("	public Map<String,Object> page(@RequestBody PageUtil page,"+entityClassName+dtoNameSuffix+" "+entityName+"){\n");
				outStr.append("		page.setPage(true);\n");
				outStr.append("		"+servicName+".queryList(page,"+entityName+");\n");
				outStr.append("		Map<String,Object> map=new HashMap<String,Object>();\n");
				outStr.append("		map.put(\"total\", page.getRecTotal());\n");
				outStr.append("		map.put(\"rows\", page.getList());\n");
				outStr.append("		return map;\n");*/
                outStr.append("	}\n\n");
            }
            String primaryKey=(formInfo.getTypes()==1?"id":formInfo.getPrimaryKeyToEntityName());
            {//根据主键获取数据
//                outStr.append("	/**\n"
//                        +"	 * 根据主键获取"+formInfo.getNames()+"数据\n"
//                        +"	 * @param "+primaryKey+"\n"
//                        +"	 * @return 返回json\n"
//                        +"	 */\n");
//                outStr.append("	@GetMapping(value = { \"/find/{"+primaryKey+"}\", \"\" })\n");
                //outStr.append("	@ResponseBody\n");
                String entityByIdRespDto=entityClassName+DtoType.DTO_RESPONSE.getPrex();
                outStr.append("	@Override\n");
                outStr.append("	public Feedback<"+entityByIdRespDto+"> get"+entityClassName+"ById(Long "+primaryKey+"){\n");
                outStr.append("		"+entityByIdRespDto+" "+entityName+" = "+dasName+".get"+entityClassName+"ById("+primaryKey+");\n");
                outStr.append("		if("+entityName+" == null) {\n");
                outStr.append("		    return new Feedback<"+entityByIdRespDto+">(4,\"没有获取到"+formInfo.getNames()+"\");\n");
                outStr.append("		}\n");
                outStr.append("		return new Feedback<"+entityByIdRespDto+">("+entityName+");\n");
                outStr.append("	}\n");
			/*	outStr.append("	public "+entityClassNameDto+" get"+entityClassName+"ById(@PathVariable(\""+primaryKey+"\") Long "+primaryKey+"){\n");
				outStr.append("		"+entityClassNameDto+" "+entityName+"="+servicName+".find"+entityClassName+"("+primaryKey+");\n");
				outStr.append("		return "+entityName+";\n");
				outStr.append("	}\n");*/

            }
            {//添加编辑
                /*****************转到添加、编辑页面***********************/
//				outStr.append("	/**\n"
//						+"	 * 转到"+formInfo.getNames()+"添加、编辑管理页面\n"
//						+"	 * @param request\n"
//						+"	 * @param "+primaryKey+"\n"
//						+"	 * @return \n"
//						+"	 */\n");
				/*outStr.append("	@RequestMapping(value = { \"editPage\", \"\" }, method = RequestMethod.GET)\n");
				outStr.append("	public String editPage(HttpServletRequest request,String "+primaryKey+"){\n");
				outStr.append("		request.setAttribute(\""+primaryKey+"\","+primaryKey+");\n");
				outStr.append("		return \"manager/"+formInfo.getPath()+"/"+entityName+"_editPage\";\n");
				outStr.append("	}\n");
				outStr.append("\n");*/
                /*****************保存数据***********************/
                outStr.append("	@Override\n");
                outStr.append("	public Feedback<"+entityClassNameRespDto+"> add"+entityClassName+"("+entityClassNameReqDto+" "+entityName+"){\n");
                outStr.append("		"+entityClassNamePo+" "+entityName+"Po = new "+entityClassNamePo+"();\n");
                outStr.append("		BeanUtils.copyProperties("+entityName+", "+entityName+"Po);\n");
                outStr.append("		Feedback<"+entityClassNameRespDto+"> fb = " + dasName + ".add" + entityClassName + "(" + entityName +"Po);\n");
                //outStr.append("		return new Feedback<"+entityClassNameRespDto+">(fb.getCode(),fb.getMessage());\n");
                outStr.append("		return fb;\n");

                outStr.append("	}\n");
                outStr.append("\n");
                outStr.append("	@Override\n");
                outStr.append("	public Feedback<"+entityClassNameRespDto+"> update"+entityClassName+"("+entityClassNameReqDto+" "+entityName+"){\n");
                outStr.append("		"+entityClassNamePo+" "+entityName+"Po = new "+entityClassNamePo+"();\n");
                outStr.append("		BeanUtils.copyProperties("+entityName+", "+entityName+"Po);\n");
                outStr.append("		Feedback<"+entityClassNameRespDto+"> fb = " + dasName + ".update" + entityClassName + "(" + entityName + "Po);\n");
                //outStr.append("		return new Feedback<"+entityClassNameRespDto+">(fb.getCode(),fb.getMessage());\n");
                outStr.append("		return fb;\n");

                outStr.append("	}\n");
            }
            boolean isFile=false;
//			{//通过数据库获取数据的下拉框
//				for(FormElementField field : formInfo.getEleFields()){
//					FormFieldType fieldType=FormFieldType.valueOf(field.getTypes());
//					if(FormFieldType.SELECT==fieldType || FormFieldType.CHECKBOX==fieldType || FormFieldType.RADIO==fieldType){
//						SelectItems selectItem=field.getSelectItem();
//						if(selectItem==null || selectItem.getType()!=2) continue;
//						String methodName=BuildUtils.buildTuoFengName(field.getTag(),true),paramNames="",params="",paramdescs="";
//						if(selectItem.getParams().size()>0){
//							for(String key : selectItem.getParamNames()){
//								if(params.length()>0){
//									params+=",String "+key;
//									paramNames+=","+key;
//								}else {
//									params="String "+key;
//									paramNames=key;
//								}
//								paramdescs+="	 * @param "+key+" \n";
//							}
//						}
//						outStr.append("	/**\n"
//								+"	 * 获取 "+field.getNames()+" 的选项数据\n"
//								+ paramdescs
//								+"	 * @return json数据\n"
//								+"	 */\n");
//						outStr.append("	@RequestMapping(value = { \"get"+methodName+"SelectItems\", \"\" }, method = RequestMethod.POST)\n");
//						outStr.append("	@ResponseBody\n");
//						outStr.append("	public Feedback<List> get"+methodName+"SelectItems("+params+") {\n");
//						outStr.append("		return new Feedback("+servicName+".get"+methodName+"SelectItems("+paramNames+"));\n");
//						outStr.append("	}\n");
//					} else if(FormFieldType.IMAGE==fieldType || FormFieldType.FILE==fieldType) isFile=true;
//				}
//			}
            if(isFile){
                outStr.append("	/**\n"
                        +"	 * 保存文件数据\n"
                        +"	 * @param request\n"
                        +"	 * @param number 上传配置文件编号。如果为null，则默认使用0号配置文件上传\n"
                        +"	 * @return json数据\n"
                        +"	 */\n");
                outStr.append("	@RequestMapping(value = { \"upFile\", \"\" }, method = RequestMethod.POST)\n");
                outStr.append("	@ResponseBody\n");
                outStr.append("	public Feedback upFile(HttpServletRequest request,Integer number) {\n");
                outStr.append("		Map<String,Object> ret=new HashMap<String,Object>();\n");
                outStr.append("		if(number==null) number=0;\n");
                outStr.append("		IState state=new FileHelp(number).saveFile(request, \""+entityName+"\");\n");
                outStr.append("		if(state.isSuccess()){\n");
                outStr.append("			ret.put(\"status\", 0);\n");
                outStr.append("			ret.put(\"msg\", \"上传成功\");\n");
                outStr.append("			ret.put(\"path\", state.getInfo(\"url\"));\n");
                outStr.append("			ret.put(\"fileName\", state.getInfo(\"original\"));\n");

                outStr.append("		}else {\n");
                outStr.append("			ret.put(\"status\", 1);\n");
                outStr.append("			ret.put(\"msg\", state.getInfo());\n");
                outStr.append("		}\n");
                outStr.append("		return new Feedback(ret);\n");
                outStr.append("	}\n");
            }

            outStr.append("\n");
            outStr.append("}");
            BufferedWriter outrWrite= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(project.getJavaBasePath()+project.getChildPackage().replace(".", "/")+"/"+bizModule.getPageName().replace(".","/")+"/"+className+".java"),BuildUtils.FILE_CHARSETNAME));
            outrWrite.write(outStr.toString());
            outrWrite.flush();
            outrWrite.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String getInterfaceName(String entityClassName){
        return "I"+entityClassName+apiModule.getSuffix();
    }

    private static boolean buildInterface(ProInfo project,FormInfo formInfo){
        String entityClassName=BuildUtils.buildTuoFengName(formInfo.getTag(),true,project.getDelTablePrefix()),
                entityClassNameReqDto=entityClassName+DtoType.DTO_REQUEST.getPrex(),
                entityClassNameRespDto=entityClassName+DtoType.DTO_RESPONSE.getPrex(),
                entityName=BuildUtils.buildTuoFengName(formInfo.getTag(),false,project.getDelTablePrefix());
        String interfaceClassName=getInterfaceName(entityClassName),servicName=entityName+ serviceModule.getSuffix();
        String path=BuildUtils.buildTuoFengName(formInfo.getPath(),false,project.getDelTablePrefix());
        String visitBasePath="/v1/"+formInfo.getModuleName()+"/"+path;
        File file=new File(project.getJavaBasePath()+project.getChildPackage().replace(".", "/")+"/"+apiModule.getPageName());
        try {
            if(!file.exists()) file.mkdirs();
            StringBuilder outStr=new StringBuilder();
            StringBuilder impltStr=new StringBuilder();
            {
                outStr.append("package "+project.getBasePackage()+project.getChildPackage()+"."+apiModule.getPageName()+";\n\n");
                //outStr.append("import javax.servlet.http.HttpServletResponse;\n");
                outStr.append("\n");
                //outStr.append("import org.springframework.cloud.openfeign.FeignClient;\n");
                outStr.append("import com.yofoys.services.commons.base.PageRespDto;\n");
                outStr.append("import io.swagger.annotations.*;\n");
                outStr.append("import org.springframework.cloud.openfeign.FeignClient;\n");
                outStr.append("import org.springframework.web.bind.annotation.*;\n");

                outStr.append("\n");
                //outStr.append("import "+project.getBasePackage()+".commons.BaseController;\n");
               // if(isDto){
                    outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_RESPONSE.getPackagName()+"."+entityClassName+DtoType.DTO_RESPONSE.getPrex()+";\n");
                    outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_RESPONSE_LIST.getPackagName()+"."+entityClassName+DtoType.DTO_RESPONSE_LIST.getPrex()+";\n");
                    outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_RESPONSE_PAGE.getPackagName()+"."+entityClassName+DtoType.DTO_RESPONSE_PAGE.getPrex()+";\n");
                    outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_REQUEST.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST.getPrex()+";\n");
                    outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_REQUEST_QUERY_LIST.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST_QUERY_LIST.getPrex()+";\n");
                    outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_REQUEST_QUERY_PAGE.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST_QUERY_PAGE.getPrex()+";\n");
                    outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_REQUEST_QUERY.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST_QUERY.getPrex()+";\n");
               // }
                outStr.append("import cn.com.yofogo.frame.assistant.PageUtil;\n");
                outStr.append("import cn.com.yofogo.tools.Feedback;\n");
                outStr.append("\n");
                outStr.append("import java.util.List;\n");
                //outStr.append("import com.yofoys.services.logs.tools.component.FeignFallbackFactory;\n");
                //outStr.append("import cn.com.yofogo.tools.IState;\n");
                //outStr.append("import cn.com.yofogo.tools.image.FileHelp;\n");
                outStr.append("\n");
                //outStr.append("@RequestMapping(value = \"${projMgPath}/"+formInfo.getPath()+"\")\n");
                //outStr.append("//@FeignClient(name = \"yzzou-module-\", fallbackFactory = "+entityClassName+"HystrixClientFallback.class)\n");
                outStr.append("@Api(tags = {\""+formInfo.getNames()+"\"})\n");
                outStr.append("@FeignClient(name = \""+project.getModuleServiceName()+"\")\n");
                //outStr.append("@FeignClient(name = \""+project.getModuleServiceName()+"\",path=\""+visitBasePath+"\")\n");
                outStr.append("public interface "+interfaceClassName+" {\n\n");// extends BaseController

                impltStr.append("class "+entityClassName+"HystrixClientFallback {\n");//  extends FeignFallbackFactory<"+interfaceClassName+"> implements "+className+"
                impltStr.append("	@Override\n");
                impltStr.append("	public "+interfaceClassName+" create() {\n");
                impltStr.append("	    return new "+interfaceClassName+"(){\n");
            }
            {//数据List列表

                String querHandleName=entityName+DtoType.DTO_REQUEST_QUERY.getPrex();
                String listData="List<"+entityClassName+DtoType.DTO_RESPONSE_LIST.getPrex()+">";


                outStr.append("	@ApiOperation(\"获取"+formInfo.getNames()+"列表信息\")\n");
                outStr.append("	@PostMapping(\""+visitBasePath+"/queryList\")\n");
                outStr.append("	Feedback<"+listData+"> queryList(@ApiParam(value = \""+formInfo.getNames()+"查询对象\",required = true) @RequestBody "+entityClassName+DtoType.DTO_REQUEST_QUERY_LIST.getPrex()+" "+querHandleName+");\n");//,"+entityClassName+dtoNameSuffix+" "+entityName+"

                impltStr.append("			@Override\n");
                impltStr.append("			public Feedback<"+listData+"> queryList("+entityClassName+DtoType.DTO_REQUEST_QUERY_LIST.getPrex()+" "+querHandleName+"){\n");//,"+entityClassName+dtoNameSuffix+" "+entityName+"
                impltStr.append("				return new Feedback(10500,\""+formInfo.getNames()+"List服务异常\");\n");
                impltStr.append("			}\n\n");
            }
            outStr.append("\n");
            outStr.append("\n");
            {//数据page列表

                String querHandleName=entityName+DtoType.DTO_REQUEST_QUERY.getPrex();
                outStr.append("	@ApiOperation(\"获取"+formInfo.getNames()+"分页列表信息。\")\n");
                outStr.append("	@PostMapping(\""+visitBasePath+"/queryPage\")\n");
                outStr.append("	Feedback<PageRespDto<"+entityClassName+DtoType.DTO_RESPONSE_PAGE.getPrex()+">> queryPage(@ApiParam(value = \""+formInfo.getNames()+"分页查询对象\",required = true) @RequestBody "+entityClassName+DtoType.DTO_REQUEST_QUERY_PAGE.getPrex()+" "+querHandleName+");\n");//,"+entityClassName+dtoNameSuffix+" "+entityName+"

                impltStr.append("			@Override\n");
                impltStr.append("			public Feedback<PageRespDto<"+entityClassName+DtoType.DTO_RESPONSE_PAGE.getPrex()+">> queryPage("+entityClassName+DtoType.DTO_REQUEST_QUERY_PAGE.getPrex()+" "+querHandleName+"){\n");//,"+entityClassName+dtoNameSuffix+" "+entityName+"
                impltStr.append("				return new Feedback(10500,\""+formInfo.getNames()+"分页服务异常\");\n");
                impltStr.append("			}\n\n");
            }
            outStr.append("\n");
            outStr.append("\n");
            String primaryKey=(formInfo.getTypes()==1?"id":formInfo.getPrimaryKeyToEntityName());
            {//根据主键获取数据
                outStr.append("\n");
                outStr.append("	@ApiOperation(\"根据主键获取"+formInfo.getNames()+"数据\")\n");
                outStr.append("	@GetMapping(\""+visitBasePath+"/get"+entityClassName+"ById\")\n");
                outStr.append("	Feedback<"+entityClassName+DtoType.DTO_RESPONSE.getPrex()+"> get"+entityClassName+"ById(@ApiParam(\"主键编码\") @RequestParam(\""+primaryKey+"\") Long "+primaryKey+");\n");

                impltStr.append("			@Override\n");
                impltStr.append("			public Feedback<"+entityClassName+DtoType.DTO_RESPONSE.getPrex()+"> get"+entityClassName+"ById(Long "+primaryKey+"){\n");
                impltStr.append("				return new Feedback(10500,\""+formInfo.getNames()+"查询服务异常\");\n");
                impltStr.append("			}\n");
            }
            outStr.append("\n");
            outStr.append("\n");
            {//添加
                outStr.append("\n");
                outStr.append("	@ApiOperation(\"添加"+formInfo.getNames()+"数据\")\n");
                outStr.append("	@PostMapping(\""+visitBasePath+"/add"+entityClassName+"\")\n");
                outStr.append("	Feedback<"+entityClassNameRespDto+"> add"+entityClassName+"(@ApiParam(value = \""+formInfo.getNames()+"对象\",required = true) @RequestBody "+entityClassNameReqDto+" "+entityName+");\n");

                impltStr.append("			@Override\n");
                impltStr.append("			public Feedback<"+entityClassNameRespDto+"> add"+entityClassName+"("+entityClassNameReqDto+" "+entityName+"){\n");
                impltStr.append("				return new Feedback(10500,\""+formInfo.getNames()+"服务新增数据异常\");\n");
                impltStr.append("			}\n");

                outStr.append("\n");
                //编辑
                outStr.append("\n");
                outStr.append("	@ApiOperation(\"修改"+formInfo.getNames()+"数据\")\n");
                outStr.append("	@PostMapping(\""+visitBasePath+"/update"+entityClassName+"\")\n");
                outStr.append("	Feedback<"+entityClassNameRespDto+"> update"+entityClassName+"(@ApiParam(value = \""+formInfo.getNames()+"对象\",required = true) @RequestBody "+entityClassNameReqDto+" "+entityName+");\n");

                impltStr.append("			@Override\n");
                impltStr.append("			public Feedback<"+entityClassNameRespDto+"> update"+entityClassName+"("+entityClassNameReqDto+" "+entityName+"){\n");
                impltStr.append("				return new Feedback(10500,\""+formInfo.getNames()+"服务更新数据异常\");\n");
                impltStr.append("			}\n");
            }
            impltStr.append("	    };\n");
            impltStr.append("	}\n\n");
            outStr.append("}\n");
            //outStr.append(impltStr).append("}");
            BufferedWriter outrWrite= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(project.getJavaBasePath()+project.getChildPackage().replace(".", "/")+"/"+apiModule.getPageName()+"/"+interfaceClassName+".java"),BuildUtils.FILE_CHARSETNAME));
            outrWrite.write(outStr.toString());
            outrWrite.flush();
            outrWrite.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean buildMgApplication(ProInfo project,FormInfo formInfo) {
        String entityClassName=BuildUtils.buildTuoFengName(formInfo.getTag(),true,project.getDelTablePrefix()),
                entityClassNameReqDto=entityClassName+DtoType.DTO_REQUEST.getPrex(),
                entityClassNameRespDto=entityClassName+DtoType.DTO_RESPONSE.getPrex(),
                entityName=BuildUtils.buildTuoFengName(formInfo.getTag(),false,project.getDelTablePrefix()),
                interfaceName=entityName+apiModule.getSuffix(),
                interfaceClassName=getInterfaceName(entityClassName);;
        String path=BuildUtils.buildTuoFengName(formInfo.getPath(),false,project.getDelTablePrefix());
        String packageName="."+String.format(appMgModule.getPageName(),"."+formInfo.getModuleName());
        String visitBasePath="/mgv1/"+formInfo.getModuleName()+"/"+path,
        saveFilePath=project.getJavaBasePath()+packageName.replace(".","/");
        File file=new File(saveFilePath);
        try {
            if(!file.exists()) file.mkdirs();
            StringBuilder outStr=new StringBuilder();
            StringBuilder impltStr=new StringBuilder();
            {
                outStr.append("package "+project.getBasePackage()+packageName+";\n\n");
                //outStr.append("import javax.servlet.http.HttpServletResponse;\n");
                outStr.append("\n");
                outStr.append("import com.yofoys.services.commons.base.PageRespDto;\n");
                outStr.append("import io.swagger.annotations.*;\n");
                outStr.append("import org.springframework.beans.factory.annotation.Autowired;\n");
                outStr.append("import org.springframework.web.bind.annotation.*;\n");

                outStr.append("\n");
                //outStr.append("import "+project.getBasePackage()+".commons.BaseController;\n");
                // if(isDto){

                outStr.append("import "+project.getBasePackage()+project.getChildPackage()+"."+apiModule.getPageName()+".I"+entityClassName+apiModule.getSuffix()+";\n");
                outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_RESPONSE.getPackagName()+"."+entityClassName+DtoType.DTO_RESPONSE.getPrex()+";\n");
                outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_RESPONSE_LIST.getPackagName()+"."+entityClassName+DtoType.DTO_RESPONSE_LIST.getPrex()+";\n");
                outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_RESPONSE_PAGE.getPackagName()+"."+entityClassName+DtoType.DTO_RESPONSE_PAGE.getPrex()+";\n");
                outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_REQUEST.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST.getPrex()+";\n");
                outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_REQUEST_QUERY_LIST.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST_QUERY_LIST.getPrex()+";\n");
                outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_REQUEST_QUERY_PAGE.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST_QUERY_PAGE.getPrex()+";\n");
                outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".api"+DtoType.DTO_REQUEST_QUERY.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST_QUERY.getPrex()+";\n");
                // }
                outStr.append("import cn.com.yofogo.frame.assistant.PageUtil;\n");
                outStr.append("import cn.com.yofogo.tools.Feedback;\n");
                outStr.append("\n");
                outStr.append("import java.util.List;\n");
                outStr.append("\n");
                outStr.append("@Api(tags = {\"后台管理——"+formInfo.getNames()+"\"})\n");
                outStr.append("@RestController\n");
                outStr.append("@RequestMapping(path =\""+visitBasePath+"\")\n");//,produces = "application/json"
                outStr.append("public class "+entityClassName+appMgModule.getSuffix()+" {\n\n");// extends BaseController
                outStr.append("	@Autowired\n");
                outStr.append("	private "+interfaceClassName+" "+interfaceName+";\n");
                outStr.append("\n");
            }
            {//数据List列表

                String listData="List<"+entityClassName+DtoType.DTO_RESPONSE_LIST.getPrex()+">";


                outStr.append("	@ApiOperation(\"获取"+formInfo.getNames()+"列表信息\")\n");
                outStr.append("	@PostMapping(\"/queryList\")\n");
                outStr.append("	public Feedback<"+listData+"> queryList(@ApiParam(value = \""+formInfo.getNames()+"查询对象\",required = true) @RequestBody "+entityClassName+DtoType.DTO_REQUEST_QUERY_LIST.getPrex()+" "+entityName+DtoType.DTO_REQUEST_QUERY.getPrex()+"){\n");//,"+entityClassName+dtoNameSuffix+" "+entityName+"
                outStr.append("		return "+interfaceName+".queryList("+entityName+DtoType.DTO_REQUEST_QUERY.getPrex()+");\n");
                outStr.append("	}\n\n");
            }
            outStr.append("\n");
            outStr.append("\n");
            {//数据page列表
                String querHandleName=entityName+DtoType.DTO_REQUEST_QUERY.getPrex();
                outStr.append("	@ApiOperation(\"获取"+formInfo.getNames()+"列表信息。分页\")\n");
                outStr.append("	@PostMapping(\"/queryPage\")\n");
                outStr.append("	public Feedback<PageRespDto<"+entityClassName+DtoType.DTO_RESPONSE_PAGE.getPrex()+">> queryPage(@ApiParam(value = \""+formInfo.getNames()+"分页查询对象\",required = true) @RequestBody "+entityClassName+DtoType.DTO_REQUEST_QUERY_PAGE.getPrex()+" "+querHandleName+"){\n");//,"+entityClassName+dtoNameSuffix+" "+entityName+"
                outStr.append("		return "+interfaceName+".queryPage("+querHandleName+");\n");
                outStr.append("	}\n\n");
            }
            outStr.append("\n");
            outStr.append("\n");
            String primaryKey=(formInfo.getTypes()==1?"id":formInfo.getPrimaryKeyToEntityName());
            {//根据主键获取数据
                outStr.append("\n");
                outStr.append("	@ApiOperation(\"根据主键获取"+formInfo.getNames()+"数据\")\n");
                outStr.append("	@GetMapping(\"/get"+entityClassName+"ById\")\n");
                outStr.append("	public Feedback<"+entityClassName+DtoType.DTO_RESPONSE.getPrex()+"> get"+entityClassName+"ById(@ApiParam(\"主键编码\") @RequestParam(\""+primaryKey+"\") Long "+primaryKey+"){\n");
                outStr.append("		return "+interfaceName+".get"+entityClassName+"ById("+primaryKey+");\n");
                outStr.append("	}\n");
            }
            outStr.append("\n");
            {//添加
                outStr.append("\n");
                outStr.append("	@ApiOperation(\"添加"+formInfo.getNames()+"数据\")\n");
                outStr.append("	@PostMapping(\"/add"+entityClassName+"\")\n");
                outStr.append("	public Feedback<"+entityClassNameRespDto+"> add"+entityClassName+"(@ApiParam(value = \""+formInfo.getNames()+"对象\",required = true) @RequestBody "+entityClassNameReqDto+" "+entityName+DtoType.DTO_REQUEST.getPrex()+"){\n");
                outStr.append("		return "+interfaceName+".add"+entityClassName+"("+entityName+DtoType.DTO_REQUEST.getPrex()+");\n");
                outStr.append("	}\n");

                outStr.append("\n");
                //编辑
                outStr.append("\n");
                outStr.append("	@ApiOperation(\"修改"+formInfo.getNames()+"数据\")\n");
                outStr.append("	@PostMapping(\"/update"+entityClassName+"\")\n");
                outStr.append("	public Feedback<"+entityClassNameRespDto+"> update"+entityClassName+"(@ApiParam(value = \""+formInfo.getNames()+"对象\",required = true) @RequestBody "+entityClassNameReqDto+" "+entityName+DtoType.DTO_REQUEST.getPrex()+"){\n");
                outStr.append("		return "+interfaceName+".update"+entityClassName+"("+entityName+DtoType.DTO_REQUEST.getPrex()+");\n");
                outStr.append("	}\n");
            }
            outStr.append("}\n");
            //outStr.append(impltStr).append("}");
            BufferedWriter outrWrite= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveFilePath+"/"+entityClassName+appMgModule.getSuffix()+".java"),BuildUtils.FILE_CHARSETNAME));
            outrWrite.write(outStr.toString());
            outrWrite.flush();
            outrWrite.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
