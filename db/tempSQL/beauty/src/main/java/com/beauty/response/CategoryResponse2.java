package com.beauty.response;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import com.beauty.entity.Brand;
import com.beauty.response.result.CategoryLvResult;
import com.beauty.response.result.ResultCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class CategoryResponse2 {

	@ApiModelProperty(value = "200 성공 99 실패")
	@Getter 
	private final ResultCode resultCode;

	@ApiModelProperty(value = "성공시 success 실패시 실패 내용 확인")
	@Getter 
    private final String message;

	@Getter 
    private final Date timestamp;
	
	@ApiModelProperty(value = "카테고리 List  Object[lv1_id, lv1, lv2_id, lv2, lv3_id, lv3] ")
	@Getter @Setter
	private List<CategoryLvResult> categoryList;
	

	@ApiModelProperty(value = "브랜드 List")
	@Getter @Setter
	private TreeMap brandList;


	protected CategoryResponse2(final String message, final ResultCode resultCode, final List<CategoryLvResult> categoryList, final TreeMap brandList) {
        this.message = message;
        this.resultCode = resultCode;
        this.categoryList = categoryList;
        this.brandList = brandList;
        this.timestamp = new java.util.Date();
    }
	
    public static CategoryResponse2 of(final String message, final ResultCode resultCode, final List<CategoryLvResult> categoryList, final TreeMap brandList) {
        return new CategoryResponse2(message, resultCode, categoryList, brandList);
    }
}
