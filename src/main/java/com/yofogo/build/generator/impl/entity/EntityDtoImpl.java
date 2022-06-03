package com.yofogo.build.generator.impl.entity;

import cn.com.yofogo.tools.util.DateTimeUtil;
import com.yofogo.build.entity.dto.FormDataWhere;
import com.yofogo.build.entity.dto.FormElementField;
import com.yofogo.build.entity.dto.FormInfo;
import com.yofogo.build.entity.dto.SelectItems;
import com.yofogo.build.entity.enums.DBFieldType;
import com.yofogo.build.entity.enums.DtoType;
import com.yofogo.build.entity.enums.FormFieldType;
import com.yofogo.build.entity.po.ProInfo;
import com.yofogo.build.generator.BuildUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class EntityDtoImpl implements IEntityDto {

    private static boolean isAnnotationDesc=false;
    private static boolean IS_NEW_MODULE=true;

    private final static List<String> COMMON_FIELDS= Arrays.asList("tenantId,instanceId,createPerson,createTime,updatePerson,updateTime,isFlat".split(","));

    public boolean buildEntityPo(ProInfo project, FormInfo formInfo) {
        String className= BuildUtils.buildTuoFengName(formInfo.getTag(),true,project.getDelTablePrefix());
        try {
            StringBuilder outStr=new StringBuilder();
            outStr.append("package "+project.getBasePackage()+project.getChildPackage()+BuildUtils.DAO_PACKAGE_NAME +".po;\n");
            outStr.append("\n");
            outStr.append("import org.springframework.format.annotation.DateTimeFormat;\n");
            outStr.append("\n");
            outStr.append("import cn.com.yofogo.frame.dao.annotation.ColumnAnnotation;\n");
            outStr.append("import cn.com.yofogo.frame.dao.annotation.TableAnnotation;\n");
            outStr.append("import cn.com.yofogo.tools.util.DateTimeUtil;\n");
            //outStr.append("import java.util.*;");
            //if(isLombok) outStr.append("import lombok.experimental.Accessors;");
            outStr.append("\n");
            outStr.append("/**\n * "+formInfo.getNames()+"PO实体类\n * @author zhengzhou.yang\n * "+ DateTimeUtil.getCurrentDatetime("yyyy-MM-dd")+"\n */\n");
            outStr.append("@TableAnnotation(name = \""+formInfo.getTableName()+"\",isMustAnnotation=true)\n");
            //if(isLombok) outStr.append("@Accessors(chain = true)\n");
            outStr.append("public class "+className+"Po implements java.io.Serializable{\n");
            outStr.append("	private static final long serialVersionUID = "+new Random().nextLong()+"L;\n");
            outStr.append("\n");
            outStr.append("\n");
            outStr.append("	//private fields");
            outStr.append("\n");
            StringBuilder methods=new StringBuilder("	//public methods\n");
            String annotation;
            FormFieldType fieldFType=null;
            boolean isBuildPrimary=false;
            if(formInfo.getTypes()==1) {
                isBuildPrimary=true;
                annotation="@ColumnAnnotation(name=\"id\",primaryKey=true)";
                buildEnityField(outStr,methods,annotation,"id",fieldFType,"String","主键编号",null,0);
                annotation="@ColumnAnnotation(name=\"system_fiid\",notNull=true)";
                buildEnityField(outStr,methods,annotation,"systemFiid",fieldFType,"Integer","所属表单编号",formInfo.getFiid(),1);
                annotation="@ColumnAnnotation(name=\"system_created\",insert=true)";
                buildEnityField(outStr,methods,annotation,"systemCreated",fieldFType,"java.util.Date","数据添加时间",null,0);
                annotation="@ColumnAnnotation(name=\"system_update\",insert=true ,update=true)";
                buildEnityField(outStr,methods,annotation,"systemUpdate",fieldFType,"java.util.Date","数据最后修改时间",null,0);
            }else if(formInfo.getPrimaryType()==1 || formInfo.getPrimaryType()==2){
                isBuildPrimary=true;
                if(isAnnotationDesc) annotation="@Note(\"主键编号\")\n	";
                else annotation="";
                annotation+="@ColumnAnnotation(name=\""+formInfo.getPrimaryKey()+"\",primaryKey=true";
                String typeStr="String";
                if(formInfo.getPrimaryType()==1){
                    annotation+=",isAutoIncrease=true";
                    typeStr="Long";
                }
                buildEnityField(outStr,methods,annotation+")",formInfo.getPrimaryKey(),fieldFType,typeStr,"主键编号",null,0);
            }
            outStr.append("\n");
            StringBuilder outStrOther=new StringBuilder();
            StringBuilder methodOther=new StringBuilder();
            String fieldName,typeName;
            for(FormElementField field : formInfo.getEleFields()){
                if(isAnnotationDesc) annotation="@Note(\""+field.getNames()+"\")\n	";
                else annotation="";
                annotation+="@ColumnAnnotation(name=\""+field.getDataTag()+"\"";
                if(formInfo.getPrimaryKey().equalsIgnoreCase(field.getDataTag())){
                    formInfo.setPrimaryKeyToEntityName(field.getFefid()==null?field.getDataTag():field.getTag());
                    if(isBuildPrimary) continue;
                    annotation+=",primaryKey=true";
                    if(formInfo.getPrimaryType()==1) annotation+=",isAutoIncrease=true";
                }
                if(field.getFefid()==null){
                    if(field.getFdfMust()==1) annotation+=",notNull=true";
                    fieldName=BuildUtils.buildTuoFengName(field.getDataTag(),false);
                    try {
                        fieldFType= DBFieldType.valueOf(field.getDbType().toUpperCase()).toEleFieldType();
                    } catch (Exception e) {
                        System.out.println(field.getDbType());
                    }
                }else {
                    if(field.getFdfMust()==1) annotation+=",notNull=true";
                    fieldName=BuildUtils.buildTuoFengName(field.getTag(),false);
                    fieldFType=FormFieldType.valueOf(field.getTypes());
                }
                typeName = BuildUtils.buildFieldTypeName(DBFieldType.valueOf(field.getDbType()));
                if("created".equals(fieldName) || "createTime".equals(fieldName)){
                    annotation+=",insert = true";
                }
                buildEnityField(outStr,methods,annotation+")",fieldName,fieldFType,typeName,field.getNames(),null,0);
                SelectItems selectItem=field.getSelectItem();
                if(selectItem!=null && selectItem.getType()==2) buildEnityField(outStrOther,methodOther,null,fieldName+"ToOfItemName",fieldFType,"String",field.getNames()+"对应说明信息",null,0);

                outStr.append("\n");
            }
            FormElementField field;
            for(FormDataWhere where : formInfo.getFormDataWheres()){
                if(where.getIsInterval()!=1) continue;
                field=formInfo.getFormElementField(where.getFdfid());
                if(field==null) continue;
                fieldName=BuildUtils.buildTuoFengName((field.getFefid()==null?field.getDataTag():field.getTag()),false);
                typeName=BuildUtils.buildFieldTypeName(DBFieldType.valueOf(field.getDbType()));
                buildEnityField(outStrOther,methodOther,null,fieldName+"_begin",fieldFType,typeName,field.getNames()+" 的开始",null,0);
                buildEnityField(outStrOther,methodOther,null,fieldName+"_end",fieldFType,typeName,field.getNames()+" 的结束",null,0);
            }
            if(outStrOther.length()>0) outStr.append("\n	//other fields\n");
            outStr.append(outStrOther.toString());
            outStr.append("\n");
            outStr.append(methods.toString());
            outStr.append(methodOther.toString());
            outStr.append("}");
            File file=new File(project.getJavaBasePath()+(project.getChildPackage()+BuildUtils.DAO_PACKAGE_NAME).replace(".", "/")+"/po");
            if(!file.exists()) file.mkdirs();
            BufferedWriter outWrite= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(project.getJavaBasePath()+(project.getChildPackage()+BuildUtils.DAO_PACKAGE_NAME).replace(".", "/")+"/po/"+className+"Po.java"),BuildUtils.FILE_CHARSETNAME));
            outWrite.write(outStr.toString());
            outWrite.flush();
            outWrite.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean buildEntityDto(ProInfo project,FormInfo formInfo){
        String className=BuildUtils.buildTuoFengName(formInfo.getTag(),true,project.getDelTablePrefix());
        File file=new File(project.getJavaBasePath()+project.getChildPackage().replace(".", "/")+"/api/dto");
        try {
            if(!file.exists()) file.mkdirs();
            for (DtoType dtoType : DtoType.values()){
                _buildEntityDto(project,formInfo,className, dtoType);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    private void _buildEntityDto(ProInfo project,FormInfo formInfo,String className,DtoType dtoType) throws IOException {

        String extendsStr = IS_NEW_MODULE ? " extends BaseQueryDto" : "";

        StringBuilder outStr = new StringBuilder();
        outStr.append("package " + project.getBasePackage() + project.getChildPackage() + ".api" + dtoType.getPackagName() + ";\n");
        outStr.append("\n");
        outStr.append("import cn.com.yofogo.tools.util.DateTimeUtil;\n");
        //outStr.append("import com.yzzou.commons.utils.ReqeustQueryDto;\n");
        outStr.append("import org.springframework.format.annotation.DateTimeFormat;\n");
        //outStr.append("import "+project.getBasePackage()+project.getChildPackage()+".entity.vo."+className+"Vo;\n");
        //if(isLombok) outStr.append("import lombok.experimental.Accessors;");
        outStr.append("import io.swagger.annotations.ApiModelProperty;\n");
        String extendClass = "";
        if (DtoType.DTO_REQUEST_QUERY_PAGE == dtoType) {
            extendClass = IS_NEW_MODULE ? " extends PageQueryDto" : "";
            outStr.append("import com.yofoys.services.commons.base.PageQueryDto;");
        } else if (dtoType.name().startsWith(DtoType.DTO_REQUEST_QUERY.name())) {
            extendClass = IS_NEW_MODULE ? " extends BaseQueryDto" : "";
            outStr.append("import com.yofoys.services.commons.base.BaseQueryDto;");
        } else if (DtoType.DTO_REQUEST == dtoType) {
            extendClass = IS_NEW_MODULE ? " extends BaseReqDto" : "";
            outStr.append("import com.yofoys.services.commons.base.BaseReqDto;");
        }
        if (dtoType.getExtendsDto() != null) {
            extendClass = " extends " + className + dtoType.getExtendsDto().getPrex();
        }

        //else if(DtoType.DTO_RESPONSE==dtoTypeName) extendClass = IS_NEW_MODULE?" extends BaseQueryDto":"";
        outStr.append("\n");
        outStr.append("\n");
        outStr.append("/**\n * " + formInfo.getNames() + dtoType.getPrex() + "实体类\n * @author zhengzhou.yang\n * " + DateTimeUtil.getCurrentDatetime("yyyy-MM-dd") + "\n */\n");
        outStr.append("public class " + className + dtoType.getPrex() + extendClass)
                .append("  implements java.io.Serializable{\n");
        outStr.append("	private static final long serialVersionUID = " + new Random().nextLong() + "L;\n");
        outStr.append("\n");
        outStr.append("\n");
        if (dtoType.getExtendsDto() == null) {
            StringBuilder methods = new StringBuilder();


            FormFieldType fieldFType = null;
            String fieldName, typeName, annotation;
            for (FormElementField field : formInfo.getEleFields()) {
                annotation = "";
                if (field.getFefid() == null) {
                    //if(field.getFdfMust()==1) annotation+=",notNull=true";
                    fieldName = BuildUtils.buildTuoFengName(field.getDataTag(), false);
                    try {
                        fieldFType = DBFieldType.valueOf(field.getDbType().toUpperCase()).toEleFieldType();
                    } catch (Exception e) {
                        System.out.println(field.getDbType());
                    }
                } else {
                    //if(field.getFdfMust()==1) annotation+=",notNull=true";
                    fieldName = BuildUtils.buildTuoFengName(field.getTag(), false);
                    fieldFType = FormFieldType.valueOf(field.getTypes());
                }
                if (COMMON_FIELDS.contains(fieldName)) {
                    if (dtoType.name().indexOf("RESPONSE") < 0 || !"createTime".equals(fieldName)) {
                        continue;
                    }
                }
                annotation += ("".equals(annotation) ? "" : "\n") + "@ApiModelProperty(name = \"" + fieldName + "\",value = \"" + field.getNames() + "\")";

                typeName = BuildUtils.buildFieldTypeName(DBFieldType.valueOf(field.getDbType()));

                buildEnityField(outStr, methods, annotation, fieldName, fieldFType, typeName, field.getNames(), null, 0);
                SelectItems selectItem = field.getSelectItem();
                if (selectItem != null && selectItem.getType() == 2)
                    buildEnityField(outStr, methods, null, fieldName + "ToOfItemName", fieldFType, "String", field.getNames(), null, 0);

                outStr.append("\n");
            }

                /*if(DTO_REQUEST_QUERY.equals(dtoTypeName)){
                    for(FormElementField field : formInfo.getEleFields()){
                        String fieldName,typeName;
                        FormFieldType fieldFType=null;
                        if(field.getFefid()==null){
                            fieldName=BuildUtils.buildTuoFengName(field.getDataTag(),false);
                            try {
                                fieldFType=DBFieldType.valueOf(field.getDbType().toUpperCase()).toEleFieldType();
                            } catch (Exception e) {
                                System.out.println(field.getDbType());
                            }
                        }else {
                            fieldName=BuildUtils.buildTuoFengName(field.getTag(),false);
                            fieldFType=FormFieldType.valueOf(field.getTypes());
                        }
                        typeName=BuildUtils.buildFieldTypeName(DBFieldType.valueOf(field.getDbType()));

                        buildEnityField(outStr,methods,"",fieldName,fieldFType,typeName,field.getNames(),null,0);
                        outStr.append("\n");
                    }
                } else buildEnityField(outStr,methods,"",className+"Vo",null,className+"Vo",formInfo.getNames(),null,0);

                */


            outStr.append(methods.toString());
        }
        outStr.append("\n");
        outStr.append("}");
        File file=new File(project.getJavaBasePath()+project.getChildPackage().replace(".", "/")+"/api/"+dtoType.getPackagName().replace(".","/"));
        if(!file.exists()) file.mkdirs();
        BufferedWriter outWrite= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(project.getJavaBasePath()+project.getChildPackage().replace(".", "/")+"/api"+dtoType.getPackagName().replace(".","/")+"/"+className+dtoType.getPrex()+".java"),BuildUtils.FILE_CHARSETNAME));
        outWrite.write(outStr.toString());
        outWrite.flush();
        outWrite.close();
    }

    //methodSign(0有get、set；1无set有get；2无get有set；3无set、get)
    private static void buildEnityField(StringBuilder outStr,StringBuilder methods,String annotation,String fieldName,FormFieldType fieldType
            ,String typeName,String descName,Object val,int methodSign) throws IOException{
        String methodName=fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);

        if(methodSign==0 || methodSign==1){
            String fomatStr=null;
            if(FormFieldType.DATE==fieldType){
                fomatStr="yyyy-MM-dd";
                annotation=annotation==null?"	@DateTimeFormat(pattern=\"yyyy-MM-dd\")":annotation+"\n	@DateTimeFormat(pattern=\"yyyy-MM-dd\")";
            } else if(FormFieldType.DATETIME==fieldType){
                fomatStr="yyyy-MM-dd HH:mm:ss";
                annotation=annotation==null?"	@DateTimeFormat(pattern=\"yyyy-MM-dd HH:mm:ss\")":annotation+"\n	@DateTimeFormat(pattern=\"yyyy-MM-dd HH:mm:ss\")";
            }
            //@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
            //if(fomatStr==null){
            methods.append("	/**\n");
            methods.append("	 * "+descName+"\n");
            methods.append("	 * @return\n");
            methods.append("	 */\n");
            methods.append("	public "+typeName+" get"+methodName+"() {\n");
            methods.append("		return this."+fieldName+";\n");
            methods.append("	}\n");
            //}
        }
        if(methodSign==0 || methodSign==2){
            methods.append("	/**\n");
            methods.append("	 * "+descName+"\n");
            methods.append("	 * @param "+fieldName+"\n");
            methods.append("	 */\n");
            methods.append("	public void set"+methodName+"("+typeName+" "+fieldName+") {\n");
            methods.append("		this."+fieldName+" = "+fieldName+";\n");
            methods.append("	}\n");
        }
        if(!StringUtils.isBlank(annotation)) outStr.append("	"+annotation+"\n");
        outStr.append("	private "+typeName+" "+fieldName+(val!=null?"="+val+"":"")+";");
        //if(!isAnnotationDesc) outStr.append("//"+descName);
        outStr.append("\n");
    }

}
