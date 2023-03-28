package com.internetplus.farm.order.client;

import com.internetplus.farm.product.entity.InfoEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("farm-product")
public interface ProductService {
  @RequestMapping("product/info/info")
  public InfoEntity info(@RequestParam("productId") Integer productId);
}
