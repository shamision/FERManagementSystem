package com.project.FERMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class FermsApplication {

	public static void main(String[] args) {

		SpringApplication.run(FermsApplication.class, args);
	}


}
