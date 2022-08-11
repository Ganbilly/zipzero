package com.ktds.zipzero.payment.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ktds.zipzero.payment.controller.formatter.LocalDateTimeFormatter;

import lombok.extern.log4j.Log4j2;

@Configuration
@EnableWebMvc
@Log4j2
public class CustomServletConfig implements WebMvcConfigurer{


    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new LocalDateTimeFormatter());
    }
}
