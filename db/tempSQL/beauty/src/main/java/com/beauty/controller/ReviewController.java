package com.beauty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beauty.entity.Review;
import com.beauty.service.ReviewService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value="/api/review")
public class ReviewController {
	public final String TAG = "리뷰";
	
	@Autowired
	private ReviewService reviewService;
	
	
    /**
     * 리뷰 목록
     * @return
     */
    @ApiOperation(value = "목록", notes = "리뷰 목록", response = Review.class, responseContainer="List", tags={TAG, })
    @RequestMapping(value="/list/{page}", method=RequestMethod.GET)
    public @ResponseBody Page<Review> list(
    		@ApiParam(name="page", value="조회할 페이지  0부터 시작", required = true) @PathVariable int page,
    		@ApiParam(name="product_id", value="상품 ID", required = true) @RequestParam Long product_id,
    		@ApiParam(name="photo", value="0 전체  1 포토리뷰만", required = true) @RequestParam int photo,
    		@ApiParam(name="sort", value="1:등록순 2:추천순", required = true)  @RequestParam int sort) {
    		return reviewService.list(product_id, photo, page, sort, null);
    }
}
