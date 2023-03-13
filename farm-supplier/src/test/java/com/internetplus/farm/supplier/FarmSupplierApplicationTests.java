package com.internetplus.farm.supplier;

import com.internetplus.farm.supplier.entity.InfoEntity;
import com.internetplus.farm.supplier.service.InfoService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = FarmSupplierApplication.class)
@RunWith(SpringRunner.class)
class FarmSupplierApplicationTests {

  @Autowired
  InfoService infoService;

  @Test
  void contextLoads() {
    InfoEntity info = new InfoEntity();
    info.setGender("1");
    info.setSupplierId(1);
    info.setSupplierInfoId(1);
    info.setSupplierName("1");
    info.setSupplierEmail("1");
    info.setMobilePhone(1);
    info.setIdentityCardNo("1");
    info.setIdentityCardType(1);
    System.out.println("测试");
    infoService.save(info);
  }

}
