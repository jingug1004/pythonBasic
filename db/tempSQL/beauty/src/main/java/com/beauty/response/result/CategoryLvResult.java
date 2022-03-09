package com.beauty.response.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class CategoryLvResult {

	@ApiModelProperty(value = "LV1")
	@Getter @Setter
	private Long lv1_id;
	
	@ApiModelProperty(value = "LV1 명")
	@Getter @Setter
	private String lv1;

	@ApiModelProperty(value = "LV2")
	@Getter @Setter
	private Long lv2_id;
	
	@ApiModelProperty(value = "LV2 명")
	@Getter @Setter
	private String lv2;
	
	@ApiModelProperty(value = "LV3")
	@Getter @Setter
	private Long lv3_id;
	
	@ApiModelProperty(value = "LV3 명")
	@Getter @Setter
	private String lv3;
	
}
