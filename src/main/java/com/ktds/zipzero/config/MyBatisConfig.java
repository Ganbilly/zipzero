package com.ktds.zipzero.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.ktds.zipzero.payment", "com.ktds.zipzero.member", "com.ktds.zipzero.comment"})
public class MyBatisConfig {
    

    
}
