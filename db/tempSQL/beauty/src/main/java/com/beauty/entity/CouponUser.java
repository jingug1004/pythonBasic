package com.beauty.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="APP_COUPON_USER")
@EqualsAndHashCode
@ToString
@ApiModel
public class CouponUser {

	@Id 
    @Column(name="c_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long cId;
	
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
	
    @ApiModelProperty(value = "쿠폰 사용여부 Y : 사용  N : 미사용")
	@Column(name="use_yn")
	@Getter @Setter
	private String useYn;
    
    @ApiModelProperty(value = "사용일")
	@Column(name="use_date")
	@Getter @Setter
	private Date useDate;

    @ApiModelProperty(value = "시작일")
	@Column(name="start_date")
	@Getter @Setter
	private Date startDate;
    
    @ApiModelProperty(value = "종료일")
	@Column(name="end_date")
	@Getter @Setter
	private Date endDate;
    
    @ApiModelProperty(value = "발급일")
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
    
    
    @ApiModelProperty(value = "특별쿠폰 일 경우 적용가능 상품")
    @Transient
    @Getter @Setter
    private List<Product> productList; 
}
