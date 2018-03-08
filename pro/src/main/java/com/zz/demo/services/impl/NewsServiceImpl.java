package com.zz.demo.services.impl;

import com.zz.demo.beans.News;
import com.zz.demo.mappers.NewsMapper;
import com.zz.demo.services.NewsService;
import com.zz.utils.mappers.BaseMapper;
import com.zz.utils.services.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("newsService")
public class NewsServiceImpl extends BaseServiceImpl<News> implements NewsService {
    @Autowired
    private NewsMapper mapper;
    @Override
    public BaseMapper<News> getMapper() {
        return mapper;
    }

}
