package com.internetplus.farm.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.internetplus.farm.order.client.ProductService;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.internetplus.farm.order.entity.DetailEntity;
import com.internetplus.farm.order.service.DetailService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.R;



/**
 * 订单项表
 *
 * @author lcx
 * @email 1181153997@gmail.com
 * @date 2023-03-13 08:47:57
 */
@RestController
@RequestMapping("order/detail")
public class DetailController {
    @Autowired
    private DetailService detailService;

    @Autowired
    private ProductService productService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = detailService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{orderDetailId}")
    public R info(@PathVariable("orderDetailId") Integer orderDetailId){
		DetailEntity detail = detailService.getById(orderDetailId);

        return R.ok().put("detail", detail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody DetailEntity detail){
		detailService.save(detail);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody DetailEntity detail){
		detailService.updateById(detail);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] orderDetailIds){
		detailService.removeByIds(Arrays.asList(orderDetailIds));

        return R.ok();
    }

    /**
     * 查看订单详情
     */
    @RequestMapping("/listDetail")
    public List<Map<String,String>> listDetail(@RequestParam("orderId")String orderId) {
        QueryWrapper<DetailEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id",Integer.valueOf(orderId));
        List<Map<String,String>> res = new ArrayList<>();
        List<DetailEntity> list = detailService.list(wrapper);
        for (DetailEntity detail : list) {
            Map<String,String> map = new HashMap<>();
            map.put("id",String.valueOf(detail.getOrderDetailId()));
            map.put("count",String.valueOf(detail.getProductCnt()));
            map.put("picUrl",productService.getUrl(String.valueOf(detail.getProductId())));
            map.put("name",detail.getProductName());
            map.put("price",String.valueOf(detail.getProductPrice()));
            map.put("weight",String.valueOf(detail.getWeight()));
            res.add(map);
        }
        return res;
    }

    /**
     * 模糊查询
     */
    @RequestMapping("/search")
    public R search(@Param("orderId")Integer orderId,@Param("productName") String productName,@Param("productId")Integer productId) {
        QueryWrapper<DetailEntity> queryWrapper = new QueryWrapper<>();
        if(orderId != null) {
            queryWrapper.like("order_id",orderId);
        }
        if(productId != null) {
            queryWrapper.like("product_id",productId);
        }
        if(productName != null) {
            queryWrapper.like("product_name",productName);
        }
        List<DetailEntity> list = detailService.getBaseMapper().selectList(queryWrapper);
        return R.ok("list",list);
    }

}
