package com.beauty.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="APP_ORDER_TOTAL")
@ApiModel
public class OrderTotal {
	
	@Id 
    @ApiModelProperty(value = "ORDER ID")
	@Column(name="order_id", nullable=false, length=100)
	@Getter @Setter
	private String orderId;
	
    @ApiModelProperty(value = "주문명")
	@Column(name="payment_name", nullable=false, length=100)
    @Getter @Setter
	private String paymentName;
	
    @ApiModelProperty(value = "주문 상품 목록")
	@OneToMany(cascade=CascadeType.ALL, mappedBy="orderTotal")
    @Getter @Setter
	private List<OrderProduct> orderProduct;
	
    @ApiModelProperty(value = "총 가격")
	@Column(name="total_price", nullable=false, length=100)
    @Getter @Setter
	private Long totalPrice;
    
    @ApiModelProperty(value = "상품 주문 수량")
	@Column(name="total_count")
    @Getter @Setter
	private int totalCount;
    
    @ApiModelProperty(value = "총 배송비")
	@Column(name="total_delivery", nullable=false, length=100)
    @Getter @Setter
	private Long totalDelivery;
    
	@JsonIgnore
	@ApiModelProperty(value = "유저 ID")
	@ManyToOne
	@JoinColumn(name="user_id")
	@Getter @Setter
	private User user;
	
    @ApiModelProperty(value = "쿠폰 목록")
	@OneToMany(cascade=CascadeType.ALL, mappedBy="orderTotal")
    @Getter @Setter
	private List<OrderCoupon> orderCoupon;
    
	@ApiModelProperty(value = "등록일")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
}
