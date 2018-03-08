package com.zz.demo.beans;

import com.zz.utils.annotations.ColumnName;
import com.zz.utils.annotations.IdColumn;
import com.zz.utils.annotations.TableName;

/**
 * Created by admin on 2017/12/4.
 */
@TableName(tableName="AppException")
public class AppException {
    @ColumnName(columnName="rec_id",fieldType = "int")
    @IdColumn(idName="rec_id")
    private Integer recId;
    @ColumnName(columnName="errorcontent")
    private String errorcontent;
    @ColumnName(columnName="dateStr")
    private String dateStr;
    @ColumnName(columnName="deviceinfo")
    private String deviceinfo;

    public Integer getRecId() {
        return recId;
    }

    public void setRecId(Integer recId) {
        this.recId = recId;
    }

    public String getErrorcontent() {
        return errorcontent;
    }

    public void setErrorcontent(String errorcontent) {
        this.errorcontent = errorcontent;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getDeviceinfo() {
        return deviceinfo;
    }

    public void setDeviceinfo(String deviceinfo) {
        this.deviceinfo = deviceinfo;
    }
}
