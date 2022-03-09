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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="APP_REVIEW_WAIT_ITEM")
@EqualsAndHashCode
@ApiModel
public class ReviewWaitItem {
	
	@Id 
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long itemId;

	@ApiModelProperty(value = "리뷰ID")
	@ManyToOne
	@JoinColumn(name="wait_id")
	@JsonBackReference
	@Getter @Setter
	private ReviewWait reviewWait;
	
	@ApiModelProperty(value = "구매")
	@ManyToOne
	@JoinColumn(name="pi_id")
	@Getter @Setter
	private PaymentItem paymentItem;
	
	
}
