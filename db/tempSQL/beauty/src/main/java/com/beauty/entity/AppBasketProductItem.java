package com.beauty.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="APP_BASKET_PRODUCTITEM")
@EqualsAndHashCode
@ApiModel
public class AppBasketProductItem {
	
	@Id 
    @Column(name="bpi_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long bpiId;
	
    @ApiModelProperty(value="상품")
	@ManyToOne
	@JoinColumn(name="product_item")
	@Getter @Setter
	private ProductItem productItem;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="basket_product")
	@Getter @Setter
	private AppBasketProduct basketProduct;
	
	
	@ApiModelProperty(value = "판매가")
	@Column(name="price")
	@Getter @Setter
	private int price;
	
	@ApiModelProperty(value = "수량")
	@Column(name="basket_count")
	@Getter @Setter
	private int basketCount;
	
	@ApiModelProperty(value = "합계")
	@Column(name="total_price")
	@Getter @Setter
	private int totalPrice;
	
	@Transient
	@Getter @Setter
	private int soldOut;
	
}
