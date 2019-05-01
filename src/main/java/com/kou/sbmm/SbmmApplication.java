package com.kou.sbmm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
//@ComponentScan("com.kou.sbmm.service.Spmmdb0001Service")
public class SbmmApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbmmApplication.class, args);

	}

}
