package com.internetplus.farm.order.service.impl;

import java.util.Date;
import java.util.List;
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
    public void saveOrder(MasterEntity master) {
        master.setCreateTime(new Date());
        master.setModifiedTime(new Date());
        this.save(master);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MasterEntity> page = this.page(
                new Query<MasterEntity>().getPage(params),
                new QueryWrapper<MasterEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<MasterEntity> findByConditions (Integer orderId,Integer customerId, String shippingUser,String address, Date createTime, String phoneNumber){
        QueryWrapper<MasterEntity> queryWrapper = new QueryWrapper<>();
        if(orderId != null) {
            queryWrapper.like("order_id",orderId);
        }
        if(customerId != null) {
            queryWrapper.like("customer_id",customerId);
        }
        if(shippingUser != null) {
            queryWrapper.like("shipping_user",shippingUser);
        }
        if(address != null) {
            queryWrapper.like("address",address);
        }
        if(createTime != null) {
            queryWrapper.like("create_time",createTime);
        }
        if(phoneNumber != null) {
            queryWrapper.like("phone_number",phoneNumber);
        }
        return baseMapper.selectList(queryWrapper);
    }

}
