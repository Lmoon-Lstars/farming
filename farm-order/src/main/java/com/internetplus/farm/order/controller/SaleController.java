package com.internetplus.farm.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.internetplus.common.utils.Query;
import com.internetplus.farm.order.entity.ShowEntity;
import com.internetplus.farm.order.service.DetailService;
import com.internetplus.farm.order.service.ShowService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.internetplus.farm.order.entity.SaleEntity;
import com.internetplus.farm.order.service.SaleService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.R;



/**
 *
 *
 * @author wrk
 * @email 1181153997@gmail.com
 * @date 2023-04-20 23:47:06
 */
@RestController
@RequestMapping("order/sale")
public class SaleController {
    @Autowired
    private SaleService saleService;
    @Autowired
    private ShowService showService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = saleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{saleId}")
    public R info(@PathVariable("saleId") Integer saleId){
		SaleEntity sale = saleService.getById(saleId);

        return R.ok().put("sale", sale);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(){
        List<ShowEntity> list = showService.list();
        for (ShowEntity showEntity : list) {
            if(showEntity.getOrderStatus() != 3) {
                SaleEntity sale = new SaleEntity();
                sale.setOrderId(showEntity.getOrderId());
                sale.setProductName(showEntity.getProductName());
                sale.setProductCnt(showEntity.getProductCnt());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(showEntity.getCreateTime());
                sale.setYear(calendar.get(Calendar.YEAR));
                sale.setMonth(calendar.get(Calendar.MONTH) + 1);
                sale.setDay(calendar.get(Calendar.DATE));
                sale.setSalePrice(showEntity.getProductPrice().multiply(new BigDecimal(showEntity.getProductCnt())));
                saleService.save(sale);
            }
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SaleEntity sale){
		saleService.updateById(sale);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] saleIds){
		saleService.removeByIds(Arrays.asList(saleIds));

        return R.ok();
    }
    /**
     * 销量查询
     */
    @RequestMapping("/stats")
    public R stats(@RequestParam("year")Integer year,@RequestParam("month")Integer month,@RequestParam("day")Integer day) {
        QueryWrapper<SaleEntity> queryWrapper = new QueryWrapper<>();
        if(year != null) {
            queryWrapper.eq("year",year);
        }
        if(month != null) {
            queryWrapper.eq("month",month);
        }
        if(day != null) {
            queryWrapper.eq("day",day);
        }
        List<SaleEntity> list = saleService.list(queryWrapper);
        Map<String,BigDecimal> map = new HashMap<>();
        for (SaleEntity saleEntity : list) {
            map.put(saleEntity.getProductName(),map.getOrDefault(saleEntity.getProductName(),new BigDecimal(0)).add(saleEntity.getSalePrice()));
        }
        R r = new R();
        return r.put("map",map);
    }


}
