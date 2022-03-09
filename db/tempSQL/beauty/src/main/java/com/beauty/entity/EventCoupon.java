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
import lombok.ToString;

@Entity
@Table(name="APP_EVENT_COUPON")
@EqualsAndHashCode
@ToString
@ApiModel
public class EventCoupon {

	// 쿠폰 ID
	@Id 
    @Column(name="ec_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long ecId;
	
    @ApiModelProperty(value = "이벤트")
	@ManyToOne
	@JoinColumn(name="event")
	@Getter @Setter
	private Event event;
    
    @ApiModelProperty(value = "쿠폰")
	@ManyToOne
	@JoinColumn(name="coupon")
	@Getter @Setter
	private Coupon coupon;
    
}
