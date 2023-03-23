package com.internetplus.farm.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.farm.user.entity.CuponEntity;

import java.util.Map;

/**
 * 
 *
 * @author wrk
 * @email 1181153997@gmail.com
 * @date 2023-03-22 14:52:46
 */
public interface CuponService extends IService<CuponEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

