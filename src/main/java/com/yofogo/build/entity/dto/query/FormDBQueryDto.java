package com.yofogo.build.entity.dto.query;

public class FormDBQueryDto extends AbsFormQueryDto {
    private String dbName;//库名称
    private String tableName;//数据库表名
    private String tableDesc;//表名对应的描述（用于菜单，列表名称）

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }
}
