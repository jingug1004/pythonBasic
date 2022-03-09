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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="APP_BASKET_PRODUCT")
@EqualsAndHashCode
@ApiModel
public class AppBasketProduct {
	
	@Id 
    @Column(name="bp_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long bpId;
	
	@ApiModelProperty(value = "판매자")
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="basket_seller")
	@Getter @Setter
	private AppBasketSeller basketSeller;
	
    @ApiModelProperty(value = "상품")
	@ManyToOne
	@JoinColumn(name="product_id")
	@Getter @Setter
	private Product product;
    
	@ApiModelProperty(value = "상품 아이템 목록")
	@OneToMany(cascade=CascadeType.ALL, mappedBy="basketProduct")
	@Getter @Setter
	private List<AppBasketProductItem> basketProductItem;
	
	@Transient
	@Getter @Setter
	private int soldOut;	
}
