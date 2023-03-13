package com.internetplus.farm.order.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.internetplus.farm.order.entity.ProductInfoEntity;
import com.internetplus.farm.order.service.ProductInfoService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.R;



/**
 * 商品信息表
 *
 * @author lcx
 * @email 1181153997@gmail.com
 * @date 2023-03-13 08:50:57
 */
@RestController
@RequestMapping("order/productinfo")
public class ProductInfoController {
    @Autowired
    private ProductInfoService productInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{productId}")
    public R info(@PathVariable("productId") Integer productId){
		ProductInfoEntity productInfo = productInfoService.getById(productId);

        return R.ok().put("productInfo", productInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ProductInfoEntity productInfo){
		productInfoService.save(productInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ProductInfoEntity productInfo){
		productInfoService.updateById(productInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] productIds){
		productInfoService.removeByIds(Arrays.asList(productIds));

        return R.ok();
    }

}
