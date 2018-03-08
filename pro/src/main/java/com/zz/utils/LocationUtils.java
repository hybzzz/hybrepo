package com.zz.utils;

import com.zz.utils.bean.Point;

/**
 * Created by admin on 2018/2/23.
 */
public class LocationUtils {
    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 通过经纬度获取距离(单位：米)
     */
    public static double getDistance(Point p1, Point p2) {

        double radLat1 = rad(Double.parseDouble(p1.getX()));
        double radLat2 = rad(Double.parseDouble(p2.getX()));
        double a = radLat1 - radLat2;
        double b = rad(Double.parseDouble(p1.getY())) - rad(Double.parseDouble(p2.getY()));
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 1000);
        return s;
    }


    private final static double PI = 3.14159265358979323; // 圆周率
    private final static double R = 6371229; // 地球的半径

    public static double getDistance1(Point p1, Point p2) {

        double radLat1 = rad(Double.parseDouble(p1.getX()));
        double radLat2 = rad(Double.parseDouble(p2.getX()));
        double a = radLat1 - radLat2;
        double b = rad(Double.parseDouble(p1.getY())) - rad(Double.parseDouble(p2.getY()));
        double s = 2 * Math.asin(
                Math.sqrt(
                        Math.pow(Math.sin(a/2),2)
                                + Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)
                )
        );
        s = s * 6378137.0;
        s = Math.round(s * 10000) / 10000;
        return s;


    }

//    public static void main(String[] args) {
//        Point p1= new Point("119.256361","26.104883");
//        Point p2= new Point("119.356277","26.104964");
//        System.out.println(getDistance1(p1,p2));
//    }



    public static double getDistance2(Point p1, Point p2){
        double radLat1 = Math.toRadians(Double.parseDouble(p1.getY()));
        double radLat2 = Math.toRadians(Double.parseDouble(p2.getY()));
        double a = radLat1 - radLat2;
        double b = Math.toRadians(Double.parseDouble(p1.getX()) - Math.toRadians(Double.parseDouble(p2.getX())));
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
                * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
        s = Math.round(s * 10000) / 10000;
        return s;
    }
//    public static void main(String[] args) {
//        Point p = new Point("39.90816", "116.4767");
//        Point p2 = new Point("39.90815", "116.4505");
//        System.out.println(getDistance(p,p2));
//        System.out.println(getDistance(39.90816, 116.4767, 39.90815, 116.4505));
//    }


}