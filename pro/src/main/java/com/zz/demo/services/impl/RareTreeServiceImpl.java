package com.zz.demo.services.impl;

import com.zz.demo.beans.Products;
import com.zz.demo.beans.RareTree;
import com.zz.demo.mappers.ProductMapper;
import com.zz.demo.mappers.RareTreeMapper;
import com.zz.demo.services.ProductService;
import com.zz.demo.services.RareTreeService;
import com.zz.utils.mappers.BaseMapper;
import com.zz.utils.services.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("rareTreeService")
public class RareTreeServiceImpl extends BaseServiceImpl<RareTree> implements RareTreeService {
    @Autowired
    private RareTreeMapper mapper;
    @Override
    public BaseMapper<RareTree> getMapper() {
        return mapper;
    }
}
