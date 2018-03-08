package com.zz.demo.services.impl;

import com.zz.demo.beans.Messages;
import com.zz.demo.mappers.MessageMapper;
import com.zz.demo.services.MessageService;
import com.zz.utils.mappers.BaseMapper;
import com.zz.utils.services.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("messageService")
public class MessageServiceImpl extends BaseServiceImpl<Messages> implements MessageService {
    @Autowired
    private MessageMapper mapper;
    @Override
    public BaseMapper<Messages> getMapper() {
        return mapper;
    }
}
