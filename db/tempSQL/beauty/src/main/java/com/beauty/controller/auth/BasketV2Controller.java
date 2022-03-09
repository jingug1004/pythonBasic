package com.beauty.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beauty.response.AppBasketResponse;
import com.beauty.response.BasketResponse;
import com.beauty.security.auth.JwtAuthenticationToken;
import com.beauty.service.AppBasketService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value="/api/v2/basket")
public class BasketV2Controller {
	public final String TAG = "장바구니 - 로그인 필요";
	
	@Autowired
	private AppBasketService basketService;
	
	
    /**
     * 장바구니 목록
     * @return
     */
    @ApiOperation(value = "목록", notes = "등록한 장바구니 목록", response = AppBasketResponse.class, responseContainer="Map", tags={TAG, })
    @RequestMapping(value="/list", method=RequestMethod.GET)
    public @ResponseBody AppBasketResponse list(JwtAuthenticationToken token) {
    	return basketService.getBasketList(token);
    }
    
    /**
     * 장바구니 등록
     */
    @ApiOperation(value = "등록", notes = "자신이 등록한 장바구니 목록", response = AppBasketResponse.class, tags={TAG, })
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public @ResponseBody AppBasketResponse add(JwtAuthenticationToken token, 
    		@ApiParam(name="item_id", value="Item ID", required = true) @RequestParam Long item_id,
    		@ApiParam(name="item_count", value="Item 수량", required = true) @RequestParam int item_count) {
        return basketService.basketAdd(item_id, item_count, token);
    }
   
    /**
     * 장바구니 수정
     */
    @ApiOperation(value = "수정", notes = "수량 수정", response = AppBasketResponse.class, tags={TAG, })
    @RequestMapping(value="/update", method=RequestMethod.PUT)
    public @ResponseBody AppBasketResponse update(JwtAuthenticationToken token, 
    		@ApiParam(name="basket_id", value="장바구니 아이템 ID (bpi_id)", required = true) @RequestParam Long basket_id,
    		@ApiParam(name="item_count", value="Item 수량", required = true) @RequestParam int item_count) {
        return basketService.basketUpd(basket_id, item_count, token);
    }
    
    /**
     * 장바구니 삭제
     */
    @ApiOperation(value = "삭제", notes = "장바구니 삭제", response = BasketResponse.class, tags={TAG, })
    @RequestMapping(value="/delete", method=RequestMethod.POST)
    public @ResponseBody AppBasketResponse delete(JwtAuthenticationToken token,
    		@ApiParam(name="basket_id", value="장바구니 ID", required = true) @RequestParam Long[] basket_id) {
        return basketService.basketDelete(basket_id, token);
    } 
}
