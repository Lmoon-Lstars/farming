package com.internetplus.farm.supplier.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户登录日志表
 * 
 * @author lcx
 * @email 1181153997@qq.com
 * @date 2023-03-12 21:33:27
 */
@Data
@TableName("supplier_login_log")
public class LoginLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 登录日志ID
	 */
	@TableId
	private Integer loginId;
	/**
	 * 登录用户ID
	 */
	private Integer supplierId;
	/**
	 * 用户登录时间
	 */
	private Date loginTime;
	/**
	 * 登录IP
	 */
	private Integer loginIp;
	/**
	 * 登录类型:0未成功 1成功
	 */
	private Integer loginType;

}
