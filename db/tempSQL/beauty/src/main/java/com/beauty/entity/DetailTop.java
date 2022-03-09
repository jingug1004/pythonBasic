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

@Entity
@Table(name="APP_DETAIL_TOP")
@EqualsAndHashCode
@ApiModel
public class DetailTop {
	
    @Id 
    @Column(name="dt_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long dtId;
    
	@ApiModelProperty(value = "썸네일")
	@Column(name="thumb_url")
	@Getter @Setter
	private String thumbUrl;

	@ApiModelProperty(value = "상단 타입 - 0: 브랜드 1: 카테고리 2: 상품")
	@Column(name="top_type")
	@Getter @Setter
	private int topType;
	
	@ApiModelProperty(value = "이벤트ID : 0일 경우 일반공지")
	@Column(name="event_id")
	@Getter @Setter
	private Long eid;

	@ApiModelProperty(value = "등록일")
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
    
}
