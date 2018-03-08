package com.zz.demo.beans;

import com.zz.utils.annotations.ColumnName;
import com.zz.utils.annotations.IdColumn;
import com.zz.utils.annotations.TableName;

@TableName(tableName = "ly_messages")
public class Messages {
    @ColumnName(columnName="rec_id",fieldType = "int")
    @IdColumn(idName="rec_id")
    private Integer recId;
    @ColumnName(columnName="contact_phone")
    private String contactPhone;
    @ColumnName(columnName="contact_email")
    private String contactEmail;
    @ColumnName(columnName="contact_name")
    private String contactName;
    @ColumnName(columnName="msg_content")
    private String mesContent;

    public Integer getRecId() {
        return recId;
    }

    public void setRecId(Integer recId) {
        this.recId = recId;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getMesContent() {
        return mesContent;
    }

    public void setMesContent(String mesContent) {
        this.mesContent = mesContent;
    }

    public Messages(Integer recId, String contactEmail, String contactPhone, String contactName, String mesContent) {
        this.recId = recId;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.contactName = contactName;
        this.mesContent = mesContent;
    }

    public Messages() {
    }
}
