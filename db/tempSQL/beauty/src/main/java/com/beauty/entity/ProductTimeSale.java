package com.beauty.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="APP_TIME_SALE")
@EqualsAndHashCode
@ToString
@ApiModel
public class ProductTimeSale {

	@Id 
    @Column(name="sale_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long saleId;
	
    @ApiModelProperty(value = "세일상품")
	@ManyToOne
	@JoinColumn(name="product_id")
	@Getter @Setter
	private Product product;
	
    @ApiModelProperty(value = "0:종료일, 1:상품수")
    @Column(name="time_type", columnDefinition="int(1) default '0'")
	@Getter @Setter
	private int timeType;
    
    @ApiModelProperty(value = "종료일")
	@Column(name="end_date")
	@Getter @Setter
	private Date endDate;
    
    @ApiModelProperty(value = "총 상품수")
	@Column(name="prod_count")
	@Getter @Setter
	private int prodCount;
    
    @ApiModelProperty(value = "남은 상품수")
	@Column(name="	rn_count")
	@Getter @Setter
	private int rnCount;
    
	@ApiModelProperty(value = "등록일")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
}
