package com.internetplus.farm.order.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.Query;

import com.internetplus.farm.order.dao.MasterDao;
import com.internetplus.farm.order.entity.MasterEntity;
import com.internetplus.farm.order.service.MasterService;


@Service("masterService")
public class MasterServiceImpl extends ServiceImpl<MasterDao, MasterEntity> implements MasterService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MasterEntity> page = this.page(
                new Query<MasterEntity>().getPage(params),
                new QueryWrapper<MasterEntity>()
        );

        return new PageUtils(page);
    }

}