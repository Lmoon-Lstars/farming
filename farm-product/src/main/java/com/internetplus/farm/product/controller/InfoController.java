package com.internetplus.farm.product.controller;

import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.R;
import com.internetplus.farm.product.entity.InfoEntity;
import com.internetplus.farm.product.service.InfoService;
import java.util.Arrays;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 商品信息表
 *
 * @author lcx
 * @email 1181153997@gmail.com
 * @date 2023-03-13 16:42:29
 */
@RestController
@RequestMapping("product/info")
public class InfoController {

  @Autowired
  private InfoService infoService;

  /**
   * 列表
   */
  @RequestMapping("/list")
  public R list(@RequestParam Map<String, Object> params) {
    PageUtils page = infoService.queryPage(params);
    return R.ok().put("page", page);
  }


  /**
   * 信息
   */
  @RequestMapping("/info/{productId}")
  public R info(@PathVariable("productId") Integer productId) {
    InfoEntity info = infoService.getById(productId);

    return R.ok().put("info", info);
  }

  /**
   * 保存
   */
  @RequestMapping("/save")
  public R save(@RequestBody InfoEntity info) {
    infoService.save(info);

    return R.ok();
  }

  /**
   * 修改
   */
  @RequestMapping("/update")
  public R update(@RequestBody InfoEntity info) {
    infoService.updateById(info);

    return R.ok();
  }

  /**
   * 删除
   */
  @RequestMapping("/delete")
  public R delete(@RequestBody Integer[] productIds) {
    infoService.removeByIds(Arrays.asList(productIds));

    return R.ok();
  }

}
