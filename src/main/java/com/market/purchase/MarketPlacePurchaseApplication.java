package com.market.purchase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class MarketPlacePurchaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketPlacePurchaseApplication.class, args);
	}

}
