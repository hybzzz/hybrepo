package com.hyb.util;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 2017/12/25.
 */
public class Utils {
    private static long seqNum = 19204736;
    private static synchronized long getSeqNum(){
        seqNum+=31;
        if(seqNum==100000000){
            seqNum=12345678;
        }
        return seqNum;
    }
    public static String getUniqueNo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date())+String.valueOf(getSeqNum());
    }

}
