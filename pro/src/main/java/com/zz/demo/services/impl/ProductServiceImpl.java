package com.zz.demo.services.impl;

import com.zz.demo.beans.Products;
import com.zz.demo.mappers.ProductMapper;
import com.zz.demo.services.ProductService;
import com.zz.utils.mappers.BaseMapper;
import com.zz.utils.services.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("productService")
public class ProductServiceImpl extends BaseServiceImpl<Products> implements ProductService {
    @Autowired
    private ProductMapper mapper;
    @Override
    public BaseMapper<Products> getMapper() {
        return mapper;
    }
}
