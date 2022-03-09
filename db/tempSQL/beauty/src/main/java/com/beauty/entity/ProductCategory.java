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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="APP_PRODUCT_CATEGORY")
@EqualsAndHashCode
@ApiModel
public class ProductCategory {

	@ApiModelProperty(value = "Mapping ID")
    @Id 
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long id;
	
    @ApiModelProperty(value = "상품")
	@ManyToOne
	@JoinColumn(name="product_id")
	@Getter @Setter
	private Product product;
    
    @ApiModelProperty(value = "카테고리")
	@ManyToOne
	@JoinColumn(name="menu_id")
	@Getter @Setter
	private Category category;
    
	@ApiModelProperty(value = "등록일")
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
}
