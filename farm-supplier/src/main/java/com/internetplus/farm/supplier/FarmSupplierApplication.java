package com.internetplus.farm.supplier;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ServletComponentScan
@EnableAsync
@EnableDiscoveryClient
@MapperScan("com.internetplus.farm.supplier.dao")
public class FarmSupplierApplication {

  public static void main(String[] args) {
    SpringApplication.run(FarmSupplierApplication.class, args);
  }

}
