package com.internetplus.farm.supplier.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户登陆表
 * 
 * @author lcx
 * @email 1181153997@qq.com
 * @date 2023-03-12 21:33:27
 */
@Data
@TableName("supplier_login")
public class LoginEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@TableId
	private Integer supplierId;
	/**
	 * 用户登陆名
	 */
	private String loginName;
	/**
	 * md5加密的密码
	 */
	private String password;
	/**
	 * 用户状态（0锁定，1未锁定）
	 */
	private Integer userStats;
	/**
	 * 最后修改时间
	 */
	private Date modifiedTime;

}
