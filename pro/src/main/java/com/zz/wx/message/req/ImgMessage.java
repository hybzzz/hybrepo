package com.zz.wx.message.req;

/**
 * Created by admin on 2017/10/10.
 */
public class ImgMessage extends BaseMessage{
    private String PicUrl;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        this.PicUrl = picUrl;
    }
}
