package com.zz.demo.beans;

import com.zz.utils.annotations.ColumnName;
import com.zz.utils.annotations.IdColumn;
import com.zz.utils.annotations.SearchByLike;
import com.zz.utils.annotations.TableName;

@TableName(tableName="ly_lnquiry")
public class Lnquiry {

    @ColumnName(columnName="rec_id",fieldType = "int")
    @IdColumn(idName="rec_id")
    private Integer recId;
    @ColumnName(columnName="Inquiry_prd")
    private String lnquiryPrd;
    @ColumnName(columnName="contact_person")
    private String contacter;
    @ColumnName(columnName="contact_phone")
    private String contactPhone;
    @SearchByLike
    @ColumnName(columnName="contact_email")
    private String contactEmail;
    @SearchByLike(likeWay = "left")
    @ColumnName(columnName="Inquiry_extra")
    private String lnquiryExtra;
    @ColumnName(columnName="lnquiry_status")
    private String lnquiryStatus;

    public String getLnquiryStatus() {
        return lnquiryStatus;
    }

    public void setLnquiryStatus(String lnquiryStatus) {
        this.lnquiryStatus = lnquiryStatus;
    }

    public Lnquiry(Integer recId, String lnquiryPrd, String contacter, String contactPhone, String contactEmail, String lnquiryExtra, String lnquiryStatus) {
        this.recId = recId;
        this.lnquiryPrd = lnquiryPrd;
        this.contacter = contacter;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.lnquiryExtra = lnquiryExtra;
        this.lnquiryStatus = lnquiryStatus;
    }

    public Integer getRecId() {
        return recId;
    }

    public void setRecId(Integer recId) {
        this.recId = recId;
    }

    public String getLnquiryPrd() {
        return lnquiryPrd;
    }

    public void setLnquiryPrd(String lnquiryPrd) {
        this.lnquiryPrd = lnquiryPrd;
    }

    public String getContacter() {
        return contacter;
    }

    public void setContacter(String contacter) {
        this.contacter = contacter;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getLnquiryExtra() {
        return lnquiryExtra;
    }

    public void setLnquiryExtra(String lnquiryExtra) {
        this.lnquiryExtra = lnquiryExtra;
    }

    public Lnquiry() {

    }

    public Lnquiry(Integer recId, String lnquiryPrd, String contacter, String contactPhone, String contactEmail, String lnquiryExtra) {
        this.recId = recId;
        this.lnquiryPrd = lnquiryPrd;
        this.contacter = contacter;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.lnquiryExtra = lnquiryExtra;
    }
}
