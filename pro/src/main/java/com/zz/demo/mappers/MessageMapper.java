package com.zz.demo.mappers;

import com.zz.demo.beans.Messages;
import com.zz.utils.mappers.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by admin on 2017/9/15.
 */
@Mapper
public interface MessageMapper extends BaseMapper<Messages>{
}
