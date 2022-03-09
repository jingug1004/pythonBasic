package com.beauty.entity;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public class BasketProduct {
	@ApiModelProperty(value = "상품 ID")
	@Getter @Setter
	private Long productId;
	
	@ApiModelProperty(value = "상품명")
	@Getter @Setter
	private String prodName;
	
	@ApiModelProperty(value = "브랜드ID")
	@Getter @Setter
	private Long brandId;
	
	@ApiModelProperty(value = "상품 설명")
	@Getter @Setter
	private String prodDesc;
	
	@ApiModelProperty(value = "썸네일")
	@Getter @Setter
	private String thumbUrl;
	
	@ApiModelProperty(value = "원가")
	@Getter @Setter
	private int price;
	
	@ApiModelProperty(value = "세일")
	@Getter @Setter
	private int sale;
	
	@ApiModelProperty(value = "세일가")
	@Getter @Setter
	private int salePrice;
	
    @ApiModelProperty(value = "배송정보")
	@Getter @Setter
	private ProductDelivery productDelivery;
    
    @ApiModelProperty(value = "옵션 정보")
	@Getter @Setter
    private List<BasketProductItem> productItem;
}
