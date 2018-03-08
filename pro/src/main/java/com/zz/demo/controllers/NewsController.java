package com.zz.demo.controllers;

import com.zz.demo.beans.News;
import com.zz.demo.services.NewsService;
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
@RequestMapping("/pro/news")
public class NewsController {
    @Autowired
    private NewsService newsService;
    @RequestMapping("index")
    public String index(){
        return "news";
    }
    @ResponseBody
    @RequestMapping("list")
    public Map<String, Object> queryList( RequestData requestData) {

        PageUtils page = new PageUtils();
        page.setPageIndex(requestData.getCurrentPage());
        //根据map搜索
        Map<String,Object> queryData =  requestData.getQueryData();
        page.setTotalNum(newsService.getTotalNumByConditionLike(queryData));
        List<News> list = newsService.queryPageCustomCondiLike(page,queryData);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("list", list);
        return map;
    }
    @ResponseBody
    @RequestMapping("delBatch")
    public String delBatch(@RequestParam Map<String, Object> params) {
        System.out.println(params);
        String ids = (String) params.get("ids");
        int i = newsService.deleteBatch2(ids);
        return i>0?i + "item has be delete":"network error , please contact admin";
    }

    @ResponseBody
    @RequestMapping("update")
    public String update(@RequestParam Map<String, Object> params) {
        System.out.println(params);
        int i = newsService.updateByMap(params);
        return i>0?i + "item has be update":"network error , please contact admin";
    }

    @ResponseBody
    @RequestMapping("insert")
    public String insert(@RequestParam Map<String, Object> params) {
        System.out.println(params);
        int i = newsService.insertByMap(params);
        return i>0?i + " item has be insert":"network error , please contact admin";
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(String id) {
        int recid=Integer.parseInt(id);
        News info = newsService.getInfo(recid);
        ModelAndView mv = new ModelAndView("updateNews","news",info);
        return mv;
    }

    @RequestMapping("insertNews")
    public ModelAndView insertNews() {
        ModelAndView mv = new ModelAndView("updateNews","news",new News());
        return mv;
    }
}
