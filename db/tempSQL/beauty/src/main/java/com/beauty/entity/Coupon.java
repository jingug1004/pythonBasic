package com.beauty.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="APP_COUPON")
@EqualsAndHashCode
@ToString
@ApiModel
public class Coupon {

	// 쿠폰 ID
	@Id 
    @Column(name="cp_id")
	@Getter @Setter
	private String cpId;
	
    @ApiModelProperty(value = "쿠폰 이름") 
	@Column(name="cp_name")
	@Getter @Setter
	private String cpName;
    
    @ApiModelProperty(value = "할인율 및 할인금액")
	@Column(name="price")
	@Getter @Setter
	private Long price=0L;
	
    @ApiModelProperty(value = "최대 할인금액 ( 0 무제한 )")
	@Column(name="maximum")
	@Getter @Setter
	private Long maximum=0L;
	
    @ApiModelProperty(value = "최소 상품금액 ( 0 무제한 )")
	@Column(name="minimum")
	@Getter @Setter
	private Long minimum=0L;
    
    @ApiModelProperty(value = "쿠폰타입 [ 0:일반쿠폰, 1:중복쿠폰, 2:특별쿠폰-브랜드, 3:특별쿠폰-카테고리, 4:특별쿠폰-제품, 5:제품교환권 ]")
	@Column(name="cp_type", columnDefinition="int(1) default '0'")
	@Getter @Setter
	private int cpType=0;
    
    @ApiModelProperty(value = "남은수량")
	@Column(name="cp_count")
	@Getter @Setter
	private int cpCount=0;

    @ApiModelProperty(value = "할인타입 [ 0 : %,  1 : 원 ]")
	@Column(name="sale_type", columnDefinition="int(1) default '0'")
	@Getter @Setter
	private int saleType=0;
	    
    @ApiModelProperty(value = "소멸기준 [ 0:다운로드 기준, 1:특정일 기준, 2:매일자정 ]")
	@Column(name="end_type", columnDefinition="int(1) default '0'")
	@Getter @Setter
	private int endType=0;
	
    @ApiModelProperty(value = "소멸날짜 [ 0: x일, 1: x일, 2:매일 x시]")
	@Column(name="end_after", columnDefinition="int(3) default '0'")
	@Getter @Setter
	private int endAfter=0;
    
    @ApiModelProperty(value = "특정일")
	@Column(name="end_date")
	@Getter @Setter
	private Date endDate;
    
    @ApiModelProperty(value = "다운로드 가능여부 [ 0:다운로드 불가, 1:가능 ]")
	@Column(name="download", columnDefinition="int(1) default '0'")
	@Getter @Setter
    private int download = 0;
    
    @ApiModelProperty(value = "자동발급 타입[0: X, 1:신규회원, 2:첫구매]")
	@Column(name="auto", columnDefinition="int(1) default '0'")
	@Getter @Setter
    private int auto = 0;
    
    @ApiModelProperty(value = "등록일")
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
    
	@ApiModelProperty(value = "특별쿠폰 데이터")
	@OneToMany(cascade=CascadeType.ALL, mappedBy="cpId")
	@Getter @Setter
	public List<SpecialCoupon> spCoupon;
    
}
