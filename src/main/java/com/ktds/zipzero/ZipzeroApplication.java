package com.ktds.zipzero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication(scanBasePackages = {"com.ktds.zipzero.payment.service.PaymentService"})
@SpringBootApplication
public class ZipzeroApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZipzeroApplication.class, args);
	}

}
