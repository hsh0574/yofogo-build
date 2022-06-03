package com.yofogo.build.generator;

import com.yofogo.build.entity.dto.FormInfo;
import com.yofogo.build.entity.enums.DBFieldType;
import org.apache.commons.lang3.StringUtils;

public class BuildUtils {


    public final static String FILE_CHARSETNAME="UTF-8";
    public final static String DAO_PACKAGE_NAME =".dao";
    public static String buildFieldTypeName(DBFieldType fileType){

        if(DBFieldType.INT==fileType || DBFieldType.TINYINT==fileType || DBFieldType.SMALLINT==fileType) return "Integer";
        else if(DBFieldType.BIGINT==fileType) return "Long";
        else if(DBFieldType.DECIMAL==fileType || DBFieldType.DOUBLE==fileType) return "Double";
        else if(DBFieldType.DATE==fileType || DBFieldType.DATETIME==fileType
                || DBFieldType.TIMESTAMP==fileType) return "java.util.Date";
        else if(DBFieldType.TIME==fileType) return "java.sql.Time";
        else if(DBFieldType.BIT==fileType) return "Boolean";
        else return "String";
    }

    public static String buildTuoFengName(String name ,boolean isFirstUpper,String delPrefix){
        if(!StringUtils.isBlank(delPrefix) && name.startsWith(delPrefix)){
            return buildTuoFengName(name.substring(delPrefix.length()),isFirstUpper);
        }
        return buildTuoFengName(name,isFirstUpper);
    }

    public static String buildTuoFengName(String name ,boolean isFirstUpper){
        String[] nameStrs=name.split("_");
        String myName;
        if(nameStrs.length>1){
            myName=nameStrs[0];
            for(int i=1;i<nameStrs.length;i++) myName+=nameStrs[i].substring(0, 1).toUpperCase()+nameStrs[i].substring(1);
        }else myName=name;
        if(isFirstUpper) return myName.substring(0,1).toUpperCase()+myName.substring(1);
        return myName.substring(0,1).toLowerCase()+myName.substring(1);
    }


    public static String getEntityIdNanme(FormInfo formInfo){
        String primaryKey=formInfo.getTypes()==1?"id":formInfo.getPrimaryKeyToEntityName();
        if(primaryKey==null){
            formInfo.setPrimaryKeyToEntityName(formInfo.getPrimaryKey());
            primaryKey=BuildUtils.buildTuoFengName(formInfo.getPrimaryKey(),true);
        }
        return primaryKey;
    }
}
