package com.zz.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 2017/9/28.
 */
public class MapObjUtil {
    public static Map<String,Object > parseObjToMap(Object o )  {
        Map<String,Object> map = new HashMap<String, Object>();
        Class clazz = o.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f:fields) {
            f.setAccessible(true);
            try {
                map.put(f.getName(),f.get(o));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return  map;
    }
    public static Object parseMapToObj(Map<String,Object > map,Class clazz){
        try {
            Object o=clazz.newInstance();
            Set<String> set = map.keySet();
            for (String key : set ) {
                String methodName= "set"+key.substring(0, 1).toUpperCase() + key.substring(1);
                Method method = clazz.getDeclaredMethod(methodName,map.get(key).getClass());
                method.invoke(o,map.get(key));//o.setxxx
            }
            return o;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            System.out.println("键值错误 . 不能与实体类对应 ");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println("方法调用错误 ");
            e.printStackTrace();
        }
        return null;
    }
}
