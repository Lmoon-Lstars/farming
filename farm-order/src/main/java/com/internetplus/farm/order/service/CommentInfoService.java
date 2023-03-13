package com.internetplus.farm.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.farm.order.entity.CommentInfoEntity;

import java.util.Map;

/**
 * 订单评论信息表
 *
 * @author lcx
 * @email 1181153997@gmail.com
 * @date 2023-03-13 08:47:57
 */
public interface CommentInfoService extends IService<CommentInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

