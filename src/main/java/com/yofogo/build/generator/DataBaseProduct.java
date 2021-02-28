package com.yofogo.build.generator;

import cn.com.yofogo.tools.util.DateTimeUtil;
import com.yofogo.build.entity.enums.DBType;

public class DataBaseProduct {


    private final static DBType currDBType=DBType.MySQL;

    /**
     * 根据数据库类型处理字段名关键字
     * @param fieldName
     * @return
     */
    public static String getDBFieldSymbols(String fieldName){
        return getDBFieldSymbols(fieldName,currDBType);
    }
    /**
     * 根据数据库类型处理字段名关键字
     * @param fieldName
     * @param dbType
     * @return
     */
    public static String getDBFieldSymbols(String fieldName,DBType dbType){
        if(dbType==DBType.MySQL) return "`"+fieldName+"`";
        else if(dbType== DBType.MSSQL) return "["+fieldName+"]";
        else return fieldName;
    }

    /**
     * 根据数据库类型获取数据时间
     * @return
     */
    public static String getDBDateTime(){
        return getDBDateTime(currDBType);
    }
    /**
     * 根据数据库类型获取数据时间
     * @param dbType
     * @return
     */
    public static String getDBDateTime(DBType dbType){
        if(dbType==DBType.MySQL) return "NOW()";
        else if(dbType==DBType.MSSQL) return "GETDATE()";
        else return "'"+ DateTimeUtil.getCurrentDatetime("yyyy-MM-dd HH:mm:ss")+"'";
    }

}
