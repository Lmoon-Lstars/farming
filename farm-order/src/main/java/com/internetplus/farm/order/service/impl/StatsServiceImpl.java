package com.internetplus.farm.order.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.Query;

import com.internetplus.farm.order.dao.StatsDao;
import com.internetplus.farm.order.entity.StatsEntity;
import com.internetplus.farm.order.service.StatsService;


@Service("statsService")
public class StatsServiceImpl extends ServiceImpl<StatsDao, StatsEntity> implements StatsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StatsEntity> page = this.page(
                new Query<StatsEntity>().getPage(params),
                new QueryWrapper<StatsEntity>()
        );

        return new PageUtils(page);
    }

}