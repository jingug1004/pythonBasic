package com.beauty.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beauty.response.WishListResponse;
import com.beauty.security.auth.JwtAuthenticationToken;
import com.beauty.service.WishListService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value="/api/v1/wish")
public class WishListV1Controller {
	public final String TAG = "위시리스트 - 로그인 필요";
	
	@Autowired
	private WishListService wshListService;
	
	
    /**
     * 찜한 목록
     * @return
     */
    @ApiOperation(value = "목록", notes = "찜한 목록", response = WishListResponse.class, tags={TAG, })
    @RequestMapping(value="/list", method=RequestMethod.GET)
    public @ResponseBody WishListResponse getCategory(JwtAuthenticationToken token) {
    	return wshListService.list(token);
    }
    
    /**
     * 찜하기
     */
    @ApiOperation(value = "등록", notes = "찜하기", response = WishListResponse.class, tags={TAG, })
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public @ResponseBody WishListResponse add(JwtAuthenticationToken token, 
    		@ApiParam(name="product_id", value="Product ID", required = true) @RequestParam Long product_id) {
        return wshListService.add(product_id, token);
    }
   
    /**
     * 찜 삭제
     */
    @ApiOperation(value = "삭제", notes = "찜 삭제", response = WishListResponse.class, tags={TAG, })
    @RequestMapping(value="/delete", method=RequestMethod.DELETE)
    public @ResponseBody WishListResponse delete(JwtAuthenticationToken token,
    		@ApiParam(name="wish_id", value="찜 ID", required = true) @RequestParam Long wish_id) {
        return wshListService.delete(wish_id, token);
    }
    
    /**
     * 찜 삭제
     */
    @ApiOperation(value = "등록 확인", notes = "상품이 위시리스트에 추가 되었는지 확인", response = WishListResponse.class, tags={TAG, })
    @RequestMapping(value="/check", method=RequestMethod.GET)
    public @ResponseBody WishListResponse check(JwtAuthenticationToken token,
    		@ApiParam(name="product_id", value="Product ID", required = true) @RequestParam Long product_id) {
        return wshListService.check(product_id, token);
    }
}
