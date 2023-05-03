package com.internetplus.farm.user.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.Query;

import com.internetplus.farm.user.dao.CuponModuleDao;
import com.internetplus.farm.user.entity.CuponModuleEntity;
import com.internetplus.farm.user.service.CuponModuleService;


@Service("cuponModuleService")
public class CuponModuleServiceImpl extends ServiceImpl<CuponModuleDao, CuponModuleEntity> implements CuponModuleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CuponModuleEntity> page = this.page(
                new Query<CuponModuleEntity>().getPage(params),
                new QueryWrapper<CuponModuleEntity>()
        );

        return new PageUtils(page);
    }

}