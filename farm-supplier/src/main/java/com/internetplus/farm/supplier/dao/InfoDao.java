package com.internetplus.farm.supplier.dao;

import com.internetplus.farm.supplier.entity.InfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 用户信息表
 * 
 * @author lcx
 * @email 1181153997@qq.com
 * @date 2023-03-12 21:33:27
 */
@Mapper
@Repository
public interface InfoDao extends BaseMapper<InfoEntity> {
	
}
