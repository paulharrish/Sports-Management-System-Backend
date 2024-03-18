package com.project.sportsManagement;

import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableVaadin
@ComponentScan(basePackages = "com")

public class SportsManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportsManagementApplication.class, args);
	}

}
