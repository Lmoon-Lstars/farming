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
 * @date 2023-04-02 16:25:55
 */
@Data
@TableName("user_banner")
public class BannerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增id
	 */
	@TableId
	private Integer id;
	/**
	 * 图片url
	 */
	private String imageurl;

}
