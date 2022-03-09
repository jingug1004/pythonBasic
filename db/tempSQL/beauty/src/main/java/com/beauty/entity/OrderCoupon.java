package com.beauty.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="APP_ORDER_COUPON")
@ApiModel
public class OrderCoupon {

	@Id 
    @ApiModelProperty(value = "ORDER_CP_ID")
	@Column(name="order_cp_id", nullable=false, length=100)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long orderCpId;
	
	@ApiModelProperty(value = "주문 상품")
	@ManyToOne
	@JoinColumn(name="order_id")
	@JsonBackReference
	@Getter @Setter
	private OrderTotal orderTotal;
	
	@ApiModelProperty(value = "쿠폰 정보")
	@ManyToOne
	@JoinColumn(name="coupon_id")
	@Getter @Setter
	private Coupon coupon;
    
	@ApiModelProperty(value = "유저 ID")
	@ManyToOne
	@JoinColumn(name="user_id")
	@Getter @Setter
	private User user;
	
	@ApiModelProperty(value = "최대 할인 금액")
	@Column(name="max_dis")
	@Getter @Setter
	private Long maxDis;
	
	@ApiModelProperty(value = "최대 할인 상품  ID")
	@Column(name="ord_prod")
	@Getter @Setter
	private Long ordProdId;
	
}
