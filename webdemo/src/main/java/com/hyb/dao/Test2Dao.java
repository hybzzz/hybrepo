package com.hyb.dao;

import com.hyb.anno.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by admin on 2018/4/3.
 */
@Mapper
public interface Test2Dao {
    @Select("select count(1) from t_user")
    int getNum();
}
