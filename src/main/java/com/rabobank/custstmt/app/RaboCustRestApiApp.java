package com.rabobank.custstmt.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*Starting point of the application*/
@SpringBootApplication(scanBasePackages={"com.rabobank.custstmt"})// same as @Configuration @EnableAutoConfiguration @ComponentScan combined
public class RaboCustRestApiApp {

	public static void main(String[] args) {
		SpringApplication.run(RaboCustRestApiApp.class, args);
	}
}
