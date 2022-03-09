package com.beauty.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="APP_PRODUCT_DELIVERY")
@EqualsAndHashCode
@ApiModel
public class ProductDelivery {

	@Id 
    @Column(name="delivery_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long deliveryId;
	
	// 배송유형
	@ApiModelProperty(value = "0 일반택배  1 자사택배")
    @Column(name="shipping_type", columnDefinition="int(1) default '0'")
    @Getter @Setter
	private int shippingType = 0;
	
	// 배송사
    @ApiModelProperty(value = "택배사 정보")
	@ManyToOne
	@JoinColumn(name="company")
	@Getter @Setter
	private DeliveryCompany deliveryCompany;
    
	// 배송비 
	@ApiModelProperty(value = "xxx원 이상 무료배송")
	@Column(name="free_delivery")
	@Getter @Setter
	private int freeDelivery;
	
	// 출고일
	@ApiModelProperty(value = "x일 이내 출고")
	@Column(name="delivery_date")
	@Getter @Setter
	private int deliveryDate;
	
	@ApiModelProperty(value = "배송비")
	@Column(name="delivery_price")
	@Getter @Setter
	private int deliveryPrice = 0;
	
	// 특이사항
	@ApiModelProperty(value = "특이사항")
	@Column(name="uniqueness")
	@Getter @Setter
	private String uniqueness;
	
}
