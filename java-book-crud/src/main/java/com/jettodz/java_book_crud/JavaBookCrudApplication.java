package com.jettodz.java_book_crud;

import com.jettodz.java_book_crud.config.ConversionConfig;
import com.jettodz.java_book_crud.util.exceptions.BookToBookRecordConverter;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import javax.sql.DataSource;
import java.util.List;

@SpringBootApplication
public class JavaBookCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaBookCrudApplication.class, args);
	}

}
