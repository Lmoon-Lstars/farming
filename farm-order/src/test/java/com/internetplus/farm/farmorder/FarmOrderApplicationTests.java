package com.internetplus.farm.farmorder;

import com.internetplus.farm.order.FarmOrderApplication;
import com.internetplus.farm.order.entity.CommentInfoEntity;
import com.internetplus.farm.order.service.CommentInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = FarmOrderApplication.class)
@RunWith(SpringRunner.class)
class FarmOrderApplicationTests {

  @Autowired
  CommentInfoService commentInfoService;

  @Test
  void contextLoads() {
    CommentInfoEntity byId = commentInfoService.getById(1);
    System.out.println(byId);
  }

}
