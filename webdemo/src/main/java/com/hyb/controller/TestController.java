package com.hyb.controller;

import com.hyb.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2018/4/2.
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;
    @RequestMapping("/test")
    public List<HashMap<String,String>> getNum(String user){
        return testService.getMenus(user);
    }
    @RequestMapping("/test2")
    public String getNum2(){
        return testService.getNum2()+"";
    }
}
