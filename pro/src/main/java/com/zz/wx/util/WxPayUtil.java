package com.zz.wx.util;

import org.jdom.JDOMException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by admin on 2017/12/12.
 */
public class WxPayUtil {
    @SuppressWarnings("unchecked")
    public static String unifiedorder(String body,String out_trade_no,String openid) {
        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
        parameters.put("appid", ConfigUtil.APPID);

        parameters.put("mch_id", ConfigUtil.MCH_ID);
        parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
        parameters.put("body", body);
        parameters.put("out_trade_no", out_trade_no);
        parameters.put("total_fee", "1");
        parameters.put("spbill_create_ip","113.57.246.11");
        parameters.put("notify_url", ConfigUtil.NOTIFY_URL);
        parameters.put("trade_type", "JSAPI");
        parameters.put("openid", openid);
        String sign = PayCommonUtil.createSign(parameters);
        parameters.put("sign", sign);
        String requestXML = PayCommonUtil.getRequestXml(parameters);
        System.out.println(requestXML.toString());
        String result =CommonUtil.httpsRequest(ConfigUtil.UNIFIED_ORDER_URL, "POST", requestXML);
        System.out.println(result.toString());
        Map<String, String> map=new HashMap<String, String>();
        try {
            map = XMLUtil.doXMLParse(result);
        } catch (JDOMException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }//解析微信返回的信息，以Map形式存储便于取值
        return map.get("prepay_id").toString();
    }
}
