package com.internetplus.farm.order.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.internetplus.farm.order.entity.CommentInfoEntity;
import com.internetplus.farm.order.service.CommentInfoService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.R;



/**
 * 订单评论信息表
 *
 * @author lcx
 * @email 1181153997@gmail.com
 * @date 2023-03-13 08:47:57
 */
@RestController
@RequestMapping("order/commentinfo")
public class CommentInfoController {
    @Autowired
    private CommentInfoService commentInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = commentInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{commentId}")
    public R info(@PathVariable("commentId") Integer commentId){
		CommentInfoEntity commentInfo = commentInfoService.getById(commentId);

        return R.ok().put("commentInfo", commentInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CommentInfoEntity commentInfo){
		commentInfoService.save(commentInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CommentInfoEntity commentInfo){
		commentInfoService.updateById(commentInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] commentIds){
		commentInfoService.removeByIds(Arrays.asList(commentIds));

        return R.ok();
    }

}
