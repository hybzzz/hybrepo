package com.zz.demo.beans;

import com.zz.utils.annotations.ColumnName;
import com.zz.utils.annotations.IdColumn;
import com.zz.utils.annotations.TableName;

@TableName(tableName = "ly_news")
public class News {
    @ColumnName(columnName="rec_id",fieldType = "int")
    @IdColumn(idName="rec_id")
    private Integer recId;
    @ColumnName(columnName="news_title")
    private String title;
    @ColumnName(columnName="news_desc")
    private String description;
    @ColumnName(columnName="news_content")
    private String content;
    @ColumnName(columnName="news_agree",fieldType = "int")
    private Integer agree;
    @ColumnName(columnName="news_disagree",fieldType = "int")
    private Integer disagree;

    public News() {
    }

    public News(Integer recId, String title, String description, String content, Integer agree, Integer disagree) {
        this.recId = recId;
        this.title = title;
        this.description = description;
        this.content = content;
        this.agree = agree;
        this.disagree = disagree;
    }

    public Integer getRecId() {
        return recId;
    }

    public void setRecId(Integer recId) {
        this.recId = recId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAgree() {
        return agree;
    }

    public void setAgree(Integer agree) {
        this.agree = agree;
    }

    public Integer getDisagree() {
        return disagree;
    }

    public void setDisagree(Integer disagree) {
        this.disagree = disagree;
    }
}
