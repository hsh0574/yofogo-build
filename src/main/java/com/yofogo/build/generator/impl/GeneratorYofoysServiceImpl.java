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

    private final static ModuleFloorInfo serviceModule = new ModuleFloorInfo("Service","service");
    private final static ModuleFloorInfo bizModule=new ModuleFloorInfo("Controller","controller");
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

        String entityClassName=BuildUtils.buildTuoFengName(formInfo.getTag(),true),
                entityClassNamePo=entityClassName+"Po",
                entityClassNameReqDto=entityClassName+DtoType.DTO_REQUEST.getPrex(),
                entityName=BuildUtils.buildTuoFengName(formInfo.getTag(),false);
        String className=entityClassName+ serviceModule.getSuffix();
        File file=new File(project.getJavaBasePath()+project.getChildPackage().replace(".", "/")+"/"+serviceModule.getPageName()+"/impl");
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
                outInter.append("import "+project.getBasePackage()+project.getChildPackage()+".entity"+DtoType.DTO_RESPONSE.getPackagName()+"."+entityClassName+DtoType.DTO_RESPONSE.getPrex()+";\n");
                outInter.append("import "+project.getBasePackage()+project.getChildPackage()+".entity"+DtoType.DTO_REQUEST.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST.getPrex()+";\n");
                outInter.append("import "+project.getBasePackage()+project.getChildPackage()+".entity"+DtoType.DTO_REQUEST_QUERY.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST_QUERY.getPrex()+";\n");
                outInter.append("import cn.com.yofogo.frame.assistant.PageUtil;\n\n");
                outInter.append("public interface I"+className+" {\n\n");
            }
            {
                outImpl.append("package "+project.getBasePackage()+project.getChildPackage()+"."+serviceModule.getPageName()+".impl;\n\n");
                outImpl.append("import java.util.ArrayList;\n");
                outImpl.append("import java.util.List;\n");
                //outImpl.append("import "+project.getBasePackage()+project.getChildPackage()+".commons.BaseServiceImpl;\n");

                outImpl.append("import "+project.getBasePackage()+project.getChildPackage()+".entity.po."+entityClassNamePo+";\n");
                outImpl.append("import "+project.getBasePackage()+project.getChildPackage()+"."+serviceModule.getPageName()+".I"+className+";\n");
                outImpl.append("import cn.com.yofogo.frame.assistant.PageUtil;\n");
                outImpl.append("import cn.com.yofogo.frame.dao.DBModes.ResultMode;\n");
                outImpl.append("import cn.com.yofogo.frame.dao.perdure.BaseDao;\n");
                outImpl.append("import cn.com.yofogo.tools.Feedback;\n");
                outImpl.append("import com.yofoys.services.commontools.beanutil.BeanUtils;\n");
                outImpl.append("import org.springframework.beans.factory.annotation.Autowired;\n");
                //if(project.getControllerType()==1)
                {
                    outImpl.append("import org.springframework.stereotype.Service;\n");
                    outImpl.append("@Service\n");//(\""+className+"\")
                }
                outImpl.append("\n");
                outImpl.append("public class "+className+"Impl implements I"+className+" {\n\n");
            }
            outImpl.append("	@Resource(name = \""+project.getDatabaseName()+"\")\n");//
            outImpl.append("	private BaseDao dbHelp;\n");
            String methodHead="";
            for(String methodName : methods){
                if("add".equalsIgnoreCase(methodName)){
                    methodHead="	/**\n"
                            +"	 * 新增"+formInfo.getNames()+"\n"
                            +"	 * @param "+entityName+" \n"
                            +"	 * @return true:新增成功；false：新增失败\n"
                            +"	 */\n";
                    //接口
                    outInter.append(methodHead);
                    outInter.append("	public Feedback save"+entityClassName+"("+entityClassNameReqDto+" "+entityName+");\n");

                    //接口实现
                    outImpl.append(methodHead);
                    outImpl.append("	@Override\n");
                    outImpl.append("	public Feedback save"+entityClassName+"("+entityClassNameReqDto+" "+entityName+") {\n");
                    outImpl.append("		"+entityClassNamePo+" "+entityName+"Po = new "+entityClassNamePo+"();\n");
                    outImpl.append("		BeanUtils.copyProperties("+entityName+", "+entityName+"Po);\n");

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

                }else if("update".equalsIgnoreCase(methodName) && formInfo.getPrimaryType()==3 ){
                    methodHead="	/**\n"
                            +"	 * 修改"+formInfo.getNames()+"\n"
                            +"	 * @param "+entityName+" \n"
                            +"	 * @return true:修改成功；false：修改失败\n"
                            +"	 */\n";
                    {
                        outInter.append(methodHead);
                        outInter.append("	public Feedback update"+entityClassName+"("+entityClassNameReqDto+" "+entityName+");\n");
                    }
                    {
                        outImpl.append(methodHead);
                        outImpl.append("	@Override\n");
                        outImpl.append("	public Feedback update"+entityClassName+"("+entityClassNameReqDto+" "+entityName+") {\n");
                        outImpl.append("		"+entityClassNamePo+" "+entityName+"Po = new "+entityClassNamePo+"();\n");
                        outImpl.append("		BeanUtils.copyProperties("+entityName+", "+entityName+"Po);\n");
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
                outInter.append("	public "+entityClassName+DtoType.DTO_RESPONSE.getPrex()+" find"+entityClassName+"ById(String id);\n");

                SqlHandler sh=formInfo.getSelectSqlHandler("");
                outImpl.append(methodHead);
                outImpl.append("	@Override\n");
                outImpl.append("	public "+entityClassName+DtoType.DTO_RESPONSE.getPrex()+" find"+entityClassName+"ById(String id) {\n");
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
            {//分页条件查询

                String pageUtil="PageUtil<"+entityClassName+DtoType.DTO_RESPONSE.getPrex()+","+entityClassName+DtoType.DTO_REQUEST_QUERY.getPrex()+">";
                methodHead="	/**\n"
                        +"	 * 根据条件分页获取 "+formInfo.getNames()+"\n"
                        +"	 * @param page \n"
                        +"	 * @param "+entityName+"\n"
                        +"	 * @return \n"
                        +"	 */\n";
                outInter.append(methodHead);
                outInter.append("	public void find"+entityClassName+"s("+pageUtil+" page,"+entityClassName+DtoType.DTO_REQUEST_QUERY.getPrex()+" "+entityName+");\n");

                SqlHandler sh=formInfo.getSqlSelectAll();
                outImpl.append(methodHead);
                outImpl.append("	@Override\n");
                outImpl.append("	public void find"+entityClassName+"s("+pageUtil+" page,"+entityClassName+DtoType.DTO_REQUEST_QUERY.getPrex()+" "+entityName+") {\n");
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
                String getPrex="get";
                if(project.isLombok()) getPrex="";
                for(FormDataWhere where : formInfo.getFormDataWheres()){
                    field = formInfo.getFormElementField(where.getFdfid());
                    if(field==null) continue;
                    if(field.getFefid()==null) methodName=BuildUtils.buildTuoFengName(field.getDataTag(),true);
                    else methodName=BuildUtils.buildTuoFengName(field.getTag(),true);
                    if(where.getIsInterval()==1){
                        sqlWheres.append("		if("+entityName+"."+getPrex+methodName+"Begin()!=null && "+entityName+"."+getPrex+methodName+"End()!=null){\n");
                        sqlWheres.append("			sql += \" AND "+field.getDataTag()+" BETWEEN ? AND ?\";\n");
                        sqlWheres.append("			params.add("+entityName+"."+getPrex+methodName+"Begin());\n");
                        sqlWheres.append("			params.add("+entityName+"."+getPrex+methodName+"End());\n");
                        sqlWheres.append("		} else if("+entityName+"."+getPrex+methodName+"Begin()!=null) {\n");
                        sqlWheres.append("			sql += \" AND "+field.getDataTag()+" >= ?\";\n");
                        sqlWheres.append("			params.add("+entityName+"."+getPrex+methodName+"Begin());\n");
                        sqlWheres.append("		} else  if("+entityName+"."+getPrex+methodName+"End()!=null) {\n");
                        sqlWheres.append("			sql += \" AND "+field.getDataTag()+" <= ?\";\n");
                        sqlWheres.append("			params.add("+entityName+"."+getPrex+methodName+"End());\n");
                        sqlWheres.append("		} \n");
                    }else {
                        sqlWheres.append("		if("+entityName+"."+getPrex+methodName+"()!=null){\n");
                        if(where.getIsVague()==1){
                            sqlWheres.append("			sql += \" AND "+field.getDataTag()+" LIKE ?\";\n");
                            sqlWheres.append("			params.add(\"%\"+"+entityName+"."+getPrex+methodName+"()+\"%\");\n");
                        }else {
                            sqlWheres.append("			sql += \" AND "+field.getDataTag()+"=?\";\n");
                            sqlWheres.append("			params.add("+entityName+"."+getPrex+methodName+"());\n");
                        }
                        sqlWheres.append("		}\n");
                    }
                }
                if(sqlSelectItems.length()>0) {
                    outImpl.append("		String sql=\"SELECT "+sh.getSqlFields()+"\"\n");
                    outImpl.append(sqlSelectItems);
                    outImpl.append("			+\" FROM "+sh.getSqlTable()+("".equals(sh.getSqlWhere())?"":" WHERE "+sh.getSqlWhere())+"\";\n");
                } else outImpl.append("		String sql=\""+sh.getSql()+"\";\n");
                outImpl.append("		List<Object> params = new ArrayList<Object>();\n");
                if(formInfo.getTypes()==1) outImpl.append("		params.add("+formInfo.getFiid()+");\n");
                else if(formInfo.getFormDataWheres().size()>0) outImpl.append("		sql +=\" WHERE 1=1\";\n");
                outImpl.append(sqlWheres);
                outImpl.append("		dbHelp.queryForPage("+entityClassNamePo+".class, page, sql, params.toArray());\n");
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
            BufferedWriter InterWrite= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(project.getJavaBasePath()+project.getChildPackage().replace(".", "/")+"/"+serviceModule.getPageName()+"/I"+className+".java"),BuildUtils.FILE_CHARSETNAME));
            InterWrite.write(outInter.toString());
            InterWrite.flush();
            InterWrite.close();
            //实现类
            BufferedWriter implWrite= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(project.getJavaBasePath()+project.getChildPackage().replace(".", "/")+"/"+serviceModule.getPageName()+"/impl/"+className+"Impl.java"),BuildUtils.FILE_CHARSETNAME));
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

        String entityClassName=BuildUtils.buildTuoFengName(formInfo.getTag(),true),
                entityName=BuildUtils.buildTuoFengName(formInfo.getTag(),false),
                entityClassNameReqDto=entityClassName+DtoType.DTO_REQUEST.getPrex(),
                entityClassNameRespDto=entityClassName+DtoType.DTO_RESPONSE.getPrex();
        String dasInterface="I"+entityClassName+ serviceModule.getSuffix(),
                dasName=entityName+ serviceModule.getSuffix();
        String className=entityClassName+bizModule.getSuffix();
        String dtoNameSuffix="Dto";

        File file=new File(project.getJavaBasePath()+project.getChildPackage().replace(".", "/")+"/"+bizModule.getPageName());
        try {
            if(!file.exists()) file.mkdirs();
            StringBuilder outStr=new StringBuilder();
            {
                outStr.append("package "+project.getBasePackage()+project.getChildPackage()+"."+bizModule.getPageName()+";\n\n");
                outStr.append("\n");
                outStr.append("import javax.annotation.Resource;\n");
                //outStr.append("import javax.servlet.http.HttpServletResponse;\n");
                outStr.append("\n");
                outStr.append("import org.springframework.web.bind.annotation.*;\n");
                outStr.append("\n");
                //outStr.append("import "+project.getBasePackage()+".commons.BaseController;\n");
                outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".tools."+entityClassName+"Tools;\n");
                //if(isDto){
                    outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".entity"+DtoType.DTO_RESPONSE.getPackagName()+"."+entityClassName+DtoType.DTO_RESPONSE.getPrex()+";\n");
                    outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".entity"+DtoType.DTO_REQUEST.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST.getPrex()+";\n");
                    outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".entity"+DtoType.DTO_REQUEST_QUERY.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST_QUERY.getPrex()+";\n");
               // }
                outStr.append("import "+project.getBasePackage()+project.getChildPackage()+"."+serviceModule.getPageName()+".I"+entityClassName+ serviceModule.getSuffix() +";\n");
                outStr.append("import cn.com.yofogo.frame.assistant.PageUtil;\n");
                outStr.append("import cn.com.yofogo.tools.Feedback;\n");
                //outStr.append("import cn.com.yofogo.tools.IState;\n");
                //outStr.append("import cn.com.yofogo.tools.image.FileHelp;\n");
                outStr.append("\n");
                outStr.append("@RestController\n");
                //outStr.append("@RequestMapping(value = \"${projMgPath}/"+formInfo.getPath()+"\")\n");
                //outStr.append("@RequestMapping(value = \"/"+BuildUtils.buildTuoFengName(formInfo.getPath(),false)+"\")\n");
                outStr.append("public class "+className+" implements "+getInterfaceName(entityClassName)+"{\n\n");// extends BaseController
            }

            outStr.append("\n");
            outStr.append("	@Autowired\n");
            outStr.append("	private "+dasInterface+" "+dasName+";\n");
            outStr.append("\n");

            {//数据列表页面
                if(1==3){
                    outStr.append("	/**\n"
                            +"	 * 获取"+formInfo.getNames()+"列表数据\n"
                            +"	 * @param page 分布对象\n"
                            +"	 * @param "+entityName+" 查询条件\n"
                            +"	 * @return 返回json\n"
                            +"	 */\n");

                }
                String pageUtil="PageUtil<"+entityClassName+DtoType.DTO_RESPONSE.getPrex()+","+entityClassName+DtoType.DTO_REQUEST_QUERY.getPrex()+">";
                outStr.append("	@Override\n");
                outStr.append("	public Feedback<"+pageUtil+"> find"+entityClassName+"ForPage(@RequestBody "+pageUtil+" page){\n");//,"+entityClassName+dtoNameSuffix+" "+entityName+"
                outStr.append("		page.setPage(true);\n");
                outStr.append("		"+dasName+".page(page,page.getConditions());\n");//,"+entityName+"
                outStr.append("		return page;\n");//,"+entityName+"
				/*outStr.append("	public Map<String,Object> page(@RequestBody PageUtil page,"+entityClassName+dtoNameSuffix+" "+entityName+"){\n");
				outStr.append("		page.setPage(true);\n");
				outStr.append("		"+servicName+".find"+entityClassName+"s(page,"+entityName+");\n");
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
                outStr.append("	public Feedback<"+entityByIdRespDto+"> find"+entityClassName+"ById(@PathVariable(\""+primaryKey+"\") String "+primaryKey+"){\n");
                outStr.append("		"+entityByIdRespDto+" "+entityName+" = "+dasName+".find"+entityClassName+"ById("+primaryKey+");\n");
                outStr.append("		if("+entityName+" == null) {\n");
                outStr.append("		    return new Feedback<"+entityByIdRespDto+">(4,\"没有获取到"+formInfo.getNames()+"\");\n");
                outStr.append("		}\n");
                outStr.append("		return new Feedback<"+entityByIdRespDto+">("+entityName+");\n");
                outStr.append("	}\n");
			/*	outStr.append("	public "+entityClassNameDto+" find"+entityClassName+"ById(@PathVariable(\""+primaryKey+"\") String "+primaryKey+"){\n");
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
                outStr.append("	public Feedback<"+entityClassNameRespDto+"> save"+entityClassName+"(@RequestBody "+entityClassNameReqDto+" "+entityName+""+(formInfo.getPrimaryType()==3?",int sign":"")+"){\n");
                if(formInfo.getPrimaryType()==3) {
                    outStr.append("		Feedback fb;\n");
                    outStr.append("		if(sign==0){\n");
                    outStr.append("		    fb = " + dasName + ".save" + entityClassName + "(" + entityName + ");\n");
                    outStr.append("		} else {\n");
                    outStr.append("		    fb = " + dasName + ".update" + entityClassName + "(" + entityName + ");\n");
                    outStr.append("		} \n");
                    outStr.append("		return new Feedback<"+entityClassNameRespDto+">(fb.getCode(),fb.getMessage());\n");
                } else {
                    outStr.append("		Feedback fb = "+dasName+".save"+entityClassName+"("+entityName+");\n");
                    outStr.append("		return new Feedback<"+entityClassNameRespDto+">(fb.getCode(),fb.getMessage());\n");
                }
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
            BufferedWriter outrWrite= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(project.getJavaBasePath()+project.getChildPackage().replace(".", "/")+"/"+bizModule.getPageName()+"/"+className+".java"),BuildUtils.FILE_CHARSETNAME));
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
        return "I"+entityClassName+"Tools";
    }

    private static boolean buildInterface(ProInfo project,FormInfo formInfo){
        String entityClassName=BuildUtils.buildTuoFengName(formInfo.getTag(),true),
                entityClassNameReqDto=entityClassName+DtoType.DTO_REQUEST.getPrex(),
                entityClassNameRespDto=entityClassName+DtoType.DTO_RESPONSE.getPrex(),
                entityName=BuildUtils.buildTuoFengName(formInfo.getTag(),false);
        String interfaceClassName=getInterfaceName(entityClassName),servicName=entityName+ serviceModule.getSuffix();
        String path=BuildUtils.buildTuoFengName(formInfo.getPath(),false);
        File file=new File(project.getJavaBasePath()+project.getChildPackage().replace(".", "/")+"/tools");
        try {
            if(!file.exists()) file.mkdirs();
            StringBuilder outStr=new StringBuilder();
            StringBuilder impltStr=new StringBuilder();
            {
                outStr.append("package "+project.getBasePackage()+project.getChildPackage()+".tools;\n\n");
                //outStr.append("import javax.servlet.http.HttpServletResponse;\n");
                outStr.append("\n");
                //outStr.append("import org.springframework.cloud.openfeign.FeignClient;\n");
                outStr.append("import org.springframework.web.bind.annotation.*;\n");
                outStr.append("\n");
                //outStr.append("import "+project.getBasePackage()+".commons.BaseController;\n");
               // if(isDto){
                    outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".entity"+DtoType.DTO_RESPONSE.getPackagName()+"."+entityClassName+DtoType.DTO_RESPONSE.getPrex()+";\n");
                    outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".entity"+DtoType.DTO_REQUEST.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST.getPrex()+";\n");
                    outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".entity"+DtoType.DTO_REQUEST_QUERY.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST_QUERY.getPrex()+";\n");
               // }
                outStr.append("import cn.com.yofogo.frame.assistant.PageUtil;\n");
                outStr.append("import cn.com.yofogo.tools.Feedback;\n");
                outStr.append("import com.yofoys.services.logs.tools.component.FeignFallbackFactory;\n");
                //outStr.append("import cn.com.yofogo.tools.IState;\n");
                //outStr.append("import cn.com.yofogo.tools.image.FileHelp;\n");
                outStr.append("\n");
                //outStr.append("@RequestMapping(value = \"${projMgPath}/"+formInfo.getPath()+"\")\n");
                outStr.append("//@FeignClient(name = \"yzzou-module-\", fallbackFactory = "+entityClassName+"HystrixClientFallback.class)\n");
                outStr.append("public interface "+interfaceClassName+" {\n\n");// extends BaseController

                impltStr.append("class "+entityClassName+"HystrixClientFallback extends FeignFallbackFactory<"+interfaceClassName+"> {\n");//  implements "+className+"
                impltStr.append("	@Override\n");
                impltStr.append("	public "+interfaceClassName+" create() {\n");
                impltStr.append("	    return new "+interfaceClassName+"(){\n");
            }
            {//数据列表

                String pageUtil="PageUtil<"+entityClassName+DtoType.DTO_RESPONSE.getPrex()+","+entityClassName+DtoType.DTO_REQUEST_QUERY.getPrex()+">";


                outStr.append("	@ApiOperation(\"获取"+formInfo.getNames()+"列表信息。分页\")\n");
                outStr.append("	@ApiImplicitParams({\n");
                outStr.append("	        @ApiImplicitParam(name = \"pageSize\", value = \"每页条数\", dataType = \"int\", paramType = \"query\", required = true,defaultValue = \"0\")\n");
                outStr.append("	})\n");
                outStr.append("	@ApiResponses({\n");
                outStr.append("	        @ApiResponse(code = 0, message = \"成功\"),\n");
                outStr.append("	})");
                outStr.append("	@GetMapping(\"/"+formInfo.getModuleName()+"/"+path+"/page\")\n");
                outStr.append("	public Feedback<"+pageUtil+"> find"+entityClassName+"ForPage(@RequestBody "+pageUtil+" page);\n");//,"+entityClassName+dtoNameSuffix+" "+entityName+"

                impltStr.append("			@Override\n");
                impltStr.append("			public Feedback<"+pageUtil+"> find"+entityClassName+"ForPage("+pageUtil+" page){\n");//,"+entityClassName+dtoNameSuffix+" "+entityName+"
                impltStr.append("				return new Feedback(11111,\""+formInfo.getNames()+"分页服务异常\");\n");
                impltStr.append("			}\n\n");
            }
            String primaryKey=(formInfo.getTypes()==1?"id":formInfo.getPrimaryKeyToEntityName());
            {//根据主键获取数据
                outStr.append("\n");
                outStr.append("	@ApiOperation(\"根据主键获取"+formInfo.getNames()+"数据\")\n");
                outStr.append("	@ApiImplicitParams({\n");
                outStr.append("	        @ApiImplicitParam(name = \""+primaryKey+"\", value = \"主键编码\", dataType = \"String\", paramType = \"query\", required = true,defaultValue = \"\")\n");
                outStr.append("	})\n");
                outStr.append("	@ApiResponses({\n");
                outStr.append("	        @ApiResponse(code = 0, message = \"表示成功\"),\n");
                outStr.append("	})");
                outStr.append("	@GetMapping(\"/"+formInfo.getModuleName()+"/"+path+"/find/{"+primaryKey+"}\")\n");
                outStr.append("	public Feedback<"+entityClassName+DtoType.DTO_RESPONSE.getPrex()+"> find"+entityClassName+"ById(@PathVariable(\""+primaryKey+"\") String "+primaryKey+");\n");

                impltStr.append("			@Override\n");
                impltStr.append("			public Feedback<"+entityClassName+DtoType.DTO_RESPONSE.getPrex()+"> find"+entityClassName+"ById(String "+primaryKey+"){\n");
                impltStr.append("				return new Feedback(11111,\""+formInfo.getNames()+"查询服务异常\");\n");
                impltStr.append("			}\n");
            }
            {//添加编辑
                outStr.append("\n");
                outStr.append("	@ApiOperation(\"保存"+formInfo.getNames()+"数据\")\n");
                outStr.append("	@ApiImplicitParams({\n");
                outStr.append("	        @ApiImplicitParam(name = \""+entityName+"\", value = \"数据对象\", dataType = \"Object\", paramType = \"query\", required = true),\n");
                if(formInfo.getPrimaryType()==3) {
                    outStr.append("	        @ApiImplicitParam(name = \"sign\", value = \"0添加数据；1修改数据\", dataType = \"Integer\", paramType = \"query\", required = true,defaultValue = \"0\")\n");
                }
                outStr.append("	})\n");
                outStr.append("	@ApiResponses({\n");
                outStr.append("	        @ApiResponse(code = 0, message = \"表示成功，info为保存对象\"),\n");
                outStr.append("	})");
                outStr.append("	@PostMapping(\"/"+formInfo.getModuleName()+"/"+path+"/save\")\n");
                outStr.append("	public Feedback<"+entityClassNameRespDto+"> save"+entityClassName+"(@RequestBody "+entityClassNameReqDto+" "+entityName+""+(formInfo.getPrimaryType()==3?",Integer sign":"")+");\n");

                impltStr.append("			@Override\n");
                impltStr.append("			public Feedback<"+entityClassNameRespDto+"> save"+entityClassName+"("+entityClassNameReqDto+" "+entityName+""+(formInfo.getPrimaryType()==3?",Integer sign":"")+"){\n");
                impltStr.append("				return new Feedback(11111,\""+formInfo.getNames()+"服务更新数据异常\");\n");
                impltStr.append("			}\n");
            }
            impltStr.append("	    };\n");
            impltStr.append("	}\n\n");
            outStr.append("}\n");
            outStr.append(impltStr).append("}");
            BufferedWriter outrWrite= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(project.getJavaBasePath()+project.getChildPackage().replace(".", "/")+"/tools/"+interfaceClassName+".java"),BuildUtils.FILE_CHARSETNAME));
            outrWrite.write(outStr.toString());
            outrWrite.flush();
            outrWrite.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }




    public boolean buildApplication(ProInfo project,FormInfo formInfo) {
        boolean interfaceRet=buildInterface(project, formInfo);
        if(!interfaceRet) return false;

        String entityClassName=BuildUtils.buildTuoFengName(formInfo.getTag(),true),
                entityClassNameDto=entityClassName+"Dto",
                entityName=BuildUtils.buildTuoFengName(formInfo.getTag(),false);
        String className=entityClassName+"Controller",toolsName=entityName+"Tools";
        String dtoNameSuffix="Dto";

        File file=new File(project.getJavaBasePath()+project.getChildPackage().replace(".", "/")+"/controller");
        try {
            if(!file.exists()) file.mkdirs();
            StringBuilder outStr=new StringBuilder();
            {
                outStr.append("package "+project.getBasePackage()+project.getChildPackage()+".controller;\n\n");
                outStr.append("\n");
                outStr.append("import javax.annotation.Resource;\n");
                //outStr.append("import javax.servlet.http.HttpServletResponse;\n");
                outStr.append("\n");
                outStr.append("import org.springframework.web.bind.annotation.*;\n");
                outStr.append("\n");
                //outStr.append("import "+project.getBasePackage()+".commons.BaseController;\n");
                outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".tools."+entityClassName+"Tools;\n");
                //if(isDto){
                outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".entity"+DtoType.DTO_RESPONSE.getPackagName()+"."+entityClassName+DtoType.DTO_RESPONSE.getPrex()+";\n");
                outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".entity"+DtoType.DTO_REQUEST.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST.getPrex()+";\n");
                outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".entity"+DtoType.DTO_REQUEST_QUERY.getPackagName()+"."+entityClassName+DtoType.DTO_REQUEST_QUERY.getPrex()+";\n");
                // }
                outStr.append("import "+project.getBasePackage()+project.getChildPackage()+"."+serviceModule.getPageName()+".I"+entityClassName+ serviceModule.getSuffix() +";\n");
                outStr.append("import cn.com.yofogo.frame.assistant.PageUtil;\n");
                outStr.append("import cn.com.yofogo.tools.Feedback;\n");
                //outStr.append("import cn.com.yofogo.tools.IState;\n");
                //outStr.append("import cn.com.yofogo.tools.image.FileHelp;\n");
                outStr.append("\n");
                outStr.append("@RestController\n");
                //outStr.append("@RequestMapping(value = \"${projMgPath}/"+formInfo.getPath()+"\")\n");
                //outStr.append("@RequestMapping(value = \"/"+BuildUtils.buildTuoFengName(formInfo.getPath(),false)+"\")\n");
                outStr.append("public class "+className+" implements "+getInterfaceName(entityClassName)+"{\n\n");// extends BaseController
            }

            outStr.append("\n");
            outStr.append("	@Autowired\n");
            outStr.append("	private "+entityClassName+"Tools "+toolsName+";\n");
            outStr.append("\n");

            {//数据列表页面
                if(1==3){
                    outStr.append("	/**\n"
                            +"	 * 转到"+formInfo.getNames()+"数据列表页面\n"
                            +"	 * @return \n"
                            +"	 */\n");
                    outStr.append("	@GetMapping(value = { \"toPage\", \"\" })\n");
                    outStr.append("	public String toPage(){\n");
                    outStr.append("		return \"manager/"+formInfo.getPath()+"/"+entityName+"_listPage\";\n");
                    outStr.append("	}\n\n");
                    outStr.append("	/**\n"
                            +"	 * 获取"+formInfo.getNames()+"列表数据\n"
                            +"	 * @param page 分布对象\n"
                            +"	 * @param "+entityName+" 查询条件\n"
                            +"	 * @return 返回json\n"
                            +"	 */\n");

                }
                String pageUtil="PageUtil<"+entityClassName+DtoType.DTO_RESPONSE.getPrex()+","+entityClassName+DtoType.DTO_REQUEST_QUERY.getPrex()+">";
                outStr.append("	@GetMapping(value = { \"/page\", \"\" })\n");
                outStr.append("	@ResponseBody\n");
                outStr.append("	public Feedback<"+pageUtil+"> page(@RequestBody "+pageUtil+" page){\n");//,"+entityClassName+dtoNameSuffix+" "+entityName+"
                outStr.append("		page.setPage(true);\n");
                outStr.append("		"+toolsName+".page(page);\n");//,"+entityName+"
                outStr.append("		return page;\n");//,"+entityName+"
				/*outStr.append("	public Map<String,Object> page(@RequestBody PageUtil page,"+entityClassName+dtoNameSuffix+" "+entityName+"){\n");
				outStr.append("		page.setPage(true);\n");
				outStr.append("		"+servicName+".find"+entityClassName+"s(page,"+entityName+");\n");
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
                outStr.append("	@ResponseBody\n");
                outStr.append("	public Feedback<"+entityClassName+DtoType.DTO_RESPONSE.getPrex()+"> find"+entityClassName+"ById(@PathVariable(\""+primaryKey+"\") String "+primaryKey+"){\n");
                outStr.append("		"+entityClassName+" "+entityName+" = "+toolsName+". find"+entityClassName+"ById("+primaryKey+");\n");
                outStr.append("		if("+entityName+" == null) {\n");
                outStr.append("		    return new Feedback<"+entityClassName+DtoType.DTO_RESPONSE.getPrex()+">(4,\"没有获取到"+formInfo.getNames()+"\");\n");
                outStr.append("		}\n");
                outStr.append("		return return new Feedback<"+entityClassName+DtoType.DTO_RESPONSE.getPrex()+">("+entityName+");\n");
                outStr.append("	}\n");
			/*	outStr.append("	public "+entityClassNameDto+" find"+entityClassName+"ById(@PathVariable(\""+primaryKey+"\") String "+primaryKey+"){\n");
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
                outStr.append("	/**\n"
                        +"	 * 保存"+formInfo.getNames()+"数据\n"
                        +"	 * @param "+entityName+"\n"+(formInfo.getPrimaryType()==3?"	 * @param sign 0添加数据；1表示修改数据。\n":"")
                        +"	 * @return 返回json\n"
                        +"	 */\n");
                outStr.append("	@PostMapping(value = { \"/save\", \"\" })\n");
                //outStr.append("	@PostMapping(value = { \""+entityName+"\", \"\" })\n");
                outStr.append("	@ResponseBody\n");
                outStr.append("	public Feedback<"+entityClassNameDto+"> save"+entityClassName+"(@RequestBody "+entityClassNameDto+" "+entityName+""+(formInfo.getPrimaryType()==3?",int sign":"")+"){\n");
                if(formInfo.getPrimaryType()==3) {
                    outStr.append("		Feedback fb;\n");
                    outStr.append("		if(sign==0){\n");
                    outStr.append("		    fb = " + toolsName + ".save" + entityClassName + "(" + entityName + ");\n");
                    outStr.append("		} else {\n");
                    outStr.append("		    fb = " + toolsName + ".update" + entityClassName + "(" + entityName + ");\n");
                    outStr.append("		} \n");
                    outStr.append("		return new Feedback<"+entityClassNameDto+">(fb.getCode(),fb.getMessage(),"+entityName+");\n");
                } else {
                    outStr.append("		Feedback fb = "+toolsName+".update"+entityClassName+"("+entityName+");\n");
                    outStr.append("		return new Feedback<"+entityClassNameDto+">(fb.getCode(),fb.getMessage(),"+entityName+");\n");
                }
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
            BufferedWriter outrWrite= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(project.getJavaBasePath()+project.getChildPackage().replace(".", "/")+"/controller/"+className+".java"),BuildUtils.FILE_CHARSETNAME));
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
