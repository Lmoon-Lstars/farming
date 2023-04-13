package com.internetplus.farm.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @date 2023-03-22 14:52:47
 */
@Data
@TableName("user_info")
public class InfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId(type = IdType.AUTO)
	private Integer userInfoId;
	/**
	 *
	 */
	private String avatarUrl;
	/**
	 *
	 */
	private String openId;
	/**
	 *
	 */
	private String plantPoint;
	/**
	 *
	 */
	private String mobilePhone;
	/**
	 *
	 */
	private String nickName;
	/**
	 *
	 */
	private Date registerTime;
	/**
	 *
	 */
	private Integer userState;

}
