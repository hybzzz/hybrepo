package com.zz.demo.controllers;

import com.zz.demo.beans.AppException;
import com.zz.demo.services.AppExceptionService;
import com.zz.utils.PageUtils;
import com.zz.utils.bean.RequestData;
import com.zz.utils.bean.RespData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/12/4.
 */
@Controller
@RequestMapping("/pro/hzl")
public class HzlController {
    @Autowired
    private AppExceptionService appExceptionService;
    @ResponseBody
    @RequestMapping("exceptionHandler")
    public RespData testexceptionHandler(@RequestBody AppException appException) {
        System.out.println(appException);
        int save = appExceptionService.save(appException);
        RespData respData = new RespData();
        respData.setCode(save>0?200:9999);
        respData.setMsg(save>0?"success":"error");
        respData.setDesc("");
        return respData;
    }
    @ResponseBody
    @RequestMapping("list")
    public Map<String, Object> queryList(RequestData requestData) {
        PageUtils page = new PageUtils();
        page.setPageIndex(requestData.getCurrentPage());
        page.setTotalNum(appExceptionService.getTotalNumByCondition(requestData.getQueryData()));
        List<AppException> list = appExceptionService.queryPageCustom(page,requestData.getQueryData());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("list", list);
        return map;
    }
    @RequestMapping("index")
    public String index(){
        return "appExp";
    }

    @ResponseBody
    @RequestMapping("delBatch")
    public String delBatch(@RequestParam Map<String, Object> params) {
        String ids = (String) params.get("ids");
        int i = appExceptionService.deleteBatch2(ids);
        if (i > 0) {
            return i + "rows has be delete";
        }
        return "network error , please contact admin";
    }
}
