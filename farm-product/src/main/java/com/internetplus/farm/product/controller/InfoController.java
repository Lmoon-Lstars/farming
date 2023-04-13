package com.internetplus.farm.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.R;
import com.internetplus.farm.product.entity.InfoEntity;
import com.internetplus.farm.product.service.InfoService;
import com.internetplus.farm.product.service.PicInfoService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
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

  @Autowired
  private PicInfoService picInfoService;

  /**
   * 列表（后台管理系统）
   */
  @RequestMapping("/list")
  public R list(@RequestParam Map<String, Object> params) {
    PageUtils page = infoService.queryPage(params);
    return R.ok().put("page", page);
  }

  /**
   * 获取优惠商品
   */
  @RequestMapping("/getDiscount")
  public List getDiscount() {
    QueryWrapper wrapper = new QueryWrapper();
    wrapper.eq("publish_status",1);
    List<InfoEntity> products = infoService.list(wrapper);
    Date date = new Date();
    List<Map> res = new ArrayList<>();
    for (InfoEntity product : products) {
      if(date.after(product.getStartTime()) && date.before(product.getEndTime())) {
        Map<String,String> map = new HashMap<>();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("product_id",product.getProductId());
        map.put("id",String.valueOf(product.getProductId()));
        if(picInfoService.getOne(queryWrapper) != null) {
          map.put("picUrl",picInfoService.getOne(queryWrapper).getPicUrl());
        }
        map.put("name",product.getProductName());
        map.put("price",String.valueOf(product.getPrice()));
        map.put("disPrice",String.valueOf(product.getDisPrice()));
        map.put("weight",String.valueOf(product.getPerWeight()));
        map.put("supplyNum",String.valueOf(product.getSupplyNum()));
        map.put("startTime",product.getStartTime().toString());
        map.put("endTime",product.getEndTime().toString());
        res.add(map);
      }
    }
    return res;
  }

  /**
   * 展示商品信息
   */
  @RequestMapping("/showList")
  public R showList(@RequestParam(value = "typeCode")String typeCode) {
    R r = new R();
    List<InfoEntity> productList = new ArrayList<>();
    QueryWrapper queryWrapper = new QueryWrapper();
    if(!typeCode.equals("-1")) {
      queryWrapper.eq("type_code",Integer.valueOf(typeCode));
      queryWrapper.eq("if_show",0);
      queryWrapper.eq("publish_status",1);
      productList = infoService.list(queryWrapper);
      List<Map> list = new ArrayList<>();
      for (InfoEntity info : productList) {
        Map<String,String> map = new HashMap<>();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("product_id",info.getProductId());
        if(picInfoService.getOne(wrapper) != null) {
          map.put("picUrl",picInfoService.getOne(wrapper).getPicUrl());
        }
        map.put("productId",String.valueOf(info.getProductId()));
        map.put("price",String.valueOf(info.getPrice()));
        map.put("weight",String.valueOf(info.getPerWeight()));
        map.put("description",info.getDescription());
        map.put("supplyNum",String.valueOf(info.getSupplyNum()));
        map.put("place",info.getPlace());
        map.put("breed",info.getBreed());
        map.put("name",info.getProductName());
        map.put("isSpecial",String.valueOf(info.getIsSpecial()));
        list.add(map);
      }
      r.put("list",list);
    } else {

      queryWrapper.eq("if_show",1);
      queryWrapper.eq("publish_status",1);
      productList = infoService.list(queryWrapper);
      List<Map> list = new ArrayList<>();
      for (InfoEntity info : productList) {
        Map<String,String> map = new HashMap<>();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("product_id",info.getProductId());
        if(picInfoService.getOne(wrapper) != null) {
          map.put("picUrl",picInfoService.getOne(wrapper).getPicUrl());
        }
        map.put("productId",String.valueOf(info.getProductId()));
        map.put("price",String.valueOf(info.getPrice()));
        map.put("disPrice",String.valueOf(info.getDisPrice()));
        map.put("weight",String.valueOf(info.getPerWeight()));
        map.put("description",info.getDescription());
        map.put("supplyNum",String.valueOf(info.getSupplyNum()));
        map.put("place",info.getPlace());
        map.put("breed",info.getBreed());
        map.put("name",info.getProductName());
        map.put("isSpecial",String.valueOf(info.getIsSpecial()));
        list.add(map);
      }
      r.put("list",list);
    }
    return r;
  }

  /**
   * 信息（表单）
   */
  @RequestMapping("/info/{productId}")
  public R forminfo(@PathVariable("productId") Integer productId) {
    InfoEntity info = infoService.getById(productId);

    return R.ok().put("info", info);
  }

  /**
   *获取单个信息
   */
  @RequestMapping("/info")
  public InfoEntity info(@RequestParam("productId") Integer productId) {
    InfoEntity info = infoService.getById(productId);

    return info;
  }

  /**
   * 保存
   */
  @RequestMapping("/save")
  public R save(@RequestBody InfoEntity info) {
    infoService.saveInfo(info);

    return R.ok();
  }

  /**
   * 修改
   */
  @RequestMapping("/update")
  public void update(@RequestBody InfoEntity info) {

    infoService.updateInfo(info);
  }

  /**
   * 删除
   */
  @RequestMapping("/delete")
  public R delete(@RequestBody Integer[] productIds) {
    picInfoService.removeByIds(Arrays.asList(productIds));
    return R.ok();
  }

  /**
   * 下架
   */
  @RequestMapping("/down")
  public R down(@RequestBody Integer[] productIds) {
    for (Integer productId : productIds) {
      InfoEntity info = infoService.getBaseMapper().selectById(productId);
      info.setPublishStatus(0);
      infoService.updateInfo(info);
    }
    return R.ok();
  }

  /**
   *上架
   */
  @RequestMapping("/up")
  public R up(@RequestBody Integer[] productIds) {
    for (Integer productId : productIds) {
      InfoEntity info = infoService.getBaseMapper().selectById(productId);
      info.setPublishStatus(1);
      infoService.updateInfo(info);
    }
    return R.ok();
  }

  /**
   * 产品售出
   */
  @RequestMapping("/sell")
  public void sell(@RequestParam("productId") String productId,@RequestParam("num") String num) {
    QueryWrapper queryWrapper = new QueryWrapper();
    queryWrapper.eq("product_id",productId);
    InfoEntity info = infoService.getOne(queryWrapper);
    info.setSupplyNum(info.getSupplyNum() - Integer.valueOf(num));
    infoService.updateInfo(info);
  }

  /**
   * 模糊查询
   */
  @RequestMapping("/search")
  public R search(@Param("productId") Integer productId,@Param("supplyNum")Integer supplyNum,@Param("ifShow") Integer ifShow) {
    QueryWrapper<InfoEntity> queryWrapper = new QueryWrapper<>();
    if(productId != null) {
      queryWrapper.like("product_id",productId);
    }
    if(ifShow != null) {
      queryWrapper.like("if_show",ifShow);
    }
    if(supplyNum != null) {
      queryWrapper.like("supply_num",supplyNum);
    }
    List<InfoEntity> list = infoService.getBaseMapper().selectList(queryWrapper);
    R r = new R();
    r.put("list",list);
    return r;
  }

  /**
   * 根据产品名进行模糊查询
   */
  @RequestMapping("/searchByName")
  public List searchByName(@Param("productName") String productName) {
    QueryWrapper<InfoEntity> queryWrapper = new QueryWrapper<>();
    if(productName != null) {
      queryWrapper.like("product_name",productName);
    }
    queryWrapper.eq("publish_status",1);
    List<InfoEntity> productList = infoService.getBaseMapper().selectList(queryWrapper);
    List<Map> list = new ArrayList<>();
    for (InfoEntity info : productList) {
      Map<String,String> map = new HashMap<>();
      QueryWrapper wrapper = new QueryWrapper();
      wrapper.eq("product_id",info.getProductId());
      map.put("picUrl",picInfoService.getOne(wrapper).getPicUrl());
      map.put("productId",String.valueOf(info.getProductId()));
      map.put("price",String.valueOf(info.getPrice()));
      map.put("weight",String.valueOf(info.getPerWeight()));
      map.put("description",info.getDescription());
      map.put("supplyNum",String.valueOf(info.getSupplyNum()));
      map.put("place",info.getPlace());
      map.put("breed",info.getBreed());
      map.put("isSpecial",String.valueOf(info.getIsSpecial()));
      list.add(map);
    }
    return list;
  }
}
