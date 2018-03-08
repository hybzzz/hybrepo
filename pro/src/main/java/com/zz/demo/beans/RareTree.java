package com.zz.demo.beans;

import com.zz.utils.annotations.ColumnName;
import com.zz.utils.annotations.IdColumn;
import com.zz.utils.annotations.TableName;

/**
 * Created by admin on 2018/2/23.
 */
@TableName(tableName = "fafu_trees")
public class RareTree {
    @ColumnName(columnName="rec_id",fieldType = "int")
    @IdColumn(idName="rec_id")
    private String recId;
    @ColumnName(columnName="tree_name")
    private String treeName;//名字
    @ColumnName(columnName="tree_desc")
    private String treeDesc;//描述
    @ColumnName(columnName="tree_icon")
    private String treeIcon;//图标
    @ColumnName(columnName="latin_name")
    private String latinName;//拉丁文
    @ColumnName(columnName="tree_chara")
    private String treeChara;//生态学特征
    @ColumnName(columnName="live_rate")
    private String liveRate;//存活率
    @ColumnName(columnName="live_state")
    private String liveState;//生活状况
    @ColumnName(columnName="worth")
    private String worth;//价值
    @ColumnName(columnName="lng")
    private String lng;//经度
    @ColumnName(columnName="lat")
    private String lat;//纬度
    private String distance;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getRecId() {
        return recId;
    }

    public void setRecId(String recId) {
        this.recId = recId;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public String getTreeDesc() {
        return treeDesc;
    }

    public void setTreeDesc(String treeDesc) {
        this.treeDesc = treeDesc;
    }

    public String getTreeIcon() {
        return treeIcon;
    }

    public void setTreeIcon(String treeIcon) {
        this.treeIcon = treeIcon;
    }

    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    public String getTreeChara() {
        return treeChara;
    }

    public void setTreeChara(String treeChara) {
        this.treeChara = treeChara;
    }

    public String getLiveRate() {
        return liveRate;
    }

    public void setLiveRate(String liveRate) {
        this.liveRate = liveRate;
    }

    public String getLiveState() {
        return liveState;
    }

    public void setLiveState(String liveState) {
        this.liveState = liveState;
    }

    public String getWorth() {
        return worth;
    }

    public void setWorth(String worth) {
        this.worth = worth;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
