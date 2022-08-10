package com.ktds.zipzero.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.ktds.zipzero.**.mapper"})
public class MyBatisConfig {
    

    
}
