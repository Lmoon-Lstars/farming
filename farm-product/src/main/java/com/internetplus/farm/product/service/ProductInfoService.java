package com.internetplus.farm.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.farm.order.entity.ProductInfoEntity;

import java.util.Map;

/**
 * 商品信息表
 *
 * @author lcx
 * @email 1181153997@gmail.com
 * @date 2023-03-13 08:50:57
 */
public interface ProductInfoService extends IService<ProductInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

