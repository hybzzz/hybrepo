package com.zz.utils;

public class PageUtils {
    private int pageSize=18;
    private int pageIndex=0;
    private int totalNum;
    private int totalPage;
    private int fromIndex;
    public PageUtils() {
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        //有余数
        if(this.totalNum%this.pageSize!=0||this.totalNum==0) this.totalPage=this.totalNum/this.pageSize +1;
        else this.totalPage=this.totalNum/this.pageSize;
        this.fromIndex=(pageIndex-1)*pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public void setFromIndex(int fromIndex) {
        this.fromIndex = fromIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
        this.fromIndex = (pageIndex-1)*pageSize;

    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
        if(this.totalNum%this.pageSize!=0||this.totalNum==0) this.totalPage=this.totalNum/this.pageSize +1;
        else this.totalPage=this.totalNum/this.pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public PageUtils(int pageSize, int pageIndex, int totalNum, int totalPage) {
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.totalNum = totalNum;
        this.totalPage = totalPage;
    }
}
