package com.internetplus.farm.order.dao;

import com.internetplus.farm.order.entity.ShowEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 *
 *
 * @author wrk
 * @email 1181153997@gmail.com
 * @date 2023-04-12 15:23:24
 */
@Mapper
public interface ShowDao extends BaseMapper<ShowEntity> {
  @Select("select d.order_id,m.customer_id,d.product_name,d.product_cnt,d.product_price,d.weight,m.shipping_user,m.province,m.city,m.district,m.address,m.order_money,m.payment_money,m.order_status,m.remark,m.phone_number from order_detail d, order_master m where d.order_id=m.order_id")
  List<ShowEntity> generateList();
}
