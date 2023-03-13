package com.internetplus.farm.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 订单评论信息表
 * 
 * @author lcx
 * @email 1181153997@gmail.com
 * @date 2023-03-13 08:47:57
 */
@Data
@TableName("order_comment_info")
public class CommentInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 评论id
	 */
	@TableId
	private Integer commentId;
	/**
	 * 评论内容
	 */
	private String content;
	/**
	 * 评论者id
	 */
	private Integer userId;
	/**
	 * 评论商品id
	 */
	private Integer productId;
	/**
	 * 评论父id
	 */
	private Integer pid;
	/**
	 * 被评论人id
	 */
	private Integer replyUserId;

}
