package com.example.mycodeBack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class MycodeBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(MycodeBackApplication.class, args);
	}

}
