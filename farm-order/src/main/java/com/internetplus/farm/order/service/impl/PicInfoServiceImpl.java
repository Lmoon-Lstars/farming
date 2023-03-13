package com.internetplus.farm.order.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.Query;

import com.internetplus.farm.order.dao.PicInfoDao;
import com.internetplus.farm.order.entity.PicInfoEntity;
import com.internetplus.farm.order.service.PicInfoService;


@Service("picInfoService")
public class PicInfoServiceImpl extends ServiceImpl<PicInfoDao, PicInfoEntity> implements PicInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PicInfoEntity> page = this.page(
                new Query<PicInfoEntity>().getPage(params),
                new QueryWrapper<PicInfoEntity>()
        );

        return new PageUtils(page);
    }

}