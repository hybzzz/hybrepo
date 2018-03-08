package com.zz.demo.mappers;

import com.zz.demo.beans.Products;
import com.zz.utils.mappers.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by admin on 2017/9/15.
 */
@Mapper
public interface ProductMapper extends BaseMapper<Products>{
}
