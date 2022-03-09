package com.beauty.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="APP_BRAND")
@ApiModel
@EqualsAndHashCode
@ToString
public class Brand {

	@ApiModelProperty(value = "브랜드 ID")
	@Id
	@Column(name="brand_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long brandId;

	@ApiModelProperty(value = "브랜드명")
	@Column(name="brand_name")
	@Getter @Setter
	private String brandName;

	@ApiModelProperty(value = "브랜드배너")
	@Column(name="banner")
	@Getter @Setter
	private String banner;

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();

	// 사용여부
	@Column(name="brand_visible", columnDefinition="CHAR(1) default 'Y'")
    @Getter @Setter
	private String brandVisible = "Y";
}
