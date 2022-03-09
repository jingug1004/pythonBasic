package com.beauty.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beauty.entity.Category;
import com.beauty.entity.Product;
import com.beauty.entity.ProductCategory;
import com.beauty.entity.ProductTimeSale;
import com.beauty.response.BestResponse;
import com.beauty.response.CategoryResponse;
import com.beauty.response.CategoryResponse2;
import com.beauty.response.ProductResponse;
import com.beauty.service.ProductService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value="/api/product")
public class ProductController {
	public final String TAG = "상품";
	
	@Autowired
	private ProductService productService;
	
    /**
     * 카테고리 목록
     * @return
     */
    @ApiOperation(value = "카테고리", notes = "카테고리 목록 및 브랜드 목록", response = CategoryResponse.class, tags={TAG, })
    @RequestMapping(value="/category", method=RequestMethod.GET)
    public @ResponseBody CategoryResponse getCategory() {
        return productService.getCategory();
    }
    
    @ApiOperation(value = "카테고리", notes = "카테고리 목록 및 브랜드 목록", response = CategoryResponse2.class, tags={TAG, })
    @RequestMapping(value="/category2", method=RequestMethod.GET)
    public @ResponseBody CategoryResponse2 getCategory2() {
        return productService.getCategory2();
    }
    
    @ApiOperation(value = "하위 카테고리", notes = "하위 카테고리 목록", response = CategoryResponse.class, tags={TAG, })
    @RequestMapping(value="/sub_category", method=RequestMethod.GET)
    public @ResponseBody List<Category> getCategory(@RequestParam Long menu_id) {
        return productService.getSubCategory(menu_id);
    }
    
    /**
     * 상품 목록
     */
    @ApiOperation(value = "상품 목록", notes = "카테고리 목록", response = Product.class, responseContainer="List", tags={TAG, })
    @RequestMapping(value="/list/{parent}/{page}", method=RequestMethod.GET)
    public @ResponseBody Page<ProductCategory> getProduct(
    		@ApiParam(name="parent", value="0", required = true) @PathVariable Long parent,
    		@ApiParam(name="page", value="조회할 페이지  0부터 시작", required = true) @PathVariable int page,
    		@ApiParam(name="menu_id", value="카테고리 ID") @RequestParam(name="menu_id", required=true) Long menu_id,
    		@ApiParam(name="sort", value="1:인기순 2:리뷰순 3:낮은가격 4:높은가격, 5:신규", required = true) @RequestParam int sort) {
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
    		@ApiParam(name="sort", value="1:인기순 2:리뷰순 3:낮은가격 4:높은가격, 5:신규", required = true) @RequestParam int sort) {
        return productService.getProuctBrandList(brand_id, page, sort);
    }
    
    /**
     * 상품 목록
     */
    @ApiOperation(value = "상품 목록", notes = "신규 목록 - 최근 일주일 목록", response = BestResponse.class, tags={TAG, })
    @RequestMapping(value="/list", method=RequestMethod.GET)
    public @ResponseBody BestResponse getProductNew() {
        return productService.getProuctNewList();
    }
    
    /**
     * 상품 목록
     */
    @ApiOperation(value = "상품 목록", notes = "베스트 목록 ", response = BestResponse.class, tags={TAG, })
    @RequestMapping(value="/best", method=RequestMethod.GET)
    public @ResponseBody BestResponse getProductBest() {
        return productService.getProductBest();
    }
    
    /**
     * 상품 목록
     */
    @ApiOperation(value = "상품 검색", notes = "상품 검색 ", response = BestResponse.class, tags={TAG, })
    @RequestMapping(value="/search", method=RequestMethod.GET)
    public @ResponseBody List<Product> getProductSearch(@RequestParam String search) {
        return productService.getProductSearch(search);
    }
    
    /**
     * 상품 목록
     */
    @ApiOperation(value = "상품 목록", notes = "한정 특가 목록", response = ProductTimeSale.class, responseContainer="List", tags={TAG, })
    @RequestMapping(value="/time", method=RequestMethod.GET)
    public @ResponseBody List<ProductTimeSale> getProductTimeSale() {
        return productService.getProductTime();
    }
    
    /**
     * 상품 목록
     */
    @ApiOperation(value = "상품 목록", notes = "박스 목록", response = Product.class, responseContainer="List", tags={TAG, })
    @RequestMapping(value="/box", method=RequestMethod.GET)
    public @ResponseBody List<Product> getProductBox() {
        return productService.getProductBox();
    }
    
    /**
     * 상품 정보
     */
    @ApiOperation(value = "상품 정보", notes = "상품 정보", response = ProductResponse.class, tags={TAG, })
    @RequestMapping(value="/detail", method=RequestMethod.GET)
    public @ResponseBody ProductResponse getDetail(
    		@ApiParam(name="product_id", value="상품 ID", required = true) @RequestParam Long product_id) {
        return productService.getDetail(product_id);
    }
}
