package com.internetplus.farm.order.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.Query;

import com.internetplus.farm.order.dao.SaleDao;
import com.internetplus.farm.order.entity.SaleEntity;
import com.internetplus.farm.order.service.SaleService;


@Service("saleService")
public class SaleServiceImpl extends ServiceImpl<SaleDao, SaleEntity> implements SaleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SaleEntity> page = this.page(
                new Query<SaleEntity>().getPage(params),
                new QueryWrapper<SaleEntity>()
        );

        return new PageUtils(page);
    }

}