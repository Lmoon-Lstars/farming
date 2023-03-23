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
 * @date 2023-03-22 14:52:46
 */
@Data
@TableName("user_cupon")
public class CuponEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String cuponName;
	/**
	 * 使用门槛
	 */
	private Integer minPoint;
	/**
	 * 
	 */
	private Integer cuponAmount;
	/**
	 * 
	 */
	private Date startTime;
	/**
	 * 
	 */
	private Date endTime;
	/**
	 * 
	 */
	private Integer userInfoId;
	/**
	 * 
	 */
	private Integer cuponStatu;
	/**
	 * 
	 */
	private Integer cuponType;
	/**
	 * 
	 */
	private String cuponRules;

}
