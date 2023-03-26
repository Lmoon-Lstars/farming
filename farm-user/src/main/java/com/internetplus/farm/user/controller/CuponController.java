package com.internetplus.farm.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Data;
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

    /**
     * 查看优惠券
     */
    @RequestMapping("/listCupon")
    public List listCupon(@RequestParam("userId")String userId, @RequestParam("status")String status) {
        //0:未开始，1:过期，2:有效
        Date date = new Date();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_info_id",userId);
        List<CuponEntity> cuponList = cuponService.list(queryWrapper);
        for (CuponEntity cuponEntity : cuponList) {
            if(cuponEntity.getStartTime().after(date)) { //未到开始时间
                cuponEntity.setCuponStatu(0);
            } else {
                if(cuponEntity.getEndTime().before(date)) { //过期
                    cuponEntity.setCuponStatu(1);
                } else {
                    cuponEntity.setCuponStatu(2);
                }
            }
            cuponService.updateById(cuponEntity);
        }
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_info_id",userId);
        wrapper.eq("cupon_statu",Integer.valueOf(status));
        List<CuponEntity> list = cuponService.list(wrapper);
        return list;
    }

}
