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

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="APP_SHIPPING")
@EqualsAndHashCode
@ToString
@ApiModel
public class Shipping {
	
	@ApiModelProperty(value = "배송지 ID")
	@Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long shipId;
	
	@ApiModelProperty(value = "이름")
	@Getter @Setter
	private String name;
	
	@ApiModelProperty(value = "전화번호")
	@Getter @Setter
	private String phone;
	
	@ApiModelProperty(value = "우편번호")
	@Getter @Setter
	private String zipcode;
	
	@ApiModelProperty(value = "주소1")
	@Getter @Setter
	private String address1;
	
	@ApiModelProperty(value = "주소2")
	@Getter @Setter
	private String address2;
	
	@ApiModelProperty(value = "배송요청사항")
	@Getter @Setter
	private String memo;
	
	
	@ApiModelProperty(value = "기본배송지")
	@Column(name="brand_visible", columnDefinition="CHAR(1) default 'Y'")
	@Getter @Setter
	private String defAddr;
	
	@ApiModelProperty(value = "등록일")
	@Getter @Setter
	private Date regDate = new Date();
	
	@JsonIgnore
	@ApiModelProperty(value = "유저 고유ID")
	@ManyToOne
	@JoinColumn(name="user_id")
	@Getter @Setter
	private User user;
}
