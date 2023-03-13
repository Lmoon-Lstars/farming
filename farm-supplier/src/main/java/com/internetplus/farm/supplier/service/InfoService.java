package com.internetplus.farm.supplier.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.farm.supplier.entity.InfoEntity;

import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * 用户信息表
 *
 * @author lcx
 * @email 1181153997@qq.com
 * @date 2023-03-12 21:33:27
 */
@Service
public interface InfoService extends IService<InfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

