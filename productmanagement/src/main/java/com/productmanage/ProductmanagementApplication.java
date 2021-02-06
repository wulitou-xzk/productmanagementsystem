package com.productmanage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("com.productmanage.mapper")
@SpringBootApplication
@EnableCaching
public class ProductmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductmanagementApplication.class, args);
	}

}
