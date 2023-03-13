package com.internetplus.farm.supplier.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.internetplus.farm.supplier.entity.LoginLogEntity;
import com.internetplus.farm.supplier.service.LoginLogService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.R;



/**
 * 用户登录日志表
 *
 * @author lcx
 * @email 1181153997@qq.com
 * @date 2023-03-12 21:33:27
 */
@RestController
@RequestMapping("supplier/loginlog")
public class LoginLogController {
    @Autowired
    private LoginLogService loginLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("supplier:loginlog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = loginLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{loginId}")
    //@RequiresPermissions("supplier:loginlog:info")
    public R info(@PathVariable("loginId") Integer loginId){
		LoginLogEntity loginLog = loginLogService.getById(loginId);

        return R.ok().put("loginLog", loginLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("supplier:loginlog:save")
    public R save(@RequestBody LoginLogEntity loginLog){
		loginLogService.save(loginLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("supplier:loginlog:update")
    public R update(@RequestBody LoginLogEntity loginLog){
		loginLogService.updateById(loginLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("${moduleNamez}:loginlog:delete")
    public R delete(@RequestBody Integer[] loginIds){
		loginLogService.removeByIds(Arrays.asList(loginIds));

        return R.ok();
    }

}
