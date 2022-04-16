package com.example.BarclaysTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BarclaysTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarclaysTestApplication.class, args);
	}

}
