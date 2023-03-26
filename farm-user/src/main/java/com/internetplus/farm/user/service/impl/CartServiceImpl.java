package com.internetplus.farm.user.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.Query;

import com.internetplus.farm.user.dao.CartDao;
import com.internetplus.farm.user.entity.CartEntity;
import com.internetplus.farm.user.service.CartService;


@Service("cartService")
public class CartServiceImpl extends ServiceImpl<CartDao, CartEntity> implements CartService {



    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CartEntity> page = this.page(
                new Query<CartEntity>().getPage(params),
                new QueryWrapper<CartEntity>()
        );

        return new PageUtils(page);
    }

}