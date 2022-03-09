package com.beauty.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="APP_STAMP")
@EqualsAndHashCode
@ToString
@ApiModel
public class Stamp {

	//  ID
	@Id 
    @Column(name="st_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long stId;
	
    @ApiModelProperty(value = "1 : 쿠폰, 2:포인트, 3 : 상품")
    @Column(name="type", columnDefinition="int(1) default '0'")
    @Getter @Setter
	private int type;
	
    @ApiModelProperty(value = "스탬프 수")
    @Column(name="stamp")
    @Getter @Setter
	private int stamp;
    
    @ApiModelProperty(value = "스탬프 키 1: 쿠폰ID, 2: 포인트 수, 3: 상품ID")
    @Column(name="stamp_key")
    @Getter @Setter
    private String stampKey;
    
	@Column(name="stamp_month")
	@ApiModelProperty(value = "교환년월")
	@Getter @Setter
	private Long stampMonth;
}
