package com.beauty.entity;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="APP_BASKET_SELLER")
@EqualsAndHashCode
@ApiModel
public class AppBasketSeller {
	@Id 
    @Column(name="seller_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long sellerId;
	
	@ApiModelProperty(value = "판매자 고유ID")
	@ManyToOne
	@JoinColumn(name="seller")
	@Getter @Setter
	private User seller;
	
	@ApiModelProperty(value = "판매자 고유ID")
	@ManyToOne
	@JoinColumn(name="brand")
	@Getter @Setter
	private Brand brand;
	
	@ApiModelProperty(value = "유저 고유ID")
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name="basket_id")
	@Getter @Setter
	private AppBasket basket;
	
	
	@ApiModelProperty(value = "상품 목록")
	@OneToMany(cascade=CascadeType.ALL, mappedBy="basketSeller")
	@Getter @Setter
	private List<AppBasketProduct> basketProduct;
	
	@Transient
	@Getter @Setter
	private int soldOut;
	
	// 등록일
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
}
