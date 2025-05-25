package com.libcode.crud.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.libcode.crud.crud.config.EnvInitializer;

@SpringBootApplication
public class CrudApplication {

	public static void main(String[] args) {
		// Cargar .env antes que Spring Boot
        Class<?> env = EnvInitializer.class;
		SpringApplication.run(CrudApplication.class, args);
	}

}
