package com.internetplus.farm.supplier.dao;

import com.internetplus.farm.supplier.entity.LoginLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 用户登录日志表
 * 
 * @author lcx
 * @email 1181153997@qq.com
 * @date 2023-03-12 21:33:27
 */
@Mapper
@Repository
public interface LoginLogDao extends BaseMapper<LoginLogEntity> {
	
}
