package com.example.gmail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GmailApplication {
//01:12:43
	@Bean
	public RestTemplate getRestTemplate() {
		return  new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(GmailApplication.class, args);
	}

}
