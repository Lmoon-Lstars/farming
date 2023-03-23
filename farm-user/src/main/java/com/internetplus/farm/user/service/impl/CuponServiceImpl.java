package com.internetplus.farm.user.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.Query;

import com.internetplus.farm.user.dao.CuponDao;
import com.internetplus.farm.user.entity.CuponEntity;
import com.internetplus.farm.user.service.CuponService;


@Service("cuponService")
public class CuponServiceImpl extends ServiceImpl<CuponDao, CuponEntity> implements CuponService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CuponEntity> page = this.page(
                new Query<CuponEntity>().getPage(params),
                new QueryWrapper<CuponEntity>()
        );

        return new PageUtils(page);
    }

}