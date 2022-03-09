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

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="APP_BASKET")
@EqualsAndHashCode
@ApiModel
public class AppBasket {
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
	
	@ApiModelProperty(value = "판매자 상품 목록")
	@OneToMany(cascade=CascadeType.ALL, mappedBy="basket")
	@Getter @Setter
	private List<AppBasketSeller> appBasketSeller;
	
	// 등록일
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
}
