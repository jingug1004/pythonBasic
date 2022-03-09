package com.beauty.response;

import java.util.Date;

import com.beauty.entity.AppBasket;
import com.beauty.response.result.ResultCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel
public class AppBasketResponse {
	
	@ApiModelProperty(value = "200 성공 99 실패")
	@Getter 
	private final ResultCode resultCode;

	@ApiModelProperty(value = "성공시 success 실패시 실패 내용 확인")
	@Getter 
    private final String message;

	@Getter 
    private final Date timestamp;
    
	@ApiModelProperty(value = "장바구니 목록")
	@Getter 
	private AppBasket basketList;
	
    protected AppBasketResponse(final String message, final ResultCode resultCode, final AppBasket basketList) {
        this.message = message;
        this.resultCode = resultCode;
        this.basketList = basketList;
        this.timestamp = new java.util.Date();
    }

    public static AppBasketResponse of(final String message, final ResultCode resultCode,  final AppBasket basketList) {
        return new AppBasketResponse(message, resultCode, basketList);
    }
}
