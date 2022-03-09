package com.beauty.controller.auth;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beauty.entity.Review;
import com.beauty.response.CommonResponse;
import com.beauty.response.MyReviewResponse;
import com.beauty.security.auth.JwtAuthenticationToken;
import com.beauty.service.ReviewService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value="/api/v1/review")
public class ReviewV1Controller {
	public final String TAG = "리뷰 - 로그인 필요";

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
			@ApiParam(name="sort", value="1:등록순 2:추천순", required = true)  @RequestParam int sort,
			JwtAuthenticationToken token) {
		return reviewService.list(product_id, photo, page, sort, token);
	}

	@ApiOperation(value = "등록", notes = "리뷰 등록", response = CommonResponse.class, tags={TAG, })
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody CommonResponse add( HttpSession session,
			@ApiParam(value="주문 ID") @RequestParam(value="order_id", required=true) Long order_id,
			@ApiParam(value="이미지 경로[]") @RequestParam(required=false) String[] files,
			@ApiParam(value="내용") @RequestParam(value="content", required=true) String content,
			@ApiParam(value="1~5") @RequestParam(value="star", required=true) int star,
			@ApiParam(value="피부타입  0 : 건성,  1 : 중성,  2 : 지성,  3 : 복합성") @RequestParam(value="skin_type", required=true) int skin_type,
			JwtAuthenticationToken token) {

		return reviewService.add(order_id, files, content, star, skin_type, token);
	}

	/**
	 * 리뷰 삭제
	 */
	@ApiOperation(value = "삭제", notes = "리뷰 삭제", response = CommonResponse.class, tags={TAG, })
	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
	public @ResponseBody CommonResponse delete(JwtAuthenticationToken token,
			@ApiParam(name="review_id", value="리뷰 ID", required = true) @RequestParam Long review_id) {
		return reviewService.delete(review_id, token);
	}

	@ApiOperation(value = "좋아요", notes = "리뷰 좋아요<br/> 성공 시 message에 현재 추천수 삽입", response = CommonResponse.class, tags={TAG, })
	@RequestMapping(value = "/like", method = RequestMethod.POST)
	public @ResponseBody CommonResponse like( HttpSession session,
			@ApiParam(value="리뷰 ID") @RequestParam(value="review_id", required=true) Long review_id,
			JwtAuthenticationToken token) {
		return reviewService.like(review_id, token);
	}

	/**
	 * 마이리뷰
	 */
	@ApiOperation(value = "마이리뷰", notes = "작성한 리뷰 및 작성 가능한 리뷰", response = MyReviewResponse.class, tags={TAG, })
	@RequestMapping(value = "/myreview", method = RequestMethod.POST)
	public @ResponseBody MyReviewResponse myreview( JwtAuthenticationToken token) {
		return reviewService.myReview(token);
	}

}
