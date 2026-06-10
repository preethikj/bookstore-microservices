package com.example.guvi.service.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class GuviServiceDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuviServiceDiscoveryApplication.class, args);
	}

}
