package com.ph.powerfiler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PowerfilerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PowerfilerApplication.class, args);
	}

}

