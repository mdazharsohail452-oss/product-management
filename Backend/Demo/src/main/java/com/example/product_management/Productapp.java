package com.example.product_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Productapp {

	public static void main(String[] args) {
		SpringApplication.run(Productapp.class, args);
	}

}
