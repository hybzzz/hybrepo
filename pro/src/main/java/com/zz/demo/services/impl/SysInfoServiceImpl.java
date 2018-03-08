package com.zz.demo.services.impl;

import com.zz.demo.beans.SysInfo;
import com.zz.demo.mappers.SysInfoMapper;
import com.zz.demo.services.SysInfoService;
import com.zz.utils.mappers.BaseMapper;
import com.zz.utils.services.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysInfoService")
public class SysInfoServiceImpl extends BaseServiceImpl<SysInfo> implements SysInfoService {
    @Autowired
    private SysInfoMapper mapper;
    @Override
    public BaseMapper<SysInfo> getMapper() {
        return mapper;
    }
}
