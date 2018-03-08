package com.zz.demo.mappers;

import com.zz.demo.beans.AppException;
import com.zz.utils.mappers.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by admin on 2017/12/4.
 */
@Mapper
public interface AppExceptionMapper extends BaseMapper<AppException> {
}
