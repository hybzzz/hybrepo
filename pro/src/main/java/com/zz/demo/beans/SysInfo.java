package com.zz.demo.beans;

import com.zz.utils.annotations.ColumnName;
import com.zz.utils.annotations.IdColumn;
import com.zz.utils.annotations.TableName;

@TableName(tableName = "sys_info")
public class SysInfo {
    @ColumnName(columnName="rec_id",fieldType = "int")
    @IdColumn(idName="rec_id")
    private Integer recId;
    @ColumnName(columnName="app_ak")
    private String appAk;
    @ColumnName(columnName="app_name")
    private String appName;
    @ColumnName(columnName="app_version")
    private String appVersion;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public SysInfo() {
    }

    public Integer getRecId() {
        return recId;
    }

    public void setRecId(Integer recId) {
        this.recId = recId;
    }

    public String getAppAk() {
        return appAk;
    }

    public void setAppAk(String appAk) {
        this.appAk = appAk;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
