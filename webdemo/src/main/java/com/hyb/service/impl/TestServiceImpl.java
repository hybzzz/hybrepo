package com.hyb.service.impl;

import com.hyb.dao.Test2Dao;
import com.hyb.dao.TestDao;
import com.hyb.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2018/4/2.
 */
@Service("testService")
public class TestServiceImpl implements TestService {
    @Autowired
    private TestDao testDao;
    @Autowired
    private Test2Dao test2Dao;
    public List<HashMap<String,String>>  getMenus(String user) {

        return testDao.getMenus(user);
    }
    public int getNum2() {
        return test2Dao.getNum();
    }
}
