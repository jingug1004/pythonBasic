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
@Table(name="APP_PLAN_PRODUCT")
@EqualsAndHashCode
@ApiModel
public class PlanProduct {

	@ApiModelProperty(value = "기획전 상품 ID")
	@Id
	@Column(name="pp_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long ppId;
    
    @ApiModelProperty(value = "기획 그룹")
	@ManyToOne
	@JoinColumn(name="pg_id")
	@Getter @Setter
	@JsonBackReference
	private PlanGroup planGroup;
    
    @ApiModelProperty(value = "상품")
	@ManyToOne
	@JoinColumn(name="product_id")
	@Getter @Setter
	private Product product;
	
	
}
