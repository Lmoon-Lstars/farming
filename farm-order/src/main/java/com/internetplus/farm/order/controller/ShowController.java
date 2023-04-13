package com.internetplus.farm.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.internetplus.farm.order.dao.ShowDao;
import com.internetplus.farm.order.entity.DetailEntity;
import java.util.ArrayList;
import java.util.Arrays;
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

        return R.ok().put("list", list);
    }


    /**
     * 订单查询
     */
    @RequestMapping("/search")
    public R search(@Param("orderId")Integer orderId) {
        List<ShowEntity> list = new ArrayList<>();
        for (ShowEntity showEntity : showDao.generateList()) {
            if(showEntity.getOrderId().equals(orderId)) {
                list.add(showEntity);
            }
        }
        R r = new R();
        r.put("list",list);
        return r;
    }

}
