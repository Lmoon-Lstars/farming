package com.internetplus.farm.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.internetplus.farm.product.dao")
public class FarmProductApplication {

  public static void main(String[] args) {
    SpringApplication.run(FarmProductApplication.class, args);
  }

}
