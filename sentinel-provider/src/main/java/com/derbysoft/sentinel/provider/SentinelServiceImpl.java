package com.derbysoft.sentinel.provider;

import com.derbysoft.sentinel.api.SentinelService;
import org.apache.dubbo.config.annotation.Service;

import java.time.LocalDateTime;

@Service
public class SentinelServiceImpl implements SentinelService {
    @Override
    public String sayHello(String param) {
        return "Hello world" + LocalDateTime.now();
    }
}
