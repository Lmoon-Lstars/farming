package com.internetplus.farm.supplier.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户信息表
 * 
 * @author lcx
 * @email 1181153997@qq.com
 * @date 2023-03-12 21:33:27
 */
@Data
@TableName("supplier_info")
public class InfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键ID
	 */
	@TableId
	private Integer supplierInfoId;
	/**
	 * supplier_login表的自增ID
	 */
	private Integer supplierId;
	/**
	 * 用户真实姓名
	 */
	private String supplierName;
	/**
	 * 证件类型：1 身份证,2军官证,3护照
	 */
	private Integer identityCardType;
	/**
	 * 证件号码
	 */
	private String identityCardNo;
	/**
	 * 手机号
	 */
	private Integer mobilePhone;
	/**
	 * 邮箱
	 */
	private String supplierEmail;
	/**
	 * 性别
	 */
	private String gender;
	/**
	 * 注册时间
	 */
	private Date registerTime;
	/**
	 * 最后修改时间
	 */
	private Date modifiedTime;

}
