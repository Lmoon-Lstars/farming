package com.internetplus.farm.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *
 *
 * @author wrk
 * @email 1181153997@gmail.com
 * @date 2023-04-12 15:23:24
 */
@Data
@TableName("order_show")
public class ShowEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单表ID
	 */
	@TableId
	private Integer orderId;
	/**
	 * 下单人ID
	 */
	private Integer customerId;
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
	 * 收货人姓名
	 */
	private String shippingUser;
	/**
	 * 收货人所在省
	 */
	private String province;
	/**
	 * 收货人所在市
	 */
	private String city;
	/**
	 * 收货人所在区
	 */
	private String district;
	/**
	 * 收货人详细地址
	 */
	private String address;
	/**
	 * 订单金额
	 */
	private BigDecimal orderMoney;
	/**
	 * 支付金额
	 */
	private BigDecimal paymentMoney;
	/**
	 * 订单状态(1未支付，2已支付，3已发货，4运输中，5已送达，6已完成)
	 */
	private Integer orderStatus;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 手机号码
	 */
	private String phoneNumber;
	/**
	 * 下单时间
	 */
	private Date createTime;

}
