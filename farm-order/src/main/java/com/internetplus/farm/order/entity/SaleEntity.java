package com.internetplus.farm.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 *
 *
 * @author wrk
 * @email 1181153997@gmail.com
 * @date 2023-04-20 23:47:06
 */
@Data
@TableName("order_sale")
public class SaleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer saleId;
	/**
	 *
	 */
	private String productName;
	/**
	 *
	 */
	private Integer orderId;
	/**
	 *
	 */
	private Integer productCnt;
	/**
	 *
	 */
	private Integer year;
	/**
	 *
	 */
	private Integer month;
	/**
	 *
	 */
	private Integer day;
	/**
	 *
	 */
	private BigDecimal salePrice;

}
