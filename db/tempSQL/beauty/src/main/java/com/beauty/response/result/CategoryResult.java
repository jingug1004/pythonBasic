package com.beauty.response.result;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class CategoryResult {

	@ApiModelProperty(value = "카테고리 ID")
	@Getter @Setter
	private Long ctg_id;
	
	@ApiModelProperty(value = "카테고리")
	@Getter @Setter
	private String ctg;
	
	@ApiModelProperty(value = "부모 카테고리")
	@Getter @Setter
	private Long ctg_p;
	
	@Getter @Setter
	private List<CategoryResult> chidList; 
}
