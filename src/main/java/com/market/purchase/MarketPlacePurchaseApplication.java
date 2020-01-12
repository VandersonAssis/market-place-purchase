package com.market.purchase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients("com.market.purchase")
@SpringBootApplication
public class MarketPlacePurchaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketPlacePurchaseApplication.class, args);
	}

}
