package com.csrp.csrp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CsrpApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsrpApplication.class, args);
	}

}
