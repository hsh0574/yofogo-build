package com.yofogo.build.entity.enums;

public enum BuildConmand {
    ENTITY("entity"),
    DAO("dao"),
    BIZ("biz");
    private String packagName;
    private BuildConmand(String packagName){
        this.packagName=packagName;
    }
    public String getPackagName() {
        return packagName;
    }
}
