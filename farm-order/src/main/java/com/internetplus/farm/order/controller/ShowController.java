package com.internetplus.farm.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.internetplus.common.utils.Query;
import com.internetplus.farm.order.dao.ShowDao;
import com.internetplus.farm.order.entity.DetailEntity;
import com.internetplus.farm.product.entity.InfoEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.internetplus.farm.order.entity.ShowEntity;
import com.internetplus.farm.order.service.ShowService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.R;



/**
 *
 *
 * @author wrk
 * @email 1181153997@gmail.com
 * @date 2023-04-12 15:23:24
 */
@RestController
@RequestMapping("order/show")
public class ShowController {
    @Autowired
    private ShowService showService;

    @Autowired
    private ShowDao showDao;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@Param("orderId")String orderId,@Param("shipperName")String shipperName,@Param("productName")String productName,@Param("orderStatus")Integer orderStatus,@RequestParam Map<String, Object> params) {
        QueryWrapper<ShowEntity> queryWrapper = new QueryWrapper<>();
        if(orderId != null) {
            queryWrapper.like("order_id",orderId);
        }
        if(shipperName != null) {
            queryWrapper.like("shipping_user",shipperName);
        }
        if(productName != null) {
            queryWrapper.like("product_name",productName);
        }
        if(orderStatus != null) {
            queryWrapper.like("order_status",orderStatus);
        }
        queryWrapper.orderByDesc("create_time");
        IPage<ShowEntity> page = showService.page(
            new Query<ShowEntity>().getPage(params),
            queryWrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        return R.ok().put("page", pageUtils);
    }

}
