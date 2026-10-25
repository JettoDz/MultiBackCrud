package com.jettodz.crud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.DefaultConversionService;

import com.jettodz.crud.util.BookToBookRecordConverter;

import org.springframework.core.convert.ConversionService;

@Configuration
public class ConversionConfig {

    @Bean
    public ConversionService conversionService() {
        DefaultConversionService service = new DefaultConversionService();
        service.addConverter(new BookToBookRecordConverter());
        return service;
    }

}
