package com.example.deliveryfeecalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DeliveryFeeCalculatorApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(DeliveryFeeCalculatorApplication.class, args);
	}





}
