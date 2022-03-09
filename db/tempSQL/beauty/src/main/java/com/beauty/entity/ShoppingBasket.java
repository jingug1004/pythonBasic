package com.beauty.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

//장바구니
@Entity
@Table(name="APP_SHOPPING_BASKET")
@EqualsAndHashCode
@ApiModel
public class ShoppingBasket {

	@Id 
    @Column(name="basket_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long basketId;
	
	@JsonIgnore
	@ApiModelProperty(value = "유저 고유ID")
	@ManyToOne
	@JoinColumn(name="user_id")
	@Getter @Setter
	private User user;
	
    @ApiModelProperty(value = "상품 아이템")
	@ManyToOne
	@JoinColumn(name="item_id")
	@Getter @Setter
	private ProductItem productItem;
    
    @ApiModelProperty(value = "상품수량")
    @Column(name="item_count")
	@Getter @Setter
	private int itemCount;
    
	// 등록일
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
}
