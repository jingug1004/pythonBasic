package com.beauty.entity;

import javax.persistence.Column;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public class BasketProductItem {
	
	@Getter @Setter
	private Long itemId;
    
	@ApiModelProperty(value = "상품 ID")
	@Getter @Setter
	private Long productId;
	
	@ApiModelProperty(value = "아이템명")
	@Getter @Setter
	private String itemName;
	
	@ApiModelProperty(value = "옵션 가격")
	@Column(name="price")
	@Getter @Setter
	private int price;
	
	@ApiModelProperty(value = "아이템 총 수량")
	@Getter @Setter
	private int totalItemCnt;
	
	@ApiModelProperty(value = "구매가능한 최대 수량")
	@Getter @Setter
	private int maxItemCnt;
	
	@ApiModelProperty(value = "남은 수량")
	@Getter @Setter
	private int itemCnt;
	
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
	
	@ApiModelProperty(value = "세일")
	@Getter @Setter
	private int sale;
	
	@ApiModelProperty(value = "세일가")
	@Getter @Setter
	private int salePrice;
	
    @ApiModelProperty(value = "배송정보")
	@Getter @Setter
	private ProductDelivery productDelivery;
    
    @ApiModelProperty(value = "장바구니ID")
	@Getter @Setter
	private Long basketId;
    
    @ApiModelProperty(value = "상품수량")
	@Getter @Setter
	private int itemCount;    

	@ApiModelProperty(value = "브랜드")
	@Getter @Setter
	private Brand brand;
	
	@ApiModelProperty(value = "판매중지 [0 : 판매 , 1 : 중지]")
	@Getter @Setter
	private int stopSelling = 0;
}
