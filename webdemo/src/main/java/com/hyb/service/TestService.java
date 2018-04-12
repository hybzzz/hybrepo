package com.hyb.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2018/4/2.
 */

public interface TestService {
    List<HashMap<String,String>> getMenus(String user);
    int getNum2();
}
