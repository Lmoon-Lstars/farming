package com.internetplus.farm.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.internetplus.farm.user.entity.CuponEntity;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.internetplus.farm.user.entity.CuponModuleEntity;
import com.internetplus.farm.user.service.CuponModuleService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.R;



/**
 *
 *
 * @author wrk
 * @email 1181153997@gmail.com
 * @date 2023-05-03 16:15:07
 */
@RestController
@RequestMapping("user/cuponmodule")
public class CuponModuleController {
    @Autowired
    private CuponModuleService cuponModuleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cuponModuleService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 分发优惠券
     */
    @RequestMapping("/send")
    public R send(@RequestParam("cuponName")String cuponName,@RequestParam("minPoint")Integer minPoint,
        @RequestParam("cuponAmount")Integer cuponAmount,@RequestParam("startTime") Date startTime,
        @RequestParam("endTime")Date endTime,@RequestParam("cuponRules")String cuponRules
    ) {
        CuponModuleEntity cuponModule = new CuponModuleEntity();
        cuponModule.setCuponName(cuponName);
        cuponModule.setMinPoint(minPoint);
        cuponModule.setCuponAmount(cuponAmount);
        cuponModule.setStartTime(startTime);
        cuponModule.setEndTime(endTime);
        cuponModule.setCuponRules(cuponRules);
        cuponModuleService.save(cuponModule);
        R r = new R();
        r.put("cupon",cuponModule);
        return r;
    }

    /**
     * 展示优惠券
     */
    @RequestMapping("/show")
    public R show() {
        List<CuponModuleEntity> list = cuponModuleService.list();
        R r = new R();
        r.put("list",list);
        return r;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		CuponModuleEntity cuponModule = cuponModuleService.getById(id);

        return R.ok().put("cuponModule", cuponModule);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CuponModuleEntity cuponModule){
		cuponModuleService.save(cuponModule);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CuponModuleEntity cuponModule){
		cuponModuleService.updateById(cuponModule);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		cuponModuleService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
