package com.internetplus.farm.order.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.Query;

import com.internetplus.farm.order.dao.ShowDao;
import com.internetplus.farm.order.entity.ShowEntity;
import com.internetplus.farm.order.service.ShowService;


@Service("showService")
public class ShowServiceImpl extends ServiceImpl<ShowDao, ShowEntity> implements ShowService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ShowEntity> page = this.page(
                new Query<ShowEntity>().getPage(params),
                new QueryWrapper<ShowEntity>()
        );

        return new PageUtils(page);
    }

}