package com.zz.demo.services.impl;

import com.zz.demo.beans.Lnquiry;
import com.zz.demo.mappers.LnquiryMapper;
import com.zz.demo.services.LnquiryService;
import com.zz.utils.mappers.BaseMapper;
import com.zz.utils.services.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("lnquiryService")
public class LnquiryServiceImpl extends BaseServiceImpl<Lnquiry> implements LnquiryService {
    @Autowired
    private LnquiryMapper mapper;
    @Override
    public BaseMapper<Lnquiry> getMapper() {
        return mapper;
    }

}
