package com.internetplus.farm.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.farm.user.entity.CartEntity;

import java.util.Map;

/**
 * 
 *
 * @author wrk
 * @email 1181153997@gmail.com
 * @date 2023-03-25 13:14:18
 */
public interface CartService extends IService<CartEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

