package com.internetplus.farm.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品信息表
 * 
 * @author lcx
 * @email 1181153997@gmail.com
 * @date 2023-03-13 08:50:57
 */
@Data
@TableName("product_info")
public class ProductInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品ID
	 */
	@TableId
	private Integer productId;
	/**
	 * 商品编码
	 */
	private String productCode;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 价格差
	 */
	private BigDecimal priceDifference;
	/**
	 * 商品销售价格
	 */
	private BigDecimal price;
	/**
	 * 商品供应商id
	 */
	private Integer supplierId;
	/**
	 * 免费供应份数
	 */
	private Integer freeNum;
	/**
	 * 正常供应份数
	 */
	private Integer supplyNum;
	/**
	 * 总供应份数
	 */
	private Integer totalNum;
	/**
	 * 每份重量（克）
	 */
	private Integer perWeight;
	/**
	 * 商品描述
	 */
	private String description;
	/**
	 * 上下架状态:0下架1上架
	 */
	private Integer publishStatus;
	/**
	 * 审核状态:0未审核,1已审核
	 */
	private Integer auditStatus;
	/**
	 * 产地
	 */
	private String 
place;
	/**
	 * 品种
	 */
	private String breed;
	/**
	 * 是否为特产:0不是，1是
	 */
	private Integer isSpecial;
	/**
	 * 商品录入时间
	 */
	private Date indate;
	/**
	 * 最后修改时间
	 */
	private Date modifiedTime;

}
