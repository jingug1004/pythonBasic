package com.beauty.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beauty.entity.Product;
import com.beauty.entity.ProductCategory;
import com.beauty.response.CategoryResponse;
import com.beauty.response.ProductResponse;
import com.beauty.security.auth.JwtAuthenticationToken;
import com.beauty.service.ProductService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value="/api/v1/product")
public class ProductV1Controller {
	public final String TAG = "상품 - 로그인시 해당 API 사용";
	
	@Autowired
	private ProductService productService;
	
    /**
     * 카테고리 목록
     * @return
     */
    @ApiOperation(value = "카테고리", notes = "카테고리 목록 및 브랜드 목록", response = CategoryResponse.class, tags={TAG, })
    @RequestMapping(value="/category", method=RequestMethod.GET)
    public @ResponseBody CategoryResponse getCategory(JwtAuthenticationToken token) {
        return productService.getCategory();
    }
    
    /**
     * 상품 목록
     */
    @ApiOperation(value = "상품 목록", notes = "카테고리 목록", response = Product.class, responseContainer="List", tags={TAG, })
    @RequestMapping(value="/list/{parent}/{page}", method=RequestMethod.GET)
    public @ResponseBody Page<ProductCategory> getProduct(
    		@ApiParam(name="parent", value="카테고리 부모 ID", required = true) @PathVariable Long parent,
    		@ApiParam(name="page", value="조회할 페이지  0부터 시작", required = true) @PathVariable int page,
    		@ApiParam(name="menu_id", value="카테고리 ID") @RequestParam(name="menu_id", required=false) Long menu_id,
    		@ApiParam(name="sort", value="1:인기순 2:리뷰순 3:낮은가격 4:높은가격, 5:신규", required = true) @RequestParam int sort,
    		JwtAuthenticationToken token) {
        return productService.getProuctList(parent, menu_id, page, sort);
    }
   
    /**
     * 상품 목록
     */
    @ApiOperation(value = "상품 목록", notes = "브랜드 목록", response = Product.class, responseContainer="List", tags={TAG, })
    @RequestMapping(value="/list/{page}", method=RequestMethod.GET)
    public @ResponseBody Page<Product> getProductBrand(
    		@ApiParam(name="page", value="조회할 페이지  0부터 시작", required = true) @PathVariable int page,
    		@ApiParam(name="brand_id", value="브랜드 ID", required = true) @RequestParam Long brand_id,
    		@ApiParam(name="sort", value="1:인기순 2:리뷰순 3:낮은가격 4:높은가격, 5:신규", required = true) @RequestParam int sort,
    		JwtAuthenticationToken token) {
        return productService.getProuctBrandList(brand_id, page, sort);
    }
    
    /**
     * 상품 목록
     */
    @ApiOperation(value = "상품 정보", notes = "상품 정보", response = ProductResponse.class, tags={TAG, })
    @RequestMapping(value="/detail", method=RequestMethod.GET)
    public @ResponseBody ProductResponse getDetail(
    		@ApiParam(name="product_id", value="상품 ID", required = true) @RequestParam Long product_id,
    		JwtAuthenticationToken token) {
        return productService.getDetail(product_id);
    }
}
