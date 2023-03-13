package com.internetplus.farm.order.dao;

import com.internetplus.farm.order.entity.MasterEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单主表
 * 
 * @author lcx
 * @email 1181153997@gmail.com
 * @date 2023-03-13 08:47:57
 */
@Mapper
public interface MasterDao extends BaseMapper<MasterEntity> {
	
}
