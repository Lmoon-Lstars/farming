package com.internetplus.farm.user.client;

import com.internetplus.common.utils.R;
import com.internetplus.farm.product.entity.InfoEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient("farm-product")
public interface ProductService {
  @RequestMapping("product/info/info")
  public InfoEntity info(@RequestParam("productId") Integer productId);

  @RequestMapping("product/info/update")
  public void update(@RequestBody InfoEntity info);

  @RequestMapping("product/picinfo/getPic")
  public String getUrl(@RequestParam(value = "productId")String productId);
}
