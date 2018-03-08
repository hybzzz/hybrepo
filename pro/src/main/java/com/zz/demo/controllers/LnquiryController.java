package com.zz.demo.controllers;

import com.zz.demo.beans.Lnquiry;
import com.zz.demo.services.LnquiryService;
import com.zz.utils.EntityUtils;
import com.zz.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pro/lnquiry")
public class LnquiryController {
    @Autowired
    private LnquiryService lnquiryService;

    @RequestMapping("list")
    public Map<String, Object> queryList(@RequestParam Map<String, Object> params) {
        System.out.println(params);
        PageUtils page = new PageUtils();
        String keyword = (String) params.get("keyword");
        page.setPageIndex(Integer.parseInt((String) params.get("currentPage")));
        String wheresql="where CONCAT(rec_id,Inquiry_extra,lnquiry_status,Inquiry_prd,contact_email,contact_person,contact_phone) like '%" + keyword + "%'";
        page.setTotalNum(lnquiryService.getTotalNumByCondition(wheresql));
        String sql = EntityUtils.getPageInfoSql(page, Lnquiry.class);
        List<Lnquiry> list;
        if (keyword != null && keyword.trim() != "") {
            list = lnquiryService.queryCustom(sql.replace("limit", wheresql+" limit "));
        }else{
            list = lnquiryService.queryCustom(sql);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("list", list);
        return map;
    }

    @RequestMapping("delBatch")
    public String delBatch(@RequestParam Map<String, Object> params) {
        String ids = (String) params.get("ids");
        System.out.println(params);
        int i = lnquiryService.deleteBatch2(ids);
        if (i > 0) {
            return i + "rows has be delete";
        }
        return "neterror , please contact admin";
    }
    @RequestMapping("doneBatch")
    public String doneBatch(@RequestParam Map<String, Object> params) {
        String ids = (String) params.get("ids");
        String[] strs = ids.split(",");
        List<Lnquiry> ls = new ArrayList<Lnquiry>();
        for (String str : strs) {
            Lnquiry l = new Lnquiry();
            l.setRecId(Integer.parseInt(str));
            l.setLnquiryStatus("done");
            ls.add(l);
        }
        int i = lnquiryService.updateBathch(ls);
        if (i > 0) {
            return i + "rows has be updated";
        }
        return "neterror , please contact admin";
    }
}
