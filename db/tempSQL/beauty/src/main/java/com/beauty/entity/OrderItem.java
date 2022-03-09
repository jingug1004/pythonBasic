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
@Table(name="APP_ORDER_ITEM")
@ApiModel
public class OrderItem {

	@ApiModelProperty(value = "주문 상품 ITEM ID")
	@Id 
	@Column(name="ord_item")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long ordItemId;

	@ApiModelProperty(value = "상품 ITEM ID")
	@Column(name="item_id")
	@Getter @Setter
	private Long itemId;

	@ApiModelProperty(value = "상품 ID")
	@Column(name="prod_id")
	@Getter @Setter
	private Long productId;

	@ApiModelProperty(value = "옵션 명")
	@Column(name="item_name")
	@Getter @Setter
	private String itemName;

	@ApiModelProperty(value = "선택 수량")
	@Column(name="item_count")
	@Getter @Setter
	private int itemCount;

	@ApiModelProperty(value = "가격")
	@Column(name="price")
	@Getter @Setter
	private int price;

	@ApiModelProperty(value = "배송비")
	@Column(name="delivery")
	@Getter @Setter
	private int delivery;

	@ApiModelProperty(value = "주문 상품")
	@ManyToOne
	@JoinColumn(name="ord_prod")
	@JsonBackReference
	@Getter @Setter
	private OrderProduct orderProduct;
}
