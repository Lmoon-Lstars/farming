package com.internetplus.farm.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.internetplus.common.utils.PageUtils;
import com.internetplus.farm.order.entity.MasterEntity;

import com.mysql.cj.x.protobuf.MysqlxCrud.Order;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单主表
 *
 * @author lcx
 * @email 1181153997@gmail.com
 * @date 2023-03-13 08:47:57
 */
public interface MasterService extends IService<MasterEntity> {
    void saveOrder(MasterEntity master);
    PageUtils queryPage(Map<String, Object> params);
    List<MasterEntity> findByConditions (Integer orderId,Integer customerId, String shippingUser,String address, Date createTime, String phoneNumber);
}

