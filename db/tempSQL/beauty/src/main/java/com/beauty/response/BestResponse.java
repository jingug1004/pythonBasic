package com.beauty.response;

import java.util.Date;
import java.util.List;

import com.beauty.entity.MainFirstBanner;
import com.beauty.entity.Product;
import com.beauty.response.result.ResultCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

public class BestResponse {
	@ApiModelProperty(value = "200 성공 99 업데이트 필요")
	@Getter 
	private final ResultCode resultCode;

	@ApiModelProperty(value = "성공시 success 실패시 실패 내용 확인")
	@Getter 
    private final String message;

	@Getter 
    private final Date timestamp;
	
	@ApiModelProperty(value = "상단 배너")
	@Getter 
	private List<MainFirstBanner> firstBanner;
	
	@ApiModelProperty(value = "신규 또는 베스트 상품")
	@Getter 
	private List<Product> productList;
	
    protected BestResponse(final String message, final ResultCode resultCode, final  List<MainFirstBanner> firstBanner, final List<Product> productList) {
        this.message = message;
        this.resultCode = resultCode;
        this.firstBanner = firstBanner;
        this.productList = productList;
        this.timestamp = new java.util.Date();
    }

    public static BestResponse of(final String message, final ResultCode resultCode, final  List<MainFirstBanner> firstBanner, final List<Product> productList) {
        return new BestResponse(message, resultCode, firstBanner, productList);
    }
}
