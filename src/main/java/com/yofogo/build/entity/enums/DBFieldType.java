package com.yofogo.build.entity.enums;

import com.alibaba.fastjson.JSONArray;

public enum DBFieldType {
    VARCHAR("VARCHAR","VARCHAR"),
    INT("INT","INT"),
    DATETIME("DATETIME","DATETIME"),
    TIMESTAMP("TIMESTAMP","TIMESTAMP"),
    DECIMAL("DECIMAL","DECIMAL"),
    DATE("DATE","DATE"),
    DOUBLE("DOUBLE","DOUBLE"),
    CHAR("CHAR","CHAR"),
    TEXT("TEXT","TEXT"),
    SMALLINT("SMALLINT","SMALLINT"),
    TINYINT("TINYINT","TINYINT"),
    BIGINT("BIGINT","BIGINT");

    private final String value;
    private final String name;
    DBFieldType(String name,String value) {
        this.value = value;

        this.name = name;
    }
    public String getValue() {
        return value;
    }
    public String getName() {
        return name;
    }

    public static String toJson(){
        return JSONArray.toJSONString(DBFieldType.values());
    }

    public FormFieldType toEleFieldType(){
        if(this==DBFieldType.DATETIME || this==DBFieldType.TIMESTAMP ) return FormFieldType.DATETIME;
        else if(this==DBFieldType.DATE) return FormFieldType.DATE;
        else return FormFieldType.TEXT;
    }

}
