package com.internetplus.farm.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.internetplus.farm.user.entity.AddressEntity;
import com.internetplus.farm.user.service.AddressService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.R;



/**
 * 
 *
 * @author wrk
 * @email 1181153997@gmail.com
 * @date 2023-03-22 14:52:47
 */
@RestController
@RequestMapping("user/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = addressService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{addressId}")
    public R info(@PathVariable("addressId") Integer addressId){
		AddressEntity address = addressService.getById(addressId);

        return R.ok().put("address", address);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AddressEntity address){
		    addressService.save(address);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AddressEntity address){
		addressService.updateById(address);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] addressIds){
		addressService.removeByIds(Arrays.asList(addressIds));

        return R.ok();
    }

    /**
     * 获取地址
     */
    @RequestMapping("/getInfo")
    public AddressEntity getInfo(@RequestParam("addressId")String addressId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("address_id",addressId);
        return addressService.getOne(wrapper);
    }
}
