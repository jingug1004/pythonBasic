package com.beauty.response;

import java.util.Date;
import java.util.List;

import com.beauty.entity.MainFirstBanner;
import com.beauty.entity.MainMenu;
import com.beauty.entity.MainSecondBanner;
import com.beauty.entity.MainTenMenu;
import com.beauty.entity.Product;
import com.beauty.response.result.ResultCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

public class MainResponse {
	@ApiModelProperty(value = "200 성공 99 업데이트 필요")
	@Getter 
	private final ResultCode resultCode;

	@ApiModelProperty(value = "성공시 success 실패시 실패 내용 확인")
	@Getter 
    private final String message;

	@Getter 
    private final Date timestamp;
    
	@ApiModelProperty(value = "첫번째배너")
	@Getter 
	private List<MainFirstBanner> mainFirstBanner;
	
	@ApiModelProperty(value = "두번째배너")
	@Getter 
	private List<MainSecondBanner> mainSecondBanner;
	
	@ApiModelProperty(value = "메인 10개 메뉴")
	@Getter 
	private List<MainTenMenu> mainTenMenu;
	
	@ApiModelProperty(value = "메인  메뉴")
	@Getter 
	private List<MainMenu> mainMenu;
	
	@ApiModelProperty(value = "실시간 인기 목록")
	@Getter 
	private List<Product> productList;
	
	
    protected MainResponse(final String message, final ResultCode resultCode, 
    		final List<MainFirstBanner> mainFirstBanner, final List<MainSecondBanner> mainSecondBanner,
    		final List<MainTenMenu> mainTenMenu, final List<MainMenu> mainMenu, final List<Product> productList) {
        this.message = message;
        this.resultCode = resultCode;
        this.mainFirstBanner = mainFirstBanner;
        this.mainSecondBanner = mainSecondBanner;
        this.mainTenMenu = mainTenMenu;
        this.mainMenu = mainMenu;
        this.productList = productList;
        this.timestamp = new java.util.Date();
    }

    public static MainResponse of(final String message, final ResultCode resultCode,
    		final List<MainFirstBanner> mainFirstBanner, final List<MainSecondBanner> mainSecondBanner,
    		final List<MainTenMenu> mainTenMenu, final List<MainMenu> mainMenu, final List<Product> productList) {
        return new MainResponse(message, resultCode, mainFirstBanner, mainSecondBanner, mainTenMenu, mainMenu, productList);
    }
}
