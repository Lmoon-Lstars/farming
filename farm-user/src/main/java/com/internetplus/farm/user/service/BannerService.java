package com.internetplus.farm.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.farm.user.entity.BannerEntity;

import java.util.Map;

/**
 * 
 *
 * @author wrk
 * @email 1181153997@gmail.com
 * @date 2023-04-02 16:25:55
 */
public interface BannerService extends IService<BannerEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

