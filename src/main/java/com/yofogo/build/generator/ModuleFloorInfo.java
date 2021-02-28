package com.yofogo.build.generator;

public class ModuleFloorInfo {

    private String suffix;
    private String pageName;

    public ModuleFloorInfo(){}
    public ModuleFloorInfo(String suffix, String pageName) {
        this.suffix = suffix;
        this.pageName = pageName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
}
