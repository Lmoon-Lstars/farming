package com.internetplus.farm.product;

import com.internetplus.farm.product.entity.InfoEntity;
import com.internetplus.farm.product.service.InfoService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = FarmProductApplication.class)
@RunWith(SpringRunner.class)
class FarmProductApplicationTests {

  @Autowired
  InfoService infoService;

  @Test
  void contextLoads() {
    InfoEntity byId = infoService.getById(1);
    System.out.println(byId);
  }

}
