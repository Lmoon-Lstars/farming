package com.internetplus.farm.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 订单图片信息表
 * 
 * @author lcx
 * @email 1181153997@gmail.com
 * @date 2023-03-13 08:47:57
 */
@Data
@TableName("order_pic_info")
public class PicInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单商品图片ID
	 */
	@TableId
	private Integer orderPicId;
	/**
	 * 订单ID
	 */
	private Integer orderId;
	/**
	 * 图片URL
	 */
	private String picUrl;
	/**
	 * 是否主图:0.非主图1.主图
	 */
	private Integer isMaster;
	/**
	 * 图片排序
	 */
	private Integer picOrder;
	/**
	 * 图片是否有效:0无效 1有效
	 */
	private Integer picStatus;
	/**
	 * 最后修改时间
	 */
	private Date modifiedTime;

}
