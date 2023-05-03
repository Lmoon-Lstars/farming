package com.internetplus.farm.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.internetplus.common.utils.Query;
import com.internetplus.farm.order.entity.MasterEntity;
import com.internetplus.farm.order.entity.ShowEntity;
import com.internetplus.farm.order.service.MasterService;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.internetplus.farm.order.entity.StatsEntity;
import com.internetplus.farm.order.service.StatsService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.R;



/**
 *
 *
 * @author wrk
 * @email 1181153997@gmail.com
 * @date 2023-04-23 10:13:55
 */
@RestController
@RequestMapping("order/stats")
public class StatsController {
    @Autowired
    private StatsService statsService;

    @Autowired
    private MasterService masterService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam(value = "showNum")String showNum){
        QueryWrapper<StatsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("order_num");
        queryWrapper.last("limit " + showNum);
        return R.ok().put("list",statsService.list(queryWrapper));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{statsId}")
    public R info(@PathVariable("statsId") Integer statsId){
		StatsEntity stats = statsService.getById(statsId);

        return R.ok().put("stats", stats);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(){
        List<MasterEntity> list = masterService.list();
        for (MasterEntity master : list) {
            QueryWrapper<StatsEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("customer_id",master.getCustomerId());
            StatsEntity stats = new StatsEntity();
            if(statsService.list(queryWrapper).size() == 0) {
                stats.setCustomerId(master.getCustomerId());
                stats.setOrderNum(1);
                statsService.save(stats);
            } else {
                stats = statsService.getOne(queryWrapper);
                stats.setOrderNum(stats.getOrderNum() + 1);
                statsService.updateById(stats);
            }
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody StatsEntity stats){
		statsService.updateById(stats);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] statsIds){
		statsService.removeByIds(Arrays.asList(statsIds));

        return R.ok();
    }

}
