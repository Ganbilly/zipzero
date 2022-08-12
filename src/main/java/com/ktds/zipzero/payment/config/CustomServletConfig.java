package com.ktds.zipzero.payment.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**/*.html")
            .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/**/*.png")
            .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/**/*.jpg")
            .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/**/*.css")
            .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/**/*.js")
            .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/**/*.ttf")
            .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/**/*.woff*")
            .addResourceLocations("classpath:/static/");


    }
}