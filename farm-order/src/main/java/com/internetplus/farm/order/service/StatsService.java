package com.internetplus.farm.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.farm.order.entity.StatsEntity;

import java.util.Map;

/**
 * 
 *
 * @author wrk
 * @email 1181153997@gmail.com
 * @date 2023-04-23 10:13:55
 */
public interface StatsService extends IService<StatsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

