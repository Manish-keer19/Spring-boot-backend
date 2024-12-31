package com.ms19.LearningSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LearningSpringApplication {

	public static void main(String[] args) {
	ApplicationContext context = 	SpringApplication.run(LearningSpringApplication.class, args);


   Dev dev =  context.getBean(Dev.class);

	//   Dev dev  = new Dev();
	  dev.build();

	}




}
