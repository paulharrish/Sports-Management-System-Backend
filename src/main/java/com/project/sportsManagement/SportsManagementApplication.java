package com.project.sportsManagement;

import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableVaadin
public class SportsManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportsManagementApplication.class, args);
	}

}
