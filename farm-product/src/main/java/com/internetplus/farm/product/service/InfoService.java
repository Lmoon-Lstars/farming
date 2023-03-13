package com.internetplus.farm.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.farm.product.entity.InfoEntity;

import java.util.Map;

/**
 * 商品信息表
 *
 * @author lcx
 * @email 1181153997@gmail.com
 * @date 2023-03-13 16:42:29
 */
public interface InfoService extends IService<InfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

