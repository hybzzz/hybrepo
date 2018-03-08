package com.zz.utils.services.impl;



import com.zz.utils.PageUtils;
import com.zz.utils.mappers.BaseMapper;
import com.zz.utils.EntityUtils;
import com.zz.utils.services.BaseService;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/7/6.
 */
public class BaseServiceImpl<T> implements BaseService<T>{

    public BaseMapper<T> getMapper(){
        return null;
    }
    private Class<T> clazz = this.getClazz();
    public int save(Object o){
        String sql = EntityUtils.getInsertSql(o);
        System.out.println("执行sql  : "+ sql);
        return this.getMapper().insert(sql);
    }
    public int update(T o) {
        String sql = EntityUtils.getUpdateSql(o);
        System.out.println(sql);
        return this.getMapper().update(sql);
    }
    public int updateBathch(List<T> objects){
        int i=0;
        for (T o : objects){
            i+=this.update(o);
        }
        return i;
    }
    public int delete(Integer id){
        String sql = EntityUtils.getDeleteSql(clazz,id);
        System.out.println("执行sql  : "+ sql);
        return this.getMapper().delete(sql);
    }
    public int deleteBatch(List<Integer> ids){
        String sql = EntityUtils.getDeleteBatchSql(clazz,ids);
        System.out.println("执行sql  : "+ sql);
        return this.getMapper().delete(sql);
    }
    public List<T> queryList(){
        String sql = EntityUtils.getListSql(clazz);
        System.out.println("执行sql  : "+ sql);
        return this.getMapper().getList(sql);
    }
    public T getInfo(Integer id){

        String sql = EntityUtils.getQueryInfoSql(clazz,id);
        System.out.println("执行sql  : "+ sql);
        return this.getMapper().getInfo(sql);
    }
    public T getInfo(Map<String,Object> map){

        String sql = EntityUtils.getQuerySqlByMap(clazz,map);
        System.out.println("执行sql  : "+ sql);
        return this.getMapper().getInfo(sql);
    }
    public  Class<T>  getClazz()
    {
        Type t = this.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) t).getActualTypeArguments();
        Class<T> cls = (Class<T>) params[0];
        System.out.println("获取实体类:"+cls.getName());
        return cls;
    }
    public List<T> getPage(PageUtils page){
        String pageSql = EntityUtils.getPageInfoSql(page,clazz);
        System.out.println("执行sql  : "+ pageSql);
        return getMapper().getList(pageSql);
    }
    public  int getTotalNum(){
        String sql = EntityUtils.getTotalNumSql(clazz);
        return getMapper().getTotalNum(sql);
    }
    public  int getTotalNumByCondition(String wheresql){
        String sql = EntityUtils.getTotalNumSql(clazz);
        return getMapper().getTotalNum(sql+" "+wheresql);
    }
    public  int getTotalNumByCondition(Map<String,Object> map){
        String sql = EntityUtils.getTotalNumSql(clazz,map);
        return getMapper().getTotalNum(sql);
    }

    public int deleteBatch2(String ids){
        String sql = EntityUtils.getDeleteBatchSql2(clazz,ids);
        System.out.println("执行sql  : "+ sql);
        return this.getMapper().delete(sql);
    }

    public List<T> getPageByCustomCondition(PageUtils page,String wheresql){
        String pageSql = EntityUtils.getPageByCustomCondition(page,clazz,wheresql);
        System.out.println("执行sql  : "+ pageSql);
        return getMapper().getList(pageSql);
    }
    public List<T> queryCustom(String sql){
        System.out.println("执行sql  : "+ sql);
        return this.getMapper().getList(sql);
    }
    public List<T> queryPageCustom(PageUtils page, T t){
        String sql = EntityUtils.getQueryPageByObj(page,t);
        System.out.println("执行sql :"+sql);
        return this.getMapper().getList(sql);
    }
    public List<T> queryPageCustom(PageUtils page, Map<String,Object> map){
        String sql = EntityUtils.getQueryPageByMap(page,map,clazz);
        System.out.println("执行sql :"+sql);
        return this.getMapper().getList(sql);
    }
    public int updateByMap(Map<String,Object> map){
        String sql = EntityUtils.getUpdateByMap(clazz, map);
        System.out.println("执行sql :"+sql);
        return this.getMapper().update(sql);
    }

    public int insertByMap(Map<String,Object> map){
        String sql = EntityUtils.getInsertByMap(clazz, map);
        System.out.println("执行sql :"+sql);
        return this.getMapper().insert(sql);
    }
    public List<T> queryPageCustomCondiLike(PageUtils page, Map<String,Object> map){
        String sql = EntityUtils.getQueryPageByMapLike(page,map,clazz);
        System.out.println("最终执行sql :"+sql);
        return this.getMapper().getList(sql);
    }
    public int getTotalNumByConditionLike( Map<String,Object> map){
        String sql = EntityUtils.getTotalNumLikeSql(clazz,map);
        System.out.println("最终执行sql :"+sql);
        return this.getMapper().getTotalNum(sql);
    }
}
