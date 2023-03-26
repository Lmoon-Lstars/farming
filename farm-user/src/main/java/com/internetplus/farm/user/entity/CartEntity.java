package com.internetplus.farm.user.entity;

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
 * @date 2023-03-25 13:14:18
 */
@Data
@TableName("user_cart")
public class CartEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 购物车id
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private Integer productId;
	/**
	 * 
	 */
	private Integer userId;
	/**
	 * 
	 */
	private Integer quantity;
	/**
	 * 
	 */
	private Integer selected;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;

}
