package com.internetplus.farm.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.farm.product.entity.PicInfoEntity;

import java.util.Map;

/**
 * 商品图片信息表
 *
 * @author lcx
 * @email 1181153997@gmail.com
 * @date 2023-03-13 15:58:55
 */
public interface PicInfoService extends IService<PicInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

