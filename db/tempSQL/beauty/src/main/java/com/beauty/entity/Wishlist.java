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

@Entity
@Table(name="APP_WISHLIST")
@EqualsAndHashCode
@ApiModel
public class Wishlist {

	@Id 
    @Column(name="wish_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long wishId;
	
	@JsonIgnore
	@ApiModelProperty(value = "유저 고유ID")
	@ManyToOne
	@JoinColumn(name="email")
	@Getter @Setter
	private User user;
	
    @ApiModelProperty(value = "상품 정보")
	@ManyToOne
	@JoinColumn(name="product_id")
	@Getter @Setter
	private Product product;
    
	// 등록일
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
}
