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
@Table(name="APP_FIRST_BANNER")
@EqualsAndHashCode
@ToString
@ApiModel
public class MainFirstBanner {

	@Id 
    @Column(name="fb_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long fbId;
	
	@ApiModelProperty(value = "배너 이미지")
	@Getter @Setter
	private String thumbnail;
	
	@ApiModelProperty(value = "배너타입 - 0: 출석체크 1: 브랜드 2: 상품 3: 기획전, 4: 쿠폰, 5: 공지")
	@Column(name="sb_type")
	@Getter @Setter
	private int sbType;
	
	@ApiModelProperty(value = "출석체크: 이벤트ID, 브랜드: 브랜드ID, 상품: 상품ID, 기획전: 기획전ID, 쿠폰: 이벤트ID")
	@Column(name="t_id")
	@Getter @Setter
	private Long tid;
	
	@ApiModelProperty(value = "노출 여부")
    @Column(name="show_yn", columnDefinition="char(1) default 'N'")
	@Getter @Setter
	private String showYn = "N";
	
    @ApiModelProperty(value = "정렬")
    @Column(name="sort", columnDefinition="int(3) default '0'")
	@Getter @Setter
	private int sort;
	
	@ApiModelProperty(value = "등록일")
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();

    @ApiModelProperty(value = "타입  1 : 메인  2 : 베스트  3 : New")
    @Column(name="show_type", columnDefinition="int(1) default '1'")
	@Getter @Setter
	private int showType = 1;
    
	
	
}
