package com.internetplus.farm.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 订单项表
 * 
 * @author lcx
 * @email 1181153997@gmail.com
 * @date 2023-03-13 08:47:57
 */
@Data
@TableName("order_detail")
public class DetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键ID,订单详情表ID
	 */
	@TableId
	private Integer orderDetailId;
	/**
	 * 订单表ID
	 */
	private Integer orderId;
	/**
	 * 订单商品ID
	 */
	private Integer productId;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 购买商品份数
	 */
	private Integer productCnt;
	/**
	 * 购买商品单价
	 */
	private BigDecimal productPrice;
	/**
	 * 商品重量
	 */
	private Double weight;
	/**
	 * 最后修改时间
	 */
	private Date modifiedTime;

}
