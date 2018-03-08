package com.zz.demo.controllers;

import com.zz.demo.beans.Products;
import com.zz.demo.services.ProductService;
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
@RequestMapping("/pro/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @ResponseBody
    @RequestMapping("list")
    public Map<String, Object> queryList(RequestData requestData) {
        PageUtils page = new PageUtils();
        page.setPageIndex(requestData.getCurrentPage());
        page.setTotalNum(productService.getTotalNumByCondition(requestData.getQueryData()));
        List<Products> list = productService.queryPageCustom(page,requestData.getQueryData());
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
        int i = productService.deleteBatch2(ids);
        return i > 0?i + "rows has be delete":"network error , please contact admin";
    }

    @ResponseBody
    @RequestMapping("update")
    public String update(@RequestParam Map<String, Object> params) {
        System.out.println(params);
        int i = productService.updateByMap(params);
        return i > 0?i + "rows has be update":"network error , please contact admin";
    }
    @RequestMapping("index")
    public String index(){
        return "products";
    }
    @ResponseBody
    @RequestMapping("insert")
    public String insert(@RequestParam Map<String, Object> params) {
        System.out.println(params);
        int i = productService.insertByMap(params);
        return i > 0?i + "rows has be insert":"network error , please contact admin";
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(String id) {
        int recid=Integer.parseInt(id);
        Products info = productService.getInfo(recid);
        ModelAndView mv = new ModelAndView("updateProduct","product",info);
        return mv;
    }

    @RequestMapping("insertProduct")
    public ModelAndView insertNews() {
        ModelAndView mv = new ModelAndView("updateProduct","product",new Products());
        return mv;
    }
}
