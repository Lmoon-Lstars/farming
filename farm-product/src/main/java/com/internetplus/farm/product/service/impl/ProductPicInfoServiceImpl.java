package com.internetplus.farm.order.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.Query;

import com.internetplus.farm.order.dao.ProductPicInfoDao;
import com.internetplus.farm.order.entity.ProductPicInfoEntity;
import com.internetplus.farm.order.service.ProductPicInfoService;


@Service("productPicInfoService")
public class ProductPicInfoServiceImpl extends ServiceImpl<ProductPicInfoDao, ProductPicInfoEntity> implements ProductPicInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductPicInfoEntity> page = this.page(
                new Query<ProductPicInfoEntity>().getPage(params),
                new QueryWrapper<ProductPicInfoEntity>()
        );

        return new PageUtils(page);
    }

}