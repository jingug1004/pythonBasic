package com.beauty.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="APP_ORDER_PRODUCT")
@ApiModel
public class OrderProduct {
	
	@ApiModelProperty(value = "주문 상품 ID")
    @Id 
    @Column(name="ord_prod")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long ordProdId;

	@ApiModelProperty(value = "상품 ID")
	@Column(name="prod_id")
	@Getter @Setter
	private Long productId;
	
	@ApiModelProperty(value = "브랜드명")
    @Column(name="brand_name")
	@Getter @Setter
	private String brandName;
	
	@ApiModelProperty(value = "상품명")
    @Column(name="prod_name")
	@Getter @Setter
	private String productName;
	
	@ApiModelProperty(value = "이미지")
    @Column(name="thumbnail")
	@Getter @Setter
	private String thumbnail;
	
	@ApiModelProperty(value = "주문 상품 목록")
	@OneToMany(cascade=CascadeType.ALL, mappedBy="orderProduct")
	@Getter @Setter
	private List<OrderItem> orderItem;
	
	@ApiModelProperty(value = "주문 상품")
	@ManyToOne
	@JoinColumn(name="order_id")
	@JsonBackReference
	@Getter @Setter
	private OrderTotal orderTotal;
	
	@ApiModelProperty(value = "가격")
	@Transient
	private int prodPrice;

	public int getProdPrice() {
		prodPrice = 0;
		if(getOrderItem() != null && getOrderItem().size() > 0) {
			for(OrderItem orderItem :getOrderItem()) {
				prodPrice += orderItem.getPrice();
			}
		}
		return prodPrice;
	}
	
	
}
