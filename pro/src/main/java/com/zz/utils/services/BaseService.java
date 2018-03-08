package com.zz.utils.services;

import com.zz.utils.PageUtils;
import com.zz.utils.mappers.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/9/15.
 */
public interface BaseService<T> {
    BaseMapper<T> getMapper();
    int save(Object o);
    int update(T o);
    int updateBathch(List<T> objects);
    int delete(Integer id);
    int deleteBatch(List<Integer> ids);
    List<T> queryList();
    T getInfo(Integer id);
    T getInfo(Map<String,Object> map);
    List<T> getPage(PageUtils page);
    int getTotalNum();
    int getTotalNumByCondition(String wheresql);
    int getTotalNumByCondition(Map<String,Object> map);
    int deleteBatch2(String ids);
    List<T> getPageByCustomCondition(PageUtils page,String wheresql);
    List<T> queryCustom(String sql);
    List<T> queryPageCustom(PageUtils page, T t);
    List<T> queryPageCustom(PageUtils page, Map<String,Object> map);
    int updateByMap(Map<String,Object> map);
    int insertByMap(Map<String,Object> map);
    int getTotalNumByConditionLike( Map<String,Object> map);
    List<T> queryPageCustomCondiLike(PageUtils page, Map<String,Object> map);
}
