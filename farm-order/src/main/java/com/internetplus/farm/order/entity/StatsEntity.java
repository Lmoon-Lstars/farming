package com.internetplus.farm.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author wrk
 * @email 1181153997@gmail.com
 * @date 2023-04-23 10:13:55
 */
@Data
@TableName("order_stats")
public class StatsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer statsId;
	/**
	 * 
	 */
	private Integer customerId;
	/**
	 * 
	 */
	private Integer orderNum;

}
