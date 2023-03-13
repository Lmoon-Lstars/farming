package com.internetplus.farm.supplier.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.internetplus.farm.supplier.entity.InfoEntity;
import com.internetplus.farm.supplier.service.InfoService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.R;



/**
 * 用户信息表
 *
 * @author lcx
 * @email 1181153997@qq.com
 * @date 2023-03-12 21:33:27
 */
@RestController
@RequestMapping("supplier/info")
public class InfoController {
    @Autowired
    private InfoService infoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("supplier:info:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = infoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{supplierInfoId}")
    //@RequiresPermissions("supplier:info:info")
    public R info(@PathVariable("supplierInfoId") Integer supplierInfoId){
		InfoEntity info = infoService.getById(supplierInfoId);

        return R.ok().put("info", info);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("supplier:info:save")
    public R save(@RequestBody InfoEntity info){
		infoService.save(info);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("supplier:info:update")
    public R update(@RequestBody InfoEntity info){
		infoService.updateById(info);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("${moduleNamez}:info:delete")
    public R delete(@RequestBody Integer[] supplierInfoIds){
		infoService.removeByIds(Arrays.asList(supplierInfoIds));

        return R.ok();
    }

}
