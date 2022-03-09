package com.beauty.entity;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//장바구니
@ApiModel
public class Basket {


	@ApiModelProperty(value = "브랜드")
	@Getter @Setter
	private Brand brand;
	
	@ApiModelProperty(value = "상품목록")
	@Getter @Setter
	private List<BasketProduct> products;
}
