package com.internetplus.farm.order.dao;

import com.internetplus.farm.order.entity.ProductInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品信息表
 * 
 * @author lcx
 * @email 1181153997@gmail.com
 * @date 2023-03-13 08:50:57
 */
@Mapper
public interface ProductInfoDao extends BaseMapper<ProductInfoEntity> {
	
}
