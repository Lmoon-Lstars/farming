package com.internetplus.farm.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.farm.order.entity.SaleEntity;

import java.util.Map;

/**
 * 
 *
 * @author wrk
 * @email 1181153997@gmail.com
 * @date 2023-04-20 23:47:06
 */
public interface SaleService extends IService<SaleEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

