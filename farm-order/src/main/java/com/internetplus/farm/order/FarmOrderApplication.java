package com.internetplus.farm.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages ={"com.internetplus.farm.order.client"} )
public class FarmOrderApplication {

  public static void main(String[] args) {
    SpringApplication.run(FarmOrderApplication.class, args);
  }
}
