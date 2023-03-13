package com.internetplus.farm.supplier.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.internetplus.farm.supplier.entity.LoginEntity;
import com.internetplus.farm.supplier.service.LoginService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.R;



/**
 * 用户登陆表
 *
 * @author lcx
 * @email 1181153997@qq.com
 * @date 2023-03-12 21:33:27
 */
@RestController
@RequestMapping("supplier/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("supplier:login:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = loginService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{supplierId}")
    //@RequiresPermissions("supplier:login:info")
    public R info(@PathVariable("supplierId") Integer supplierId){
		LoginEntity login = loginService.getById(supplierId);

        return R.ok().put("login", login);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("supplier:login:save")
    public R save(@RequestBody LoginEntity login){
		loginService.save(login);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("supplier:login:update")
    public R update(@RequestBody LoginEntity login){
		loginService.updateById(login);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("${moduleNamez}:login:delete")
    public R delete(@RequestBody Integer[] supplierIds){
		loginService.removeByIds(Arrays.asList(supplierIds));

        return R.ok();
    }

}
