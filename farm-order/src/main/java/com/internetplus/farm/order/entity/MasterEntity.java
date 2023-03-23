package com.internetplus.farm.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 订单主表
 * 
 * @author lcx
 * @email 1181153997@gmail.com
 * @date 2023-03-13 08:47:57
 */
@Data
@TableName("order_master")
public class MasterEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单ID
	 */
	@TableId
	private Integer orderId;
	/**
	 * 订单编号 yyyymmddnnnnnnnn
	 */
	private Long orderSn;
	/**
	 * 供应商id
	 */
	private Integer supplierId;
	/**
	 * 下单人ID
	 */
	private Integer customerId;
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
	 * 支付方式:1现金,2余额,3网银,4支付宝,5微信
	 */
	private Integer paymentMethod;
	/**
	 * 订单金额
	 */
	private BigDecimal orderMoney;
	/**
	 * 优惠金额
	 */
	private BigDecimal districtMoney;
	/**
	 * 运费金额
	 */
	private BigDecimal shippingMoney;
	/**
	 * 支付金额
	 */
	private BigDecimal paymentMoney;
	/**
	 * 快递公司名称
	 */
	private String shippingCompName;
	/**
	 * 快递单号
	 */
	private String shippingSn;
	/**
	 * 下单时间
	 */
	private Date createTime;
	/**
	 * 发货时间
	 */
	private Date shippingTime;
	/**
	 * 支付时间
	 */
	private Date payTime;
	/**
	 * 收货时间
	 */
	private Date receiveTime;
	/**
	 * 订单状态(1未支付，2已支付，3已发货，4运输中，5已送达，6已完成)
	 */
	private Integer orderStatus;
	/**
	 * 发票抬头
	 */
	private String invoiceTitle;
	/**
	 * 最后修改时间
	 */
	private Date modifiedTime;

}
