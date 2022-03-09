package com.beauty.response;

import java.util.Date;
import java.util.List;

import com.beauty.entity.Wishlist;
import com.beauty.response.result.ResultCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel
public class WishListResponse {
	
	@ApiModelProperty(value = "200 성공 99 실패")
	@Getter 
	private final ResultCode resultCode;

	@ApiModelProperty(value = "성공시 success 실패시 실패 내용 확인")
	@Getter 
    private final String message;

	@Getter 
    private final Date timestamp;
    
	@ApiModelProperty(value = "위시리스트")
	@Getter 
	private List<Wishlist> wishList;
	
    protected WishListResponse(final String message, final ResultCode resultCode, final List<Wishlist> wishList) {
        this.message = message;
        this.resultCode = resultCode;
        this.wishList = wishList;
        this.timestamp = new java.util.Date();
    }

    public static WishListResponse of(final String message, final ResultCode resultCode,  final List<Wishlist> wishList) {
        return new WishListResponse(message, resultCode, wishList);
    }
}
