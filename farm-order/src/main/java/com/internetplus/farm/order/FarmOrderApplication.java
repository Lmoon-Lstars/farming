package com.internetplus.farm.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.internetplus.farm.order.dao")
public class FarmOrderApplication {

  public static void main(String[] args) {
    SpringApplication.run(FarmOrderApplication.class, args);
  }
}
