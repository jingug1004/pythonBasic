package com.beauty.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="APP_COUPON_SP")
@EqualsAndHashCode
@ToString
@ApiModel
public class SpecialCoupon {

	// 쿠폰 ID
	@Id 
    @Column(name="sp_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long spId;
	
    @ApiModelProperty(value = "쿠폰ID") 
	@Column(name="cp_id")
	@Getter @Setter
	private String cpId;
	
    @ApiModelProperty(value = "데이터ID") 
	@Column(name="ct_id")
	@Getter @Setter
	private Long ctId;
    
}
