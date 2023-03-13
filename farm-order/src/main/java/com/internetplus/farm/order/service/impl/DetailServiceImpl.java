package com.internetplus.farm.order.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.Query;

import com.internetplus.farm.order.dao.DetailDao;
import com.internetplus.farm.order.entity.DetailEntity;
import com.internetplus.farm.order.service.DetailService;


@Service("detailService")
public class DetailServiceImpl extends ServiceImpl<DetailDao, DetailEntity> implements DetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DetailEntity> page = this.page(
                new Query<DetailEntity>().getPage(params),
                new QueryWrapper<DetailEntity>()
        );

        return new PageUtils(page);
    }

}