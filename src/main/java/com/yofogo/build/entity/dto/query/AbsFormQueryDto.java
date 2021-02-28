package com.yofogo.build.entity.dto.query;

import java.io.Serializable;

public abstract class AbsFormQueryDto implements Serializable {

    private String dbSource;// 数据源名称

    public String getDbSource() {
        return dbSource;
    }
    public void setDbSource(String dbSource) {
        this.dbSource = dbSource;
    }

}
