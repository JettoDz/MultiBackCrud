package com.jettodz.java_book_crud.config;

import com.jettodz.java_book_crud.util.exceptions.BookToBookRecordConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.DefaultConversionService;
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
