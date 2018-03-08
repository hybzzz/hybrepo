package com.zz.prettifyCar.controllers;

import com.zz.demo.beans.SysInfo;
import com.zz.demo.services.SysInfoService;
import com.zz.utils.DESUtil;
import com.zz.utils.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/pro/mch/comm")
public class CommonController {
    @Autowired
    private SysInfoService sysInfoService;

    @RequestMapping("queryK")
    public String queryK() {
        return "prettifyCar/queryK";
    }

    @RequestMapping("weather")
    public ModelAndView weather() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("app_name", "mch");
        SysInfo info = sysInfoService.getInfo(map);
        return new ModelAndView("prettifyCar/weather", "info", info);
    }

    @RequestMapping("goReg")
    public String goReg() {
        return "prettifyCar/reg";
    }

    @RequestMapping("goDes")
    public String goDes() {
        return "prettifyCar/des";
    }

    @ResponseBody
    @RequestMapping("encryptToDES")
    public String encryptToDES(String key, String data, String charset) throws UnsupportedEncodingException {
        key= Hex.encodeHexStr(key.getBytes());
        String s = new String(data.getBytes("GBK"), "1".equals(charset) ? "UTF-8" : "GBK");
        s = URLDecoder.decode(s,"utf-8");
        String res = DESUtil.encryptToDES(s, key);
        return res;
    }

    @ResponseBody
    @RequestMapping("decrypt")
    public String decrypt(String key, String data) {
        key= Hex.encodeHexStr(key.getBytes());
        String res = DESUtil.decrypt(data, key);
        return res.trim();
    }


}
