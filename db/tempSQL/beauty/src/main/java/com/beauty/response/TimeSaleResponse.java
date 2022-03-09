package com.beauty.response;

import java.util.Date;
import java.util.List;

import com.beauty.entity.ProductTimeSale;
import com.beauty.response.result.ResultCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

public class TimeSaleResponse {
	@ApiModelProperty(value = "200 성공 99 업데이트 필요")
	@Getter 
	private final ResultCode resultCode;

	@ApiModelProperty(value = "성공시 success 실패시 실패 내용 확인")
	@Getter 
    private final String message;

	@Getter 
    private final Date timestamp;
	
	@ApiModelProperty(value = "특가세일 상품")
	@Getter 
	private List<ProductTimeSale> productList;
	
    protected TimeSaleResponse(final String message, final ResultCode resultCode, final List<ProductTimeSale> productList) {
        this.message = message;
        this.resultCode = resultCode;
        this.productList = productList;
        this.timestamp = new java.util.Date();
    }

    public static TimeSaleResponse of(final String message, final ResultCode resultCode, final List<ProductTimeSale> productList) {
        return new TimeSaleResponse(message, resultCode, productList);
    }
}
