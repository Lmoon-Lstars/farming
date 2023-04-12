package com.internetplus.farm.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.internetplus.farm.user.client.ProductService;
import com.internetplus.farm.user.entity.InfoEntity;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import java.util.Stack;
import javax.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.internetplus.farm.user.entity.CartEntity;
import com.internetplus.farm.user.service.CartService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.R;



/**
 *
 *
 * @author wrk
 * @email 1181153997@gmail.com
 * @date 2023-03-25 13:14:18
 */
@RestController
@FeignClient("farm-product")//服务生产者名称
@RequestMapping("user/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cartService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		CartEntity cart = cartService.getById(id);

        return R.ok().put("cart", cart);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CartEntity cart){
		cartService.save(cart);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CartEntity cart){
		cartService.updateById(cart);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		cartService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 添加到购物车
     */
    @RequestMapping("/addToCart")
    public R addToCart(@RequestParam(value = "userId")String userId,@RequestParam(value = "productId")String productId){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",userId);
        wrapper.eq("product_id",productId);
        List<CartEntity> list = cartService.list(wrapper);
        int num = list.size();
        if(num != 0 && productService.info(Integer.valueOf(productId)).getSupplyNum() <= list.get(0).getQuantity()) {
            return R.ok("库存不足，请选购其他商品");
        }
        if(num == 0 && productService.info(Integer.valueOf(productId)).getSupplyNum() == 0) {
            return R.ok("库存不足，请选购其他商品");
        }
        CartEntity cart = new CartEntity();
        cart.setProductId(Integer.valueOf(productId));
        cart.setUserId(Integer.valueOf(userId));
        cart.setSelected(0);
        int quantity = 0;
        if(num == 0) {
            cart.setCreateTime(new Date());
            cart.setUpdateTime(new Date());
            cartService.save(cart);
        } else {
            CartEntity item = cartService.getOne(wrapper);
            item.setQuantity(item.getQuantity()+1);
            System.out.println(item.getQuantity());
            item.setUpdateTime(new Date());
            cartService.update(item,wrapper);
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        List<CartEntity> cartList = cartService.list(queryWrapper);
        for (CartEntity cartEntity : cartList) {
            quantity += cartEntity.getQuantity();
        }
        Map<Object,Object> map = new HashMap<>();
        map.put("quantity",quantity);
        map.put("sum",sum(userId).toString());
        return R.ok("res",map);
    }

    /**
     * 移除商品
     */
    @RequestMapping("/removeProduct")
    public R removeProduct(@RequestParam(value = "userId")String userId,@RequestParam(value = "productId")String productId) {
        Map<Object,Object> map = new HashMap<>();
        int quantity = 0;
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",userId);
        wrapper.eq("product_id",productId);
        CartEntity cart = cartService.getOne(wrapper);
        if(cart.getQuantity() == 1) {
            cartService.remove(wrapper);
        } else {
            cart.setQuantity(cart.getQuantity() - 1);
            cartService.update(cart,wrapper);
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        List<CartEntity> list = cartService.list(queryWrapper);
        for (CartEntity cartEntity : list) {
            quantity += cartEntity.getQuantity();
        }
        map.put("quantity",quantity);
        map.put("sum",sum(userId).toString());
        return R.ok("res",map);
    }

    /**
     *  清空购物车
     */
    @RequestMapping("/clear")
    public R clear(@RequestParam(value = "userId")String userId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",userId);
        cartService.remove(wrapper);
        return R.ok();
    }

    /**
     * 结算
     */
    @RequestMapping("/settlement")
    public R settlement(@RequestParam(value = "userId")String userId) {

        return R.ok("sum",sum(userId).toString());
    }

    public BigDecimal sum(String userId) {
        BigDecimal sum = new BigDecimal("0");
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",userId);
        List<CartEntity> cartList = cartService.list(wrapper);
        for (CartEntity cart : cartList) {
            BigDecimal price = productService.info(cart.getProductId()).getPrice();
            BigDecimal num =new BigDecimal(String.valueOf(cart.getQuantity()));
            sum = sum.add(price.multiply(num));
        }
        return sum;
    }

    /**
     * 获取用户购物车信息
     */
    @RequestMapping("/getCartInfo")
    public List<CartEntity> getCartInfo(@RequestParam("userId")String userId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",userId);
        return cartService.list(wrapper);
    }

    @RequestMapping("/getCart")
    public R getCart(@RequestParam("userId")String userId) {
        BigDecimal money = sum(userId);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",userId);
        List<CartEntity> cartList = cartService.list(wrapper);
        R r = new R();
        List<Map> list = new ArrayList<>();
        for (CartEntity cart : cartList) {
            Map<String,String> map = new HashMap<>();
            map.put("productName",productService.info(cart.getProductId()).getProductName());
            map.put("num",String.valueOf(cart.getQuantity()));
            map.put("picUrl",productService.getUrl(String.valueOf(cart.getProductId())));
            map.put("weight",String.valueOf(productService.info(cart.getProductId()).getPerWeight()));
            map.put("description",productService.info(cart.getProductId()).getDescription());
            map.put("price",String.valueOf(productService.info(cart.getProductId()).getPrice()));
            map.put("productId",String.valueOf(cart.getProductId()));
            list.add(map);
        }
        r.put("list",list);
        r.put("sum",money);
        return r;
    }
}
