package com.zz.demo.controllers;

import com.zz.demo.beans.RareTree;
import com.zz.demo.services.RareTreeService;
import com.zz.utils.LocationUtils;
import com.zz.utils.bean.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/2/23.
 */
@Controller
@RequestMapping("/fafu/rareTree")
public class RareTreeController {
    @Autowired
    private RareTreeService service;
    @ResponseBody
    @RequestMapping("getData")
    public List<RareTree> getData(){
        List<RareTree> trees = service.queryList();
        return trees;
    }
    @ResponseBody
    @RequestMapping("getNearData")
    public List<RareTree> getNearData(String curLongitude,String curLatitude,String zoomLevel){
        Point p0 = new Point(curLongitude,curLatitude);
        List<RareTree> trees = service.queryList();
        List<RareTree> res = new ArrayList<RareTree>();
        Double d = getZoomDistance(zoomLevel);
        if("0".equals(zoomLevel)||d==0.00){
            for (RareTree tree : trees){
                Point p1 = new Point(tree.getLng(),tree.getLat());
                Double dis = LocationUtils.getDistance(p0,p1);
                tree.setDistance(String.valueOf(dis));
            }
            return trees;
        }else{
            for (RareTree tree:trees) {
                Point p1 = new Point(tree.getLng(),tree.getLat());
                Double dis = LocationUtils.getDistance(p0,p1);
                if(Double.compare(dis,d)==-1){
                    tree.setDistance(String.valueOf(dis));
                    res.add(tree);
                }
            }
            return res;
        }

    }

    private Double getZoomDistance(String zoomLevel) {
        if("15".equals(zoomLevel)){
            return 500.00;
        }else if ("14".equals(zoomLevel)){
            return 1000.00;
        }else if ("13".equals(zoomLevel)){
            return 2000.00;
        }else if ("12".equals(zoomLevel)){
            return 5000.00;
        }else if ("11".equals(zoomLevel)){
            return 10000.00;
        }else{
            return 0.00;
        }
    }
}
