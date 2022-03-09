package com.beauty.entity;

import java.util.Date;

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
@Table(name="APP_TEN_MENU")
@EqualsAndHashCode
@ToString
@ApiModel
public class MainTenMenu {

	@Id 
    @Column(name="tm_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long tmId;
	
	@ApiModelProperty(value = "이름")
	@Getter @Setter
	private String name;
	
	@ApiModelProperty(value = "아이콘")
	@Getter @Setter
	private String icon;
	
	
	@ApiModelProperty(value = "타입 - 0: 출석체크 1: 브랜드 2: 카테고리 3: 기획전, 4: 쿠폰")
	@Column(name="tm_type")
	@Getter @Setter
	private int tmType;
	
	@ApiModelProperty(value = "출석체크: 0, 브랜드: 브랜드ID, 카테고리: 카테고리ID, 기획전: 기획전ID, 쿠폰: 0")
	@Column(name="t_id")
	@Getter @Setter
	private Long tid;
	
	@ApiModelProperty(value = "노출 여부")
    @Column(name="show_yn", columnDefinition="char(1) default 'N'")
	@Getter @Setter
	private String showYn = "N";
	
    @ApiModelProperty(value = "정렬")
    @Column(name="sort", columnDefinition="int(2) default '0'")
	@Getter @Setter
	private int sort;
	
	@ApiModelProperty(value = "등록일")
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
	
}
