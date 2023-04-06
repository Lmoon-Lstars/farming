package com.internetplus.farm.product.entity;

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
 * @date 2023-04-03 14:19:56
 */
@Data
@TableName("product_type")
public class TypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键自增id
	 */
	@TableId
	private Integer typeId;
	/**
	 * 类型编号
	 */
	private Integer typeCode;
	/**
	 * 类型名称
	 */
	private String typeName;

}
