package com.zz.wx.message.resp;
import com.zz.wx.bean.Music;
/**
 * Created by admin on 2017/10/10.
 */
public class MusicMessage extends BaseMessage{
    /**
     * 音乐
     */
    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        Music = music;
    }
}
