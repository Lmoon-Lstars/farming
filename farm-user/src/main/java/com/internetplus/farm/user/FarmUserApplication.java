package com.internetplus.farm.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages ={"com.internetplus.farm.user.client"} )
public class FarmUserApplication {

  public static void main(String[] args) {
    SpringApplication.run(FarmUserApplication.class, args);
  }

}
