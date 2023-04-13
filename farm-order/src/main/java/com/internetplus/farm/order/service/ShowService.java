package com.internetplus.farm.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.farm.order.entity.ShowEntity;

import java.util.Map;

/**
 * 
 *
 * @author wrk
 * @email 1181153997@gmail.com
 * @date 2023-04-12 15:23:24
 */
public interface ShowService extends IService<ShowEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

