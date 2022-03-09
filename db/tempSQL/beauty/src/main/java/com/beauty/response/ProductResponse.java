package com.beauty.response;

import java.util.Date;
import java.util.List;

import com.beauty.entity.DetailTop;
import com.beauty.entity.Product;
import com.beauty.entity.ProductItem;
import com.beauty.entity.ProductTimeSale;
import com.beauty.response.result.ResultCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

public class ProductResponse {
	@ApiModelProperty(value = "200 성공 99 업데이트 필요")
	@Getter 
	private final ResultCode resultCode;

	@ApiModelProperty(value = "성공시 success 실패시 실패 내용 확인")
	@Getter 
    private final String message;

	@Getter 
    private final Date timestamp;
    
	@ApiModelProperty(value = "상품 정보")
	@Getter 
	private Product product;
	
	@ApiModelProperty(value = "상품 상단")
	@Getter 
	private List<DetailTop> productTop;
	
	@ApiModelProperty(value = "상품 Item")
	@Getter 
	private List<ProductItem> productItem;
	
	@ApiModelProperty(value = "한정특가")
	@Getter 
	private ProductTimeSale productTimeSale;
	
    protected ProductResponse(final String message, final ResultCode resultCode, final Product product, final List<ProductItem> productItem, final ProductTimeSale productTimeSale, final List<DetailTop> productTop) {
        this.message = message;
        this.resultCode = resultCode;
        this.product = product;
        this.productItem = productItem;
        this.productTimeSale = productTimeSale;
        this.productTop = productTop;
        this.timestamp = new java.util.Date();
    }

    public static ProductResponse of(final String message, final ResultCode resultCode, final Product product, final List<ProductItem> productItem, final ProductTimeSale productTimeSale, final List<DetailTop> productTop) {
        return new ProductResponse(message, resultCode, product, productItem, productTimeSale, productTop);
    }
}
