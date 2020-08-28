package com.einschpanner.catchup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class CatchUpApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatchUpApplication.class, args);
	}

}
