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
@Table(name="APP_STAMP_USER")
@EqualsAndHashCode
@ToString
@ApiModel
public class StampUser {

	//  ID
	@Id 
    @Column(name="stu_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long stuId;
	
    @ApiModelProperty(value = "사용내역  1:쿠폰,2:포인트,3:상품 ")
    @Column(name="type", columnDefinition="int(1) default '0'")
    @Getter @Setter
	private int type;
	
    @ApiModelProperty(value = "스탬프")
    @ManyToOne
    @JoinColumn(name="st_id")
    @Getter @Setter
    private Stamp stamp;
    
	@ApiModelProperty(value = "유저 고유ID")
	@ManyToOne
	@JoinColumn(name="user_id")
	@Getter @Setter
	private User user;
	
	@Column(name="use_month")
	@ApiModelProperty(value = "사용년월")
	@Getter @Setter
	private Long useMonth;
	
	@ApiModelProperty(value = "사용일")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
}
