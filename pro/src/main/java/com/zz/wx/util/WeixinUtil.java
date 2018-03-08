package com.zz.wx.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.util.EntityUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/10/11.
 */
public class WeixinUtil {

    private static String AppID = "wx7e506b15ec6e26e9";//服务号
    private static String AppID2 = "wx55a62be03e4e6d44";//测试账号
    private static String AppSecret="cbcd65bc8d485c8543bb679a44947bb9";
    private static String AppSecret2="bc86e9872450a5872c8fc414d7877810";
    private static String TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token";
    private static String GrantType="client_credential";


    public static String getToken(){
        String access_token="";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        InputStream is = null;
        //封装请求参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("grant_type", GrantType));
        params.add(new BasicNameValuePair("appid", AppID2));
        params.add(new BasicNameValuePair("secret", AppSecret2));
        String str = "";
        try {
            str = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
            System.out.println(str);
            HttpGet httpGet = new HttpGet(TOKEN_URL+"?"+str);
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            JSONObject object = (JSONObject) JSON.parse(result);
            access_token  = (String) object.get("access_token");
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //关闭相应 丢弃http连接
            if(httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return access_token;
    }

    public static JSONObject httpRequest(String url, String json) {
        JSONObject returnValue = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try{
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            StringEntity requestEntity = new StringEntity(json,"utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(requestEntity);
            String returnStr = httpClient.execute(httpPost,responseHandler); //调接口获取返回值时，必须用此方法
            returnValue = (JSONObject) JSON.parse(returnStr);
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //第五步：处理返回值
        return returnValue;
    }
}
