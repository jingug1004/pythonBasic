package com.beauty.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="APP_EVENT")
@EqualsAndHashCode
@ApiModel
public class Event {

	@ApiModelProperty(value = "이벤트 ID")
	@Id
	@Column(name="e_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long eid;

	@ApiModelProperty(value = "썸네일")
	@Column(name="thumbnail")
	@Getter @Setter
	private String thumbnail;

	@ApiModelProperty(value = "이벤트타입 - 0: 출석체크 1: 브랜드 2: 상품 3: 기획전, 4: 쿠폰, 5: 기본")
	@Column(name="e_type")
	@Getter @Setter
	private int etype;

	@Column(name="stop_event", columnDefinition="int(1) default '0'")
	@ApiModelProperty(value = "진행여부  0 : 진행중  1 : 종료")
	@Getter @Setter
	private int stopEvent = 0;
	
	@ApiModelProperty(value = "등록일")
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();

    @ApiModelProperty(value = "eType이 4 또는 5일 경우 HTML")
    @Lob
    @Getter @Setter
    private String content;
    
	@ApiModelProperty(value = "출석체크: 0, 브랜드: 브랜드ID, 상품: 상품ID, 기획전: 0, 기본: 0")
	@Column(name="t_id")
	@Getter @Setter
	private Long tid;

	@ApiModelProperty(value = "순서")
	@Column(name="sort")
	@Getter @Setter
	private int sort;
}
