package com.beauty.iamport;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class PaymentDesc {
    @ApiModelProperty(value = "상품")
	@Getter @Setter
	private String imp_uid;
    @ApiModelProperty(value = "상품")
	@Getter @Setter
	private String merchant_uid;
    @ApiModelProperty(value = "상품")
	@Getter @Setter
	private String point_dis;
    @ApiModelProperty(value = "상품")
	@Getter @Setter
	private String coupon_dis;
    @ApiModelProperty(value = "상품")
	@Getter @Setter
	private List<Object> cp_list;
    @ApiModelProperty(value = "상품")
	@Getter @Setter
	private List<Object> item_list;
	
}
