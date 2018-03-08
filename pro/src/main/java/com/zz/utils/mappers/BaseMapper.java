package com.zz.utils.mappers;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by admin on 2017/7/6.
 */
public interface BaseMapper<T> {
    @Select("${sql}")
    T getInfo(@Param("sql") String sql);
    @Update("${sql}")
    int update(@Param("sql") String sql);
    @Insert("${sql}")
    int insert(@Param("sql") String sql);
    @Delete("${sql}")
    int delete(@Param("sql") String sql);
    @Select("${sql}")
    List<T> getList(@Param("sql") String sql);

    @Select("${sql}")
    int getTotalNum(@Param("sql") String sql);
}
