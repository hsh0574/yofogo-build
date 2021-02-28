package com.yofogo.build.entity.enums;

import com.alibaba.fastjson.JSONArray;

public enum FormFieldType {
    TEXT("TEXT","TEXT"),
    SELECT("SELECT","SELECT"),
    CHECKBOX("CHECKBOX","CHECKBOX"),
    RADIO("RADIO","RADIO"),
    IMAGE("IMAGE","IMAGE"),
    DATE("DATE","DATE"),
    MONEY("MONEY","MONEY"),
    NUMBER("NUMBER","NUMBER"),
    DOUBLE("DOUBLE","DOUBLE"),
    DATETIME("DATETIME","DATETIME"),
    FILE("FILE","FILE"),
    TEXTAREA("TEXTAREA","TEXTAREA"),
    PASSWORD("PASSWORD","PASSWORD"),
    URL("URL","URL"),
    EDITOR("EDITOR","EDITOR");

    private final String value;
    private final String name;
    FormFieldType(String name,String value) {
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
        return JSONArray.toJSONString(FormFieldType.values());
    }

}
