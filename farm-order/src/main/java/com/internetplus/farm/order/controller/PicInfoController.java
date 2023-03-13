package com.internetplus.farm.order.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.internetplus.farm.order.entity.PicInfoEntity;
import com.internetplus.farm.order.service.PicInfoService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.R;



/**
 * 订单图片信息表
 *
 * @author lcx
 * @email 1181153997@gmail.com
 * @date 2023-03-13 08:47:57
 */
@RestController
@RequestMapping("order/picinfo")
public class PicInfoController {
    @Autowired
    private PicInfoService picInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = picInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{orderPicId}")
    public R info(@PathVariable("orderPicId") Integer orderPicId){
		PicInfoEntity picInfo = picInfoService.getById(orderPicId);

        return R.ok().put("picInfo", picInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PicInfoEntity picInfo){
		picInfoService.save(picInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PicInfoEntity picInfo){
		picInfoService.updateById(picInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] orderPicIds){
		picInfoService.removeByIds(Arrays.asList(orderPicIds));

        return R.ok();
    }

}
