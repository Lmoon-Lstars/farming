package com.internetplus.farm.supplier.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.Query;

import com.internetplus.farm.supplier.dao.LoginDao;
import com.internetplus.farm.supplier.entity.LoginEntity;
import com.internetplus.farm.supplier.service.LoginService;


@Service("loginService")
public class LoginServiceImpl extends ServiceImpl<LoginDao, LoginEntity> implements LoginService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LoginEntity> page = this.page(
                new Query<LoginEntity>().getPage(params),
                new QueryWrapper<LoginEntity>()
        );

        return new PageUtils(page);
    }

}