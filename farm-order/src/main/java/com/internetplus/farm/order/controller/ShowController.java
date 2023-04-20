package com.internetplus.farm.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.internetplus.farm.order.dao.ShowDao;
import com.internetplus.farm.order.entity.DetailEntity;
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
    public R list(){
        List<ShowEntity> list = showDao.generateList();
        Collections.sort(list,new Comparator<ShowEntity>() {
            @Override
            public int compare(ShowEntity o1, ShowEntity o2) {
                return o2.getOrderId() - o1.getOrderId();
            }
        });

        return R.ok().put("list", list);
    }

    /**
     * 订单查询
     */
    @RequestMapping("/search")
    public R search(@Param("orderId")String orderId,@Param("shipperName")String shipperName,@Param("productName")String productName) {
        List<ShowEntity> list = showDao.generateList();
        if(orderId != null) {
            list.removeIf(
                showEntity -> !(String.valueOf(showEntity.getOrderId()).contains(orderId)));
        }
        if(shipperName != null) {
            list.removeIf(showEntity -> !(showEntity.getShippingUser().contains(shipperName)));
        }
        if(productName != null) {
            list.removeIf(showEntity -> !(showEntity.getProductName().contains(productName)));
        }
        Collections.sort(list,new Comparator<ShowEntity>() {
            @Override
            public int compare(ShowEntity o1, ShowEntity o2) {
                return o2.getOrderId() - o1.getOrderId();
            }
        });
        R r = new R();
        r.put("list",list);
        return r;
    }

}
