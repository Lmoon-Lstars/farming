package com.internetplus.farm.order.client;

import com.internetplus.common.utils.R;
import com.internetplus.farm.user.entity.AddressEntity;

import com.internetplus.farm.user.entity.CartEntity;
import com.internetplus.farm.user.entity.CuponEntity;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient("farm-user")
public interface UserService {
  @RequestMapping("user/address/getInfo")
  public AddressEntity getInfo(@RequestParam("addressId")String addressId);

  @RequestMapping("user/cupon/getInfo")
  public CuponEntity getCuponInfo(@RequestParam("cuponId")String cuponId);

  @RequestMapping("/user/cupon/deleteCupon")
  public void deleteCupon(@RequestParam("cuponId")Integer cuponId);

  @RequestMapping("user/cart/getCartInfo")
  public List<CartEntity> getCartInfo(@RequestParam("userId")String userId);

  @RequestMapping("user/cart/clear")
  public R clear(@RequestParam(value = "userId")String userId);

  @RequestMapping("user/info/setPoint")
  public void setPoint(@RequestParam(value = "userId")Integer userId,@RequestParam(value = "plantPoint")String point);
}
