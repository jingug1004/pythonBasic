package com.beauty.response;

import java.util.Date;
import java.util.List;

import com.beauty.entity.Review;
import com.beauty.entity.ReviewWait;
import com.beauty.response.result.ResultCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel
public class MyReviewResponse {
	
	@ApiModelProperty(value = "200 성공 99 실패")
	@Getter 
	private final ResultCode resultCode;

	@ApiModelProperty(value = "성공시 success 실패시 실패 내용 확인")
	@Getter 
    private final String message;

	@Getter 
    private final Date timestamp;
    
	@ApiModelProperty(value = "작성 가능한 리뷰")
	@Getter 
	private List<ReviewWait> reviewList;
	
	@ApiModelProperty(value = "작성한 리뷰")
	@Getter 
	private List<Review> myReviewList;
	
    protected MyReviewResponse(final String message, final ResultCode resultCode, final List<ReviewWait> reviewList, final List<Review> myReviewList) {
        this.message = message;
        this.resultCode = resultCode;
        this.reviewList = reviewList;
        this.myReviewList = myReviewList;
        this.timestamp = new java.util.Date();
    }

    public static MyReviewResponse of(final String message, final ResultCode resultCode, final List<ReviewWait> reviewList, final List<Review> myReviewList) {
        return new MyReviewResponse(message, resultCode, reviewList, myReviewList);
    }
}
