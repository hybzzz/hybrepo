package com.zz.demo.services.impl;

import com.zz.demo.beans.AppException;
import com.zz.demo.mappers.AppExceptionMapper;
import com.zz.demo.services.AppExceptionService;
import com.zz.utils.mappers.BaseMapper;
import com.zz.utils.services.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("appExceptionService")
public class AppExceptionServiceImpl extends BaseServiceImpl<AppException> implements AppExceptionService {
    @Autowired
    private AppExceptionMapper mapper;
    @Override
    public BaseMapper<AppException> getMapper() {
        return mapper;
    }
}
