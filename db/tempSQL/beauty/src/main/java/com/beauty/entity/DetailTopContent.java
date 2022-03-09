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
@Table(name="APP_DETAIL_TOP_CONTENT")
@EqualsAndHashCode
@ApiModel
public class DetailTopContent {
	// ID
	@Id 
    @Column(name="dtc_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long dtcId;
	
    @ApiModelProperty(value = "적용 상품 ID") 
	@Column(name="ct_id")
	@Getter @Setter
	private Long contentId;

	@ApiModelProperty(value = "상단 타입 - 0: 브랜드 1: 카테고리 2: 상품")
	@Column(name="top_type")
	@Getter @Setter
	private int topType;
	
    @ApiModelProperty(value = "상단 정보")
	@ManyToOne
	@JoinColumn(name="dt_id")
	@Getter @Setter
	private DetailTop detailTop;
    
}
