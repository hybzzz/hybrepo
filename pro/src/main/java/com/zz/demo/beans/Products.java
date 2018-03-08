package com.zz.demo.beans;

import com.zz.utils.annotations.ColumnName;
import com.zz.utils.annotations.IdColumn;
import com.zz.utils.annotations.TableName;

@TableName(tableName = "ly_products")
public class Products {
    @ColumnName(columnName="rec_id",fieldType = "int")
    @IdColumn(idName="rec_id")
    private Integer recId;
    @ColumnName(columnName="prd_name")
    private String name;
    @ColumnName(columnName="prd_desc")
    private String description;
    @ColumnName(columnName="prd_imgs")
    private String imgs;

    public Products() {
    }

    public Products(Integer recId, String name, String description, String imgs) {
        this.recId = recId;
        this.name = name;
        this.description = description;
        this.imgs = imgs;
    }

    public Integer getRecId() {
        return recId;
    }

    public void setRecId(Integer recId) {
        this.recId = recId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }
}
