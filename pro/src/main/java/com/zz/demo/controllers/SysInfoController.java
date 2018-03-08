package com.zz.demo.controllers;

import com.zz.demo.beans.SysInfo;
import com.zz.demo.services.SysInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/fafu/sys")
public class SysInfoController {
    @Autowired
    private SysInfoService sysInfoService;

    @RequestMapping("goIndex")
    public ModelAndView goIndex(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("app_name","fafu");
        SysInfo info = sysInfoService.getInfo(map);
        return new ModelAndView("index","info",info);
    }
    @ResponseBody
    @RequestMapping("init_app")
    public Map<String,Object> init_app(@RequestParam Map<String,Object> params){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("APP",new HashMap<String,Object>());
        Map<String,Object> web = new HashMap<String,Object>();
        web.put("homePage","http://47.95.202.173:43234/fafu/sys/goIndex.do");
        web.put("isSwipeRefresh","N");
        map.put("WEB",web);
        return web;
    }
}
