package com.zz.utils;


import com.zz.utils.annotations.ColumnName;
import com.zz.utils.annotations.IdColumn;
import com.zz.utils.annotations.SearchByLike;
import com.zz.utils.annotations.TableName;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by admin on 2017/7/6.
 */
public class EntityUtils {

    private static Map<String,Object> values ;//属性 : 值
    private static Map<String,String> columns ;//属性 : 字段
    private static Map<String,String> fullColumns ;
    private static Map<String,String> typeList ;//属性 : 类型
    private static List<String> fieldList ;
    private static List<String> fullFieldList ;
    private static String idColumn;
    private static String tableName;
    private static Integer id;
    private static Map<String,Boolean> useLikeSearch;
    private static Map<String,String> likeWays;
    private EntityUtils() {

    }
    //根据对象生成更新sql  只更新有值的字段
    public static String getUpdateSql(Object o) {
        if (o == null) {
            return "";
        }
        getTableName(o.getClass());
        StringBuffer sql = new StringBuffer("UPDATE " + tableName + " SET ");
        getColumns(o);
        for (int i = 0; i < fieldList.size(); i++) {
            if (i == 0) {
                sql.append(columns.get(fieldList.get(i)) + "=" + getValSql(fieldList.get(i)));
            } else {
                sql.append("," + columns.get(fieldList.get(i)) + "=" + getValSql(fieldList.get(i)));
            }
        }
        sql.append(" WHERE " + idColumn + " = " + id);
        return sql.toString();
    }
    //根据对象生成插入sql 只插入有值的字段
    public static String getInsertSql(Object o) {
        if (id != null) {
            throw new RuntimeException("id已存在 不能进行插入操作");
        }
        getTableName(o.getClass());
        getColumns(o);
        StringBuffer sql = new StringBuffer("INSERT INTO " + tableName + "( ");
        for (int i = 0; i < fieldList.size(); i++) {
            if (i == 0) {
                sql.append(columns.get(fieldList.get(i)));
            } else {
                sql.append("," + columns.get(fieldList.get(i)));
            }
        }
        sql.append(") VALUES (");
        for (int i = 0; i < fieldList.size(); i++) {
            if (i == 0) {
                sql.append(getValSql(fieldList.get(i)));
            } else {
                sql.append("," + getValSql(fieldList.get(i)));
            }
        }
        sql.append(")");
        return sql.toString();
    }
    //获取对象属性对应的字段以及属性的值 没有则不获取
    //修改 插入 的时候需要
    private static void getColumns(Object o) {
        columns= new HashMap<String, String>();
        values=new HashMap<String, Object>();
        typeList=new HashMap<String, String>();
        useLikeSearch=new HashMap<String, Boolean>();
        likeWays=new HashMap<String, String>();
        fieldList= new ArrayList<String>();
        Class clazz = o.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(IdColumn.class)) {
                IdColumn idAnno = field.getAnnotation(IdColumn.class);
                idColumn = idAnno.idName();
                try {
                    id = (Integer) field.get(o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            ColumnName column = field.getAnnotation(ColumnName.class);
            String columnName = column.columnName();
            try {
                if (field.get(o) != null) {
                    //获取注解searchlike
                    SearchByLike searchByLike =field.getAnnotation(SearchByLike.class);
                    boolean searchFlag=searchByLike==null?false:true;
                    String searchWay=searchByLike==null?"":searchByLike.likeWay();
                    String fName=field.getName();
                    fieldList.add(fName);
                    columns.put(fName,columnName);
                    values.put(fName,field.get(o));
                    typeList.put(fName,column.fieldType());
                    likeWays.put(columnName,searchWay);
                    useLikeSearch.put(columnName,searchFlag);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    //获取全部字段 以及属性 主要用于查询
    private static void getFullColumns(Class clazz) {
        useLikeSearch=new HashMap<String, Boolean>();
        likeWays=new HashMap<String, String>();
        fullColumns=new HashMap<String, String>();
        fullFieldList= new ArrayList<String>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            ColumnName column = field.getAnnotation(ColumnName.class);
            if(column!=null){
                //注解上的字段名
                String columnName = column.columnName();
                //属性名
                String filedName = field.getName();
                //获取注解searchlike
                SearchByLike searchByLike =field.getAnnotation(SearchByLike.class);
                boolean searchFlag=searchByLike==null?false:true;
                String searchWay=searchByLike==null?"":searchByLike.likeWay();
                fullColumns.put(filedName,columnName);
                fullFieldList.add(filedName);
                likeWays.put(columnName,searchWay);
                useLikeSearch.put(columnName,searchFlag);
            }
        }
    }
    //获取表名
    private static void getTableName(Class clazz) {
        if (clazz.isAnnotationPresent(TableName.class)) {
            TableName tableNameAnno = (TableName) clazz.getAnnotation(TableName.class);
            tableName = tableNameAnno.tableName();
        } else {
            throw new RuntimeException("无法获取表名信息 请检查实体类上注解");
        }
    }
    //获取属性值
    private static String getValSql(String fName) {
        if ("String".equals(typeList.get(fName))) {
            return "'" + values.get(fName) + "'";
        } else if ("int".equals(typeList.get(fName))) {
            return "" + values.get(fName);
        } else if ("double".equals(typeList.get(fName))) {
            return "" + values.get(fName);
        } else {
            throw new RuntimeException("不可识别的类型");
        }
    }
    //获取到主键信息
    private static void getIdColumn(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(IdColumn.class)) {
                IdColumn idAnno = field.getAnnotation(IdColumn.class);
                idColumn = idAnno.idName();
                return;
            }
        }
    }
    //生成根据主键删除sql
    public static String getDeleteSql(Class clazz, Integer id) {
        getIdColumn(clazz);
        getTableName(clazz);
        StringBuffer sql = new StringBuffer("DELETE FROM ");
        sql.append(tableName);
        sql.append(" WHERE ");
        sql.append(idColumn);
        sql.append(" = " + id);
        return sql.toString();
    }
    //生成查询全表sql
    public static String getListSql(Class clazz) {
        getTableName(clazz);
        getFullColumns(clazz);
        StringBuffer sql = new StringBuffer("SELECT ");
        for (String f : fullFieldList ) {
            sql.append(fullColumns.get(f));
            sql.append(" as "+f);
            sql.append(",");
        }
        sql.replace(sql.length()-1,sql.length(),"");
        sql.append(" from ");
        sql.append(tableName);
        return sql.toString();
    }
    //根据id集合 生成批量删除sql
    public static String getDeleteBatchSql(Class clazz, List<Integer> ids) {
        getIdColumn(clazz);
        getTableName(clazz);
        StringBuffer sql = new StringBuffer("DELETE FROM ");
        sql.append(tableName);
        sql.append("  WHERE ");
        sql.append(idColumn);
        sql.append(" IN ( ");
        for (int i = 0; i < ids.size(); i++) {
            if (i == 0) {
                sql.append(ids.get(i));
            } else {
                sql.append("," + ids.get(i));
            }
        }
        sql.append(")");
        return sql.toString();
    }
    //根据拼接好的ids生成批量删除sql
    public static String getDeleteBatchSql2(Class clazz, String ids) {
        getIdColumn(clazz);
        getTableName(clazz);
        StringBuffer sql = new StringBuffer("DELETE FROM ");
        sql.append(tableName);
        sql.append("  WHERE ");
        sql.append(idColumn);
        sql.append(" IN ( ");
        sql.append(ids);
        sql.append(")");
        return sql.toString();
    }
    //生成根据id查询的sql
    public static String getQueryInfoSql(Class clazz, Integer id) {
        getIdColumn(clazz);
        getFullColumns(clazz);
        getTableName(clazz);
        StringBuffer sql = new StringBuffer("SELECT  ");
        for (String f : fullFieldList ) {
            sql.append(fullColumns.get(f));
            sql.append(" as "+f);
            sql.append(",");
        }
        sql.replace(sql.length()-1,sql.length(),"");
        sql.append(" from ");
        sql.append(tableName);
        sql.append("  WHERE ");
        sql.append(idColumn);
        sql.append(" = " + id);
        return sql.toString();
    }
    //得到记录数
    public static String getTotalNumSql(Class clazz){
        getTableName(clazz);
        StringBuffer sql = new StringBuffer("SELECT  count(*) from ");
        sql.append(tableName);
        return sql.toString();
    }

    //得到记录数
    public static String getTotalNumSql(Class clazz,Map<String,Object> map ){
        getFullColumns(clazz);
        getTableName(clazz);
        StringBuffer sql = new StringBuffer("SELECT count(*) from ");
        sql.append(tableName);
        if(map!=null){//map键即字段名
            Set<String> keys = map.keySet();
            Collection<Object> values = map.values();
            boolean b=false;
            for (Object o:values) {
                if(o!=""){
                    b=true;
                }
            }
            if(b){
                sql.append(" where 1=1 and");
            }
            for (String key: keys) {
                if(map.get(key)!=""){
                    try {
                        if(useLikeSearch.get(key)){//如果使用模糊查询
                            if("both".equals(likeWays.get(key))){
                                sql.append(" "+key+" like %"+map.get(key)+"% and");
                            }else if ("left".equals(likeWays.get(key))){
                                sql.append(" "+key+" like %"+map.get(key)+" and");
                            }else if ("right".equals(likeWays.get(key))){
                                sql.append(" "+key+" like "+map.get(key)+"% and");
                            }else{
                                sql.append(" "+key+"="+map.get(key)+" and");
                            }
                        }else{
                            sql.append(" "+key+"="+map.get(key)+" and");
                        }
                    }catch (NullPointerException e){
                        System.out.println("没有该属性");
                    }
                }
            }
            if(b){
                sql.replace(sql.length()-3,sql.length(),"");
            }
        }
        return sql.toString();
    }
    //得到记录数
    public static String getTotalNumLikeSql(Class clazz,Map<String,Object> map ){
        getTableName(clazz);
        StringBuffer sql = new StringBuffer("SELECT count(*) from ");
        sql.append(tableName);
        if(map!=null){
            Set<String> keys = map.keySet();
            sql.append(" where 1=1 AND");
            for (String key: keys) {
                if(map.get(key)!=""){
                    sql.append(" "+key+" like '%"+map.get(key)+"%' and");
                }
            }
            sql.replace(sql.length()-3,sql.length(),"");
        }
        return sql.toString();
    }
    //生成默认分页sql
    public static String getPageInfoSql(PageUtils page,Class clazz){
        return getListSql(clazz)+" limit "+page.getFromIndex()+","+page.getPageSize();
    }
    //根据自定义sql生成分页sql
    public static String getPageByCustomCondition(PageUtils page,Class clazz,String wheresql){
        return getListSql(clazz)+" "+wheresql+" limit "+page.getFromIndex()+","+page.getPageSize();
    }
    //根据对象 生成查询sql 带分页信息
    public static String getQueryPageByObj(PageUtils page,Object o){
        getTableName(o.getClass());
        getColumns(o);
        StringBuffer sql = new StringBuffer(getListSql(o.getClass()));
        sql.append(" where ");

        for (int i = 0; i < fieldList.size(); i++) {
            appendConditionSql(sql,columns.get(fieldList.get(i)),getValSql(fieldList.get(i)));
        }
        sql.replace(sql.length()-3,sql.length(),"");
        sql.append(" limit " + page.getFromIndex() + " , " + page.getPageSize());
        return sql.toString();
    }
    public static void appendConditionSql(StringBuffer sb ,String columnName,String val){
        if(useLikeSearch.get(columnName)) {//如果使用模糊查询
            if(likeWays.get(columnName)=="left"){
                sb.append(" "+ columnName+ " like '%'" + val+" and");
            }else if(likeWays.get(columnName)=="right"){
                sb.append(" "+ columnName+ " like " + val+"'%' and");
            }else{
                sb.append(" "+ columnName+ " like '%'" + val+"'%' and");
            }
        }else {
            sb.append(" "+ columnName+ "= " + val+" and");
        }
    }

    //根据Map 生成查询sql 带分页信息
    public static String getQueryPageByMap(PageUtils page,Map<String,Object> map,Class clazz){
        getTableName(clazz);
        StringBuffer sql = new StringBuffer(getListSql(clazz));
        if(map!=null){
            sql.append(" where ");
            Set<String> keys = map.keySet();
            for (String key: keys) {
                if(map.get(key)!=""){
                    if (map.get(key) instanceof Integer||map.get(key) instanceof Double) {
                        sql.append("  "+key+"="+map.get(key)+" and");
                    }else if (map.get(key) instanceof String) {
                        sql.append("  "+key+"='"+map.get(key)+"' and");
                    }else{
                        sql.append("  "+key+"='"+map.get(key)+"' and");
                    }
                }
            }
            sql.replace(sql.length()-3,sql.length(),"");
        }
        sql.append(" limit " + page.getFromIndex() + " , " + page.getPageSize());
        return sql.toString();
    }

    //根据Map 生成查询sql 带分页信息
    public static String getQueryPageByMapLike(PageUtils page,Map<String,Object> map,Class clazz){
        getTableName(clazz);
        StringBuffer sql = new StringBuffer(getListSql(clazz));
        if(map!=null){
            sql.append(" where ");
            Set<String> keys = map.keySet();
            for (String key: keys) {
                if(map.get(key)!=""){
                    if (map.get(key) instanceof Integer||map.get(key) instanceof Double) {
                        sql.append("  "+key+"="+map.get(key)+" and");
                    }else if (map.get(key) instanceof String) {
                        sql.append("  "+key+" like '%"+map.get(key)+"%' and");
                    }else{
                        sql.append("  "+key+" like '%"+map.get(key)+"%' and");
                    }
                }
            }
            sql.replace(sql.length()-3,sql.length(),"");
        }
        sql.append(" limit " + page.getFromIndex() + " , " + page.getPageSize());
        return sql.toString();
    }

    //根据map生成更新sql
    public static String getUpdateByMap(Class clazz,Map<String,Object> map){
        getTableName(clazz);
        StringBuffer sql = new StringBuffer("UPDATE "+tableName+" SET ");
        Set<String> keys = map.keySet();
        for (String key : keys) {
            if (map.get(key) instanceof Integer||map.get(key) instanceof Double) {
                sql.append(key+"="+map.get(key)+",");
            }else if (map.get(key) instanceof String) {
                sql.append(key+"='"+map.get(key)+"',");
            }else{
                sql.append(key+"='"+map.get(key)+"',");
            }
        }
        sql.replace(sql.length()-1,sql.length(),"");
        //
        sql.append(" WHERE REC_ID = "+map.get("rec_id"));
        return  sql.toString();
    }


    public static String getQuerySqlByMap(Class clazz,Map<String,Object> map){
        getIdColumn(clazz);
        getFullColumns(clazz);
        getTableName(clazz);
        StringBuffer sql = new StringBuffer("SELECT  ");
        for (String f : fullFieldList ) {
            sql.append(fullColumns.get(f));
            sql.append(" as "+f);
            sql.append(",");
        }
        sql.replace(sql.length()-1,sql.length(),"");
        sql.append(" from ");
        sql.append(tableName);
        sql.append("  WHERE 1=1 ");
        Set<String> keys = map.keySet();
        for (String key : keys) {
            if (map.get(key) instanceof Integer||map.get(key) instanceof Double) {
                sql.append("AND "+key+"="+map.get(key));
            }else if (map.get(key) instanceof String) {
                sql.append("AND "+key+"='"+map.get(key)+"'");
            }else{
                sql.append("AND "+key+"='"+map.get(key)+"'");
            }
        }
        return sql.toString();
    }


    //根据map生成插入sql
    public static String getInsertByMap(Class clazz,Map<String,Object> map){
        getTableName(clazz);
        StringBuffer sql = new StringBuffer("INSERT INTO "+tableName+" (");
        Set<String> keys = map.keySet();
        List<Object> list = new ArrayList<Object>();
        for (String key:keys) {
            sql.append(key+",");
            list.add(map.get(key));
        }
        sql.replace(sql.length()-1,sql.length(),"");
        sql.append(") VALUES ( ");
        for (Object o : list){
            if (o instanceof Integer||o instanceof Double) {
                sql.append(o+",");
            }else if (o instanceof String) {
                sql.append("'"+o+"',");
            }else{
                sql.append("'"+o+"',");
            }
        }
        sql.replace(sql.length()-1,sql.length(),"");
        sql.append(")");
        return  sql.toString();
    }


}
