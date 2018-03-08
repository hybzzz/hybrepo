package com.zz.demo.controllers;

import com.zz.demo.beans.Messages;
import com.zz.demo.services.MessageService;
import com.zz.utils.PageUtils;
import com.zz.utils.bean.RequestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pro/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @ResponseBody
    @RequestMapping("list")
    public Map<String, Object> queryList(RequestData requestData) {
        PageUtils page = new PageUtils();
        page.setPageIndex(requestData.getCurrentPage());
        page.setTotalNum(messageService.getTotalNumByCondition(requestData.getQueryData()));
        List<Messages> list = messageService.queryPageCustom(page,requestData.getQueryData());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("list", list);
        return map;
    }
    @RequestMapping("index")
    public String index(){
        return "messages";
    }
    @ResponseBody
    @RequestMapping("delBatch")
    public String delBatch(@RequestParam Map<String, Object> params) {
        String ids = (String) params.get("ids");
        System.out.println(params);
        int i = messageService.deleteBatch2(ids);
        if (i > 0) {
            return i + "rows has be delete";
        }
        return "network error , please contact admin";
    }

    @ResponseBody
    @RequestMapping("update")
    public String update(@RequestParam Map<String, Object> params) {
        System.out.println(params);
        int i = messageService.updateByMap(params);
        if (i > 0) {
            return i + "rows has be update";
        }
        return "network error , please contact admin";
    }
    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(String id) {
        int recid=Integer.parseInt(id);
        Messages info = messageService.getInfo(recid);
        ModelAndView mv = new ModelAndView("updateMessage","message",info);
        return mv;
    }
}
