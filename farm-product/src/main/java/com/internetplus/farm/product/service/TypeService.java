package com.internetplus.farm.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.farm.product.entity.TypeEntity;

import java.util.Map;

/**
 * 
 *
 * @author wrk
 * @email 1181153997@gmail.com
 * @date 2023-04-03 14:19:56
 */
public interface TypeService extends IService<TypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

