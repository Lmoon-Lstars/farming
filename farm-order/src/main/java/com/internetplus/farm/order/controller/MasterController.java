package com.internetplus.farm.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.internetplus.farm.order.client.ProductService;
import com.internetplus.farm.order.client.UserService;
import com.internetplus.farm.order.entity.DetailEntity;
import com.internetplus.farm.order.service.DetailService;
import com.internetplus.farm.user.entity.AddressEntity;
import com.internetplus.farm.user.entity.CartEntity;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

import com.internetplus.farm.order.entity.MasterEntity;
import com.internetplus.farm.order.service.MasterService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.R;



/**
 * 订单主表
 *
 * @author lcx
 * @email 1181153997@gmail.com
 * @date 2023-03-13 08:47:57
 */
@RestController
@RequestMapping("order/master")
public class MasterController {
    @Autowired
    private MasterService masterService;

    @Autowired
    private UserService userService;

    @Autowired
    private DetailService detailService;

    @Autowired
    private ProductService productService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = masterService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{orderId}")
    public R info(@PathVariable("orderId") Integer orderId){
		MasterEntity master = masterService.getById(orderId);

        return R.ok().put("master", master);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MasterEntity master){
		masterService.saveOrder(master);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MasterEntity master){
		masterService.updateById(master);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] orderIds){
		masterService.removeByIds(Arrays.asList(orderIds));
        for (Integer orderId : orderIds) {
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("order_id",orderId);
            detailService.remove(wrapper);
        }

        return R.ok();
    }

    /**
     * 取消订单
     */
    @RequestMapping("/cancel")
    public R cancel(@RequestParam(value = "orderId")String orderId) {
        MasterEntity master = masterService.getById(orderId);
        master.setOrderStatus(3);
        masterService.updateById(master);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id",orderId);
        List<DetailEntity> list = detailService.list(queryWrapper);
        for (DetailEntity detail : list) {
            productService.sell(String.valueOf(detail.getProductId()),String.valueOf(-detail.getProductCnt()));
        }
        return R.ok("取消成功");
    }

    /**
     * 查询订单信息
     */
    @RequestMapping("/listOrder")
    public List listOrder(@RequestParam("userId")String userId,@RequestParam("orderStatus")String orderStatus) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("order_status",Integer.valueOf(orderStatus));
        wrapper.eq("customer_id",Integer.valueOf(userId));
        List<MasterEntity> masterList = masterService.list(wrapper);
        List<Map> res = new ArrayList<>();
        for (MasterEntity master : masterList) {
            Map<String,Object> map = new HashMap<>();
            map.put("orderId",String.valueOf(master.getOrderId()));
            map.put("orderSn",String.valueOf(master.getOrderSn()));
            map.put("userId",String.valueOf(master.getCustomerId()));
            map.put("shippingUser",master.getShippingUser());
            map.put("province",master.getProvince());
            map.put("city",master.getCity());
            map.put("district",master.getDistrict());
            map.put("address",master.getAddress());
            map.put("orderMoney",String.valueOf(master.getOrderMoney()));
            map.put("districtMoney",String.valueOf(master.getDistrictMoney()));
            map.put("paymentMoney",String.valueOf(master.getPaymentMoney()));
            map.put("createTime",String.valueOf(master.getCreateTime()));
            map.put("remark",master.getRemark());
            map.put("phoneNumber",master.getPhoneNumber());
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("order_id",master.getOrderId());
            int i = 1;
            List<DetailEntity> detailList = detailService.list(queryWrapper);
            List<String> picUrl = new ArrayList<>();
            for (DetailEntity detail : detailList) {
                picUrl.add(productService.getUrl(String.valueOf(detail.getProductId())));
            }
            map.put("picUrl",picUrl);
            res.add(map);
        }
        return res;
    }

    /**
     * 下单
     */
    @RequestMapping("/settlement")
    public R settlement(@RequestParam("userId")String userId,@RequestParam("addressId")String addressId,@RequestParam("cuponId")String cuponId,@RequestParam("sum")String sum,@RequestParam("shippingMoney")String shippingMoney,@RequestParam("remark")String remark) {
        List<CartEntity> list = userService.getCartInfo(userId);
        for (CartEntity cart : list) {
            if(cart.getQuantity() >= productService.info(cart.getProductId()).getSupplyNum()) {
                userService.clear(userId);
                return R.ok("库存不足，请重新下单");
            }
        }
        MasterEntity master = new MasterEntity();
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString();
        master.setOrderSn(Long.valueOf(date));
        master.setCustomerId(Integer.valueOf(userId));
        AddressEntity address = userService.getInfo(addressId);
        master.setShippingUser(address.getShippingUser());
        master.setProvince(address.getProvince());
        master.setCity(address.getCity());
        master.setPhoneNumber(address.getPhoneNumber());
        master.setDistrict(address.getDistrict());
        master.setAddress(address.getAddress());
        master.setRemark(remark);
        master.setOrderMoney(new BigDecimal(sum));
        int cuponMoney = 0;
        if(!cuponId.equals("null")) {
            cuponMoney = userService.getCuponInfo(cuponId).getCuponAmount();
            master.setDistrictMoney(new BigDecimal(userService.getCuponInfo(cuponId).getCuponAmount().toString()));
        }
        master.setShippingMoney(new BigDecimal(shippingMoney));
        master.setPaymentMoney(master.getOrderMoney().subtract(new BigDecimal(String.valueOf(cuponMoney))).add(master.getShippingMoney()));
        master.setCreateTime(new Date());
        master.setOrderStatus(1);
        masterService.save(master);
        for (CartEntity cart : list) {
            productService.sell(String.valueOf(cart.getProductId()),String.valueOf(cart.getQuantity()));
            DetailEntity detail = new DetailEntity();
            detail.setOrderId(master.getOrderId());
            detail.setProductId(cart.getProductId());
            detail.setProductName(productService.info(cart.getProductId()).getProductName());
            detail.setProductCnt(cart.getQuantity());
            detail.setProductPrice(productService.info(cart.getProductId()).getPrice());
            detail.setWeight(Double.valueOf(productService.info(cart.getProductId()).getPerWeight() * cart.getQuantity()));
            detailService.save(detail);
        }
        userService.clear(userId);
        return R.ok();
    }

    /**
     * 模糊查询
     */
    @RequestMapping("/search")
    public List search(@Param("orderId")Integer orderId,@Param("customerId")Integer customerId,
        @Param("shippingUser")String shippingUser,@Param("address")String address,
        @Param("createTime") Date createTime,@Param("phoneNumber") String phoneNumber) {
        return masterService.findByConditions(orderId,customerId,shippingUser,address,createTime,phoneNumber);
    }
}
