package com.internetplus.farm.order.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.internetplus.farm.order.entity.ProductPicInfoEntity;
import com.internetplus.farm.order.service.ProductPicInfoService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.R;



/**
 * 商品图片信息表
 *
 * @author lcx
 * @email 1181153997@gmail.com
 * @date 2023-03-13 08:50:57
 */
@RestController
@RequestMapping("order/productpicinfo")
public class ProductPicInfoController {
    @Autowired
    private ProductPicInfoService productPicInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productPicInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{productPicId}")
    public R info(@PathVariable("productPicId") Integer productPicId){
		ProductPicInfoEntity productPicInfo = productPicInfoService.getById(productPicId);

        return R.ok().put("productPicInfo", productPicInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ProductPicInfoEntity productPicInfo){
		productPicInfoService.save(productPicInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ProductPicInfoEntity productPicInfo){
		productPicInfoService.updateById(productPicInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] productPicIds){
		productPicInfoService.removeByIds(Arrays.asList(productPicIds));

        return R.ok();
    }

}
