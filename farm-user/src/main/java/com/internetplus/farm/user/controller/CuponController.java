package com.internetplus.farm.user.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.internetplus.farm.user.entity.CuponEntity;
import com.internetplus.farm.user.service.CuponService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.R;



/**
 * 
 *
 * @author wrk
 * @email 1181153997@gmail.com
 * @date 2023-03-22 14:52:46
 */
@RestController
@RequestMapping("user/cupon")
public class CuponController {
    @Autowired
    private CuponService cuponService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cuponService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		CuponEntity cupon = cuponService.getById(id);

        return R.ok().put("cupon", cupon);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CuponEntity cupon){
		cuponService.save(cupon);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CuponEntity cupon){
		cuponService.updateById(cupon);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		cuponService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
