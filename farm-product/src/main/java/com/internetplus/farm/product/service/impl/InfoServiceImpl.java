package com.internetplus.farm.product.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.common.utils.Query;

import com.internetplus.farm.product.dao.InfoDao;
import com.internetplus.farm.product.entity.InfoEntity;
import com.internetplus.farm.product.service.InfoService;


@Service("infoService")
public class InfoServiceImpl extends ServiceImpl<InfoDao, InfoEntity> implements InfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InfoEntity> page = this.page(
                new Query<InfoEntity>().getPage(params),
                new QueryWrapper<InfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveInfo(InfoEntity info) {
        info.setPriceDifference(new BigDecimal("0"));
        info.setModifiedTime(new Date());
        info.setIndate(new Date());
        System.out.println("免费供应数目为："+info.getFreeNum());
        info.setTotalNum(info.getFreeNum()+info.getSupplyNum());
        this.save(info);
    }

    @Override
    public void updateInfo(InfoEntity info) {
        info.setModifiedTime(new Date());
        this.updateById(info);
    }
}