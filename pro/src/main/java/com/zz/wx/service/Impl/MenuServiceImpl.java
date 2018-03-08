package com.zz.wx.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.zz.wx.service.MenuService;
import com.zz.wx.util.HttpUtil;
import com.zz.wx.util.WeixinUtil;
import org.apache.log4j.Logger;

/**
 * Created by admin on 2017/10/11.
 */
public class MenuServiceImpl implements MenuService {
    public static Logger log = Logger.getLogger(MenuServiceImpl.class);

    public static String MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";


    @Override
    public String createMenu(String jsonMenu) {
        String resultStr = "";
        // 调用接口获取token
        String token = WeixinUtil.getToken();
        if (token != null) {
            // 调用接口创建菜单
            int result = createMenu(jsonMenu, token);
            // 判断菜单创建结果
            if (0 == result) {
                resultStr = "菜单创建成功";
                log.info(resultStr);
            } else {
                resultStr = "菜单创建失败，错误码：" + result;
                log.error(resultStr);
            }
        }

        return resultStr;
    }

    /**
     * 创建菜单
     *
     * @param jsonMenu    菜单的json格式
     * @param accessToken 有效的access_token
     * @return 0表示成功，其他值表示失败
     */
    public static int createMenu(String jsonMenu, String accessToken) {

        int result = 0;
        // 拼装创建菜单的url
        String url = MENU_CREATE.replace("ACCESS_TOKEN", accessToken);
        // 调用接口创建菜单
        JSONObject jsonObject = WeixinUtil.httpRequest(url, jsonMenu);
        if (null != jsonObject) {
            if (0 != jsonObject.getInteger("errcode")) {
                result = jsonObject.getInteger("errcode");
                log.error("创建菜单失败 errcode:" + jsonObject.getInteger("errcode")
                        + "，errmsg:" + jsonObject.getString("errmsg"));
            }
        }
        return result;
    }//

//    public static void main(String[] args) {
//        //snsapi_login
//        String jsonMenu = "{\"button\":[{\"name\":\"生活助手\",\"sub_button\":[{\"name\":\"天气查询\",\"type\":\"view\",\"url\":\"http://smallminya.zykus.top/pro/mch/comm/weather.do\"},{\"name\":\"快递查询\",\"type\":\"view\",\"url\":\"http://smallminya.zykus.top/pro/mch/comm/queryK.do\"}]}," +
//                "{\"name\":\"会员中心\",\"sub_button\":[{\"name\":\"会员注册\",\"type\":\"view\",\"url\":\"http://smallminya.zykus.top/pro/mch/comm/goReg.do\"},{\"name\":\"会员充值\",\"type\":\"view\",\"url\":\"http://smallminya.zykus.top/pro/mch/comm/queryK.do\"},{\"name\":\"消费记录\",\"type\":\"view\",\"url\":\"http://smallminya.zykus.top/pro/mch/comm/queryK.do\"}]}]}";
//        MenuServiceImpl impl = new MenuServiceImpl();
//        impl.createMenu(jsonMenu);
//    }

    public static String getOpenId(String code){
        if(code!=null&&code!=""){
            String appid = "wx55a62be03e4e6d44";
            String secret = "bc86e9872450a5872c8fc414d7877810";
            String result = HttpUtil.doGet("https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code", null, "UTF-8", true);
            if(result!=null&&result!=""){
                JSONObject json = JSONObject.parseObject(result);
                if(json.get("openid")!=null){
                    return json.get("openid").toString();
                }
            }
        }
        return "";
    }
}
