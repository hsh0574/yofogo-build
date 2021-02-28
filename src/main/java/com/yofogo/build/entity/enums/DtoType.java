package com.yofogo.build.entity.enums;

public enum DtoType {
    DTO_REQUEST("ReqDto",".dto.request"),
    DTO_RESPONSE("RespDto",".dto.response"),
    DTO_REQUEST_QUERY("QueryDto",".dto.request");
    private String prex;
    private String packagName;
    private DtoType(String prex,String packagName){
        this.prex=prex;
        this.packagName=packagName;
    }
    public String getPrex() {
        return prex;
    }
    public String getPackagName() {
        return packagName;
    }

}
